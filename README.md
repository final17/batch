# 웨이티 프로젝트 BATCH

## KEY Summary

### ⏰ 스프링 배치 도입

1. **배경**
    - **스프링 배치 도입**
        - 정산은 실시간 처리보다는 시스템 부담이 적은 시간대에 일괄적으로 처리할 필요성에 의해 배치 방식을 선택
        - 안정적인 운영을 위해 메인 DB와 배치 메타데이터 DB를 분리하여 독립적인 데이터 관리 구조를 구성
    - **배치 메타데이터 테이블 생성 필수화**
        - 메타데이터 전용 DB를 나누는 구조로 전환
    - **멀티 DataSource 구성**
        - 메인배치 MetaDBConfig과 DataDBConfig로 데이터베이스 모듈 구분
2. **문제**
    - **DB 분리 설정 에러**
        - application.yml에 설정 에러로 인한 메타 테이블 생성 불가
    - **카멜문법 -> 스네이크문법 변환 에러**
        - DB에 접근하여 Query로 변환시 문법 변환이 안되는 에러 발생
3. **해결 방안**
    - **DB 분리 해결 방법**
        - DB에 맞는 스키마를 찾아서 추가 설정
    - **카멜문법 -> 스네이크문법 변환 해결 방법**
        - CamelCaseToUnderscoresNamingStrategy 추가 설정
        - SpringImplicitNamingStrategy 추가 설정

## 기술적 고도화

### ⚙️ 정산 처리 자동화

### [문제 인식]

토스페이에서 제공하는 정산 API가 테스트 키로 구현되어 있어 초기 개발 과정에서 정산 작업을 진행할 수 없는 상황에 직면하였습니다.
이를 해결하기 위해 실제 수집된 결제 데이터를 활용하여 Spring Batch를 사용해 정산을 진행하기로 결정했습니다.
하지만 정산을 위한 서버 설정 과정에서 몇 가지 문제가 발생하였습니다:

1. 메타테이블 생성 오류: 데이터베이스 연결 시 발생한 여러 에러로 인해 메타테이블이 정상적으로 생성되지 않았습니다. 이로 인해 정산 프로세스가 중단되는 상황이 발생했습니다.
    <details>
    <summary>application.yml 설정</summary>

        spring:
            batch:
                jdbc:
                    initialize-schema: always
    </details>

2. 메타테이블 분리: Spring Batch가 자동으로 생성하는 메타테이블을 분리하기 위해 데이터베이스를 분리하는 작업이 필요했습니다. 이 과정에서 기존 설정과의 충돌이 발생하였습니다.
   <details>
    <summary>application.yml 설정</summary>

        spring:
            config:
                import: optional:file:.env[.properties]
            datasource-meta:
                url: ${BATCH_DB_URL}
                username: ${BATCH_DB_USERNAME}
                password: ${BATCH_DB_PASSWORD}
                driver-class-name: com.mysql.cj.jdbc.Driver
            datasource-data:
                url: ${DB_URL}
                username: ${DB_USERNAME}
                password: ${DB_PASSWORD}
                driver-class-name: com.mysql.cj.jdbc.Driver
    </details>

3. JPA CRUD: DB 분리 영향으로 데이터 처리 시 발생하는 문법 변환 문제에 직면하게 되어 실제 데이터가 저장된 DB에 접근이 불가능했습니다.

4. 정산 결과의 정확성: 에러로 인해 정산 결과의 신뢰성이 낮아졌으며, 프로세스의 시작점 설정이 모호한 상태에서 어떤 시점에서 작업을 시작해야 할지를 명확히 정의하지 못한 문제도 확인되었습니다.
    <details>
    <summary>batch 사용 방법</summary>
    settleLog 처리를 하기 위한 Spring batch (ListItemReader 처리 방식)

        @Bean
        public Step settleStep() {
            return new StepBuilder("settleStep", jobRepository)
                .<PaymentDto, SettlementDto> chunk(10, platformTransactionManager)
                .reader(settleReader())
                .processor(settleProcessor())
                .writer(settleWriter())
                .build();
        }

        @Bean
        public ItemReader<PaymentDto> settleReader() {
            LocalDate today = LocalDate.now();
            String todayStr = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<PaymentDto> paymentDtoList = paymentService.paymentDtoList(todayStr);
            return new ListItemReader<>(paymentDtoList);
        }

   settleSummary 처리를 하기 위한 Spring Batch (Batch에서 제공하는 기능 사용 X)

        @Bean
        public Step summaryStep() {
            return new StepBuilder("secondStep", jobRepository)
                .tasklet(summaryTasklet() , platformTransactionManager)
                .build();
        }
    
        @Bean
        public Tasklet summaryTasklet() {
            return new Tasklet() {
                @Override
                public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    // 정산 작업 수행
                    log.info("Executing settlement tasklet...");
    
                    String type = chunkContext.getStepContext().getStepExecution().getJobParameters().getString("type");
    
                    List<SettlementSummaryDto> settlementSummaryDtos = settlementService.getSettlementSummary(SummaryType.of(type));
    
                    List<SettlementSummary> settlementSummaries = new ArrayList<>();
                    for (SettlementSummaryDto ssd : settlementSummaryDtos) {
                        SettlementSummary settlementSummary = new SettlementSummary(ssd.getSummaryDate(), ssd.getType() , ssd.getTotalAmount() , ssd.getTotalFee() , ssd.getTotalTransactions() , ssd.getUserId() , ssd.getStoreId());
                        settlementSummaries.add(settlementSummary);
                    }
                    settlementSummaryRepository.saveAll(settlementSummaries);
    
                    return RepeatStatus.FINISHED; // 작업 완료 상태 반환
            }
        };
   }
   </details>

### [해결 방안]

위 문제들을 해결하기 위해 다음과 같은 방법을 모색하였습니다:

1. 스키마 설정 추가: 현재 사용하고 있는 데이터베이스에 맞춰 메타테이블 생성을 위한 스키마 설정을 추가하였습니다. 이를 통해 테이블 생성 과정에서 발생하는 오류를 해결할 수 있었습니다.
    <details>
    <summary>application.yml 설정</summary>
        Spring Batch가 5버전으로 업그레이드 한 영향으로 참조하는 블로그 , 문서들이랑 적용하는 내용이 달랐고 사용중인 DB의 스키마를 직접 가져와서 적용 등 아래와 같이 설정함으로써 해결이 가능했습니다.

        spring:
            batch:
               job:
                   enabled: false
               jdbc:
                   initialize-schema: always
                   schema: classpath:org/springframework/batch/core/schema-mysql.sql

    <details>
    <summary>db schema 찾는 방법</summary>
    <img src="https://github.com/user-attachments/assets/cf85b412-a297-47a9-91d8-bd2cf7fd9f4b"/>
    <img src="https://github.com/user-attachments/assets/22927889-5292-4786-a274-b7d8a4cddb4f"/>
    </details>
    </details>

2. application.yml 설정 조정: 기존 프로젝트의 application.yml 설정과 달라 적절히 수정하여 문제를 해결했습니다. 특히, 데이터베이스 연결 관련 설정을 세심하게 조정하여 원활한 연결을 보장했습니다.
    <details>
    <summary>application.yml 설정</summary>
        알고보면 매우 간단한 문제이지만 어이가 없는 문제이기도 했습니다. url이 아니라 jdbc-url로 설정해야지 해결되는 문제였습니다.

        spring:
            config:
                import: optional:file:.env[.properties]
            datasource-meta:
                jdbc-url: ${BATCH_DB_URL}
                username: ${BATCH_DB_USERNAME}
                password: ${BATCH_DB_PASSWORD}
                driver-class-name: com.mysql.cj.jdbc.Driver
            datasource-data:
                jdbc-url: ${DB_URL}
                username: ${DB_USERNAME}
                password: ${DB_PASSWORD}
                driver-class-name: com.mysql.cj.jdbc.Driver

3. 카멜 케이스와 스네이크 케이스 변환: 데이터 처리 시 발생하는 문법 변환 문제에 대해 추가 설정을 통해 카멜 문법을 스네이크 문법으로 변환하도록 하였습니다. 이 과정에서 ORM 설정의 세부 사항도 조정하여 매핑 오류를 최소화했습니다.
   <details>
   <summary>DataDBConfig.java 설정</summary>
      CamelCaseToUnderscoresNamingStrategy 설정 추가
      SpringImplicitNamingStrategy 설정 추가
      
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.auto_quote_keyword", "true");
        properties.put("hibernate.highlight_sql", "true");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
   </details>

4. 청크 처리 최적화: 정산 프로세스의 정확성과 일관성을 확보하기 위해 진행 중인 청크의 범위를 명확히 하고, 데이터 처리 방식을 개선하였습니다. 특히, 메모리 초과 문제를 피하기 위해 ListItemReader 대신 ItemReader를 사용하여 메모리 사용 효율을 극대화했습니다. 이렇게 함으로써 데이터 처리 성능을 향상시킬 수 있었습니다.
   <details>
   <summary>성능 개선 및 데이터 정확성과 일관성 확보</summary>
      ListItemReader → ItemReader 로 변환

           @Bean
           public ItemReader<PaymentDto> settleReader() {
               log.info("settleReader");
               LocalDate today = LocalDate.now();
               String todayStr = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

              // 페이지 번호 초기화
              final int[] pageNumber = {1}; // 배열을 사용하여 effectively final로 유지

              return new ItemReader<PaymentDto>() {
                  private List<PaymentDto> currentBatch = new ArrayList<>();
                  private int currentIndex = 0;

                  @Override
                  public PaymentDto read() {
                      // 현재 배치가 비어있거나 인덱스가 범위를 초과하면 새로운 배치 읽기
                      if (currentBatch.isEmpty() || currentIndex >= currentBatch.size()) {
                       currentBatch = paymentService.paymentDtoList(todayStr, pageNumber[0]++ , chunkSize);
                       currentIndex = 0;

                       // 데이터가 없으면 null 반환하여 종료
                       if (currentBatch.isEmpty()) {
                           return null;
                       }
                      }
                   return currentBatch.get(currentIndex++);
                  }
              };
           }

      정산 프로세스에 문제가 생겼을때 문제 생긴 부분의 index를 명확히하고 데이터의 정확성과 일관성을 확보했습니다.

            @Bean
            public Tasklet summaryTasklet() {
                return new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        log.info("Executing settlement tasklet...");
            
                         // JobParameters에서 요약 유형 가져오기
                         String type = chunkContext.getStepContext().getStepExecution().getJobParameters().getString("type");
                         ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
            
                         // 마지막 처리된 인덱스 가져오기
                         int lastProcessedIndex = executionContext.containsKey("lastProcessedIndex") ?
                                 executionContext.getInt("lastProcessedIndex") : 0;
            
                         // 정산 데이터 가져오기
                         List<SettlementSummaryDto> settlementSummaryDtos = settlementService.getSettlementSummary(SummaryType.of(type));
                         if (lastProcessedIndex >= settlementSummaryDtos.size()) {
                             // 더 이상 처리할 데이터가 없는 경우 종료
                             return RepeatStatus.FINISHED;
                         }
            
                         // 남은 데이터 처리
                         for (int i = lastProcessedIndex; i < settlementSummaryDtos.size(); i++) {
                             SettlementSummaryDto ssd = settlementSummaryDtos.get(i);
                             SettlementSummary settlementSummary = new SettlementSummary(
                                     ssd.getSummaryDate(), ssd.getType(), ssd.getTotalAmount(),
                                     ssd.getTotalFee(), ssd.getTotalTransactions(), ssd.getUserId(), ssd.getStoreId()
                             );
                             settlementSummaryRepository.save(settlementSummary);
            
                             // 마지막 처리된 인덱스 업데이트 및 저장
                             executionContext.putInt("lastProcessedIndex", i + 1);
                         }
            
                         return RepeatStatus.FINISHED; // 작업 완료 상태 반환
                    }
                };
            }
   
   </details>

### [해결 완료]

모든 문제를 해결한 후, Spring Batch를 활용하여 정산 시스템을 성공적으로 구축하였습니다.
이를 통해 정산 프로세스의 자동화, 효율성 향상, 데이터의 정확성을 확보할 수 있게 되었습니다.
또한, 정산 결과에 대한 신뢰성을 높이며, 향후 데이터 처리 시 발생할 수 있는 문제들을 미리 예방할 수 있는 기반을 마련하였습니다.
