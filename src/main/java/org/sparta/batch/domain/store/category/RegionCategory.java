package org.sparta.batch.domain.store.category;

import java.util.ArrayList;
import java.util.List;

public enum RegionCategory implements HierarchicalCategory {
    ROOT("지역", "RE", null),

    // 서울
    SEOUL("서울", "01", ROOT),
        SEOUL_JONGNO("종로구", "1", SEOUL),
        SEOUL_JUNG("중구", "2", SEOUL),
        SEOUL_YONGSAN("용산구", "3", SEOUL),
        SEOUL_SEONGDONG("성동구", "4", SEOUL),
        SEOUL_GWANGJIN("광진구", "5", SEOUL),
        SEOUL_DONGDAEMUN("동대문구", "6", SEOUL),
        SEOUL_JUNGNANG("중랑구", "7", SEOUL),
        SEOUL_SEONGBUK("성북구", "8", SEOUL),
        SEOUL_GANGBUK("강북구", "9", SEOUL),
        SEOUL_DOBONG("도봉구", "10", SEOUL),
        SEOUL_NOWON("노원구", "11", SEOUL),
        SEOUL_EUNPYEONG("은평구", "12", SEOUL),
        SEOUL_SEODAEMUN("서대문구", "13", SEOUL),
        SEOUL_MAPO("마포구", "14", SEOUL),
        SEOUL_YANGCHEON("양천구", "15", SEOUL),
        SEOUL_GANGSEO("강서구", "16", SEOUL),
        SEOUL_GURO("구로구", "17", SEOUL),
        SEOUL_GEUMCHEON("금천구", "18", SEOUL),
        SEOUL_YEONGDEUNGPO("영등포구", "19", SEOUL),
        SEOUL_DONGJAK("동작구", "20", SEOUL),
        SEOUL_GWANAK("관악구", "21", SEOUL),
        SEOUL_SEOCHO("서초구", "22", SEOUL),
        SEOUL_GANGNAM("강남구", "23", SEOUL),
        SEOUL_SONGPA("송파구", "24", SEOUL),
        SEOUL_GANGDONG("강동구", "25", SEOUL),

    // 경기도
    GYEONGGI("경기도", "02", ROOT),
        GYEONGGI_SUWON("수원시", "1", GYEONGGI),
        GYEONGGI_SEONGNAM("성남시", "2", GYEONGGI),
        GYEONGGI_GOYANG("고양시", "3", GYEONGGI),
        GYEONGGI_YONGIN("용인시", "4", GYEONGGI),
        GYEONGGI_BUCHEON("부천시", "5", GYEONGGI),
        GYEONGGI_ANSAN("안산시", "6", GYEONGGI),
        GYEONGGI_ANYANG("안양시", "7", GYEONGGI),
        GYEONGGI_UIJEONGBU("의정부시", "8", GYEONGGI),
        GYEONGGI_PYEONGTAEK("평택시", "9", GYEONGGI),
        GYEONGGI_GWANGMYEONG("광명시", "10", GYEONGGI),
        GYEONGGI_POCHEON("포천시", "11", GYEONGGI),
        GYEONGGI_GIMPO("김포시", "12", GYEONGGI),
        GYEONGGI_GWANGJU("광주시", "13", GYEONGGI),
        GYEONGGI_GURI("구리시", "14", GYEONGGI),
        GYEONGGI_NAMYANGJU("남양주시", "15", GYEONGGI),
        GYEONGGI_OSAN("오산시", "16", GYEONGGI),
        GYEONGGI_SIHEUNG("시흥시", "17", GYEONGGI),
        GYEONGGI_GUNPO("군포시", "18", GYEONGGI),
        GYEONGGI_HANAM("하남시", "19", GYEONGGI),
        GYEONGGI_ICHEON("이천시", "20", GYEONGGI),
        GYEONGGI_YANGJU("양주시", "21", GYEONGGI),
        GYEONGGI_YEOJU("여주시", "22", GYEONGGI),
        GYEONGGI_YANGPYEONG("양평군", "23", GYEONGGI),
        GYEONGGI_PAJU("파주시", "24", GYEONGGI),

    // 세종
    SEJONG("세종", "03", ROOT),

    // 인천
    INCHEON("인천", "04", ROOT),
        INCHEON_JUNG("중구", "1", INCHEON),
        INCHEON_DONG("동구", "2", INCHEON),
        INCHEON_MICHUHOL("미추홀구", "3", INCHEON),
        INCHEON_YEONSU("연수구", "4", INCHEON),
        INCHEON_NAMDONG("남동구", "5", INCHEON),
        INCHEON_BUPYEONG("부평구", "6", INCHEON),
        INCHEON_GYEYANG("계양구", "7", INCHEON),
        INCHEON_SEO("서구", "8", INCHEON),
        INCHEON_GANGHWA("강화군", "9", INCHEON),
        INCHEON_ONGJIN("옹진군", "10", INCHEON),

    // 부산
    BUSAN("부산", "05", ROOT),
        BUSAN_JUNG("중구", "1", BUSAN),
        BUSAN_SEO("서구", "2", BUSAN),
        BUSAN_DONG("동구", "3", BUSAN),
        BUSAN_YEONGDO("영도구", "4", BUSAN),
        BUSAN_BUSANJIN("부산진구", "5", BUSAN),
        BUSAN_DONGNAE("동래구", "6", BUSAN),
        BUSAN_NAM("남구", "7", BUSAN),
        BUSAN_BUK("북구", "8", BUSAN),
        BUSAN_HAEUNDAE("해운대구", "9", BUSAN),
        BUSAN_SAHA("사하구", "10", BUSAN),
        BUSAN_GEUMJEONG("금정구", "11", BUSAN),
        BUSAN_GANGSEO("강서구", "12", BUSAN),
        BUSAN_YEONJE("연제구", "13", BUSAN),
        BUSAN_SUYEONG("수영구", "14", BUSAN),
        BUSAN_SASANG("사상구", "15", BUSAN),
        BUSAN_GIJANG("기장군", "16", BUSAN),

    // 대구
    DAEGU("대구", "06", ROOT),
        DAEGU_JUNG("중구", "1", DAEGU),
        DAEGU_DONG("동구", "2", DAEGU),
        DAEGU_SEO("서구", "3", DAEGU),
        DAEGU_NAM("남구", "4", DAEGU),
        DAEGU_BUK("북구", "5", DAEGU),
        DAEGU_SUSUNG("수성구", "6", DAEGU),
        DAEGU_DALSEO("달서구", "7", DAEGU),
        DAEGU_DALSEONG("달성군", "8", DAEGU),

    // 광주
    GWANGJU("광주", "07", ROOT),
        GWANGJU_DONG("동구", "1", GWANGJU),
        GWANGJU_SEO("서구", "2", GWANGJU),
        GWANGJU_NAM("남구", "3", GWANGJU),
        GWANGJU_BUK("북구", "4", GWANGJU),
        GWANGJU_GWANGSAN("광산구", "5", GWANGJU),

    // 대전
    DAEJEON("대전", "08", ROOT),
        DAEJEON_DONG("동구", "1", DAEJEON),
        DAEJEON_JUNG("중구", "2", DAEJEON),
        DAEJEON_SEO("서구", "3", DAEJEON),
        DAEJEON_YUSEONG("유성구", "4", DAEJEON),
        DAEJEON_DAEDEOK("대덕구", "5", DAEJEON),

    // 울산
    ULSAN("울산", "09", ROOT),
        ULSAN_JUNG("중구", "1", ULSAN),
        ULSAN_NAM("남구", "2", ULSAN),
        ULSAN_DONG("동구", "3", ULSAN),
        ULSAN_BUK("북구", "4", ULSAN),
        ULSAN_ULJU("울주군", "5", ULSAN),


    // 강원도
    GANGWON("강원도", "10", ROOT),
        GANGWON_CHUNCHEON("춘천시", "1", GANGWON),
        GANGWON_WONJU("원주시", "2", GANGWON),
        GANGWON_GANGNEUNG("강릉시", "3", GANGWON),
        GANGWON_DONGHAE("동해시", "4", GANGWON),
        GANGWON_TAEBAEK("태백시", "5", GANGWON),
        GANGWON_SOKCHO("속초시", "6", GANGWON),
        GANGWON_SAMCHEOK("삼척시", "7", GANGWON),
        GANGWON_HONGCHEON("홍천군", "8", GANGWON),

    // 충청북도
    CHUNGBUK("충청북도", "11", ROOT),
        CHUNGBUK_CHEONGJU("청주시", "1", CHUNGBUK),
        CHUNGBUK_CHUNGJU("충주시", "2", CHUNGBUK),
        CHUNGBUK_JECHEON("제천시", "3", CHUNGBUK),
        CHUNGBUK_BOEUN("보은군", "4", CHUNGBUK),
        CHUNGBUK_OKCHEON("옥천군", "5", CHUNGBUK),
        CHUNGBUK_YEONGDONG("영동군", "6", CHUNGBUK),

    // 충청남도
    CHUNGNAM("충청남도", "12", ROOT),
        CHUNGNAM_CHEONAN("천안시", "1", CHUNGNAM),
        CHUNGNAM_GONGJU("공주시", "2", CHUNGNAM),
        CHUNGNAM_BORYEONG("보령시", "3", CHUNGNAM),
        CHUNGNAM_ASAN("아산시", "4", CHUNGNAM),
        CHUNGNAM_SEOSAN("서산시", "5", CHUNGNAM),

    // 전라북도
    JEONBUK("전라북도", "13", ROOT),
        JEONBUK_JEONJU("전주시", "1", JEONBUK),
        JEONBUK_GUNSAN("군산시", "2", JEONBUK),
        JEONBUK_IKSAN("익산시", "3", JEONBUK),
        JEONBUK_JEONGEUP("정읍시", "4", JEONBUK),
        JEONBUK_NAMWON("남원시", "5", JEONBUK),

    // 전라남도
    JEONNAM("전라남도", "14", ROOT),
        JEONNAM_MOKPO("목포시", "1", JEONNAM),
        JEONNAM_YEOSU("여수시", "2", JEONNAM),
        JEONNAM_SUNCHEON("순천시", "3", JEONNAM),
        JEONNAM_GWANGYANG("광양시", "4", JEONNAM),

    // 경상북도
    GYEONGBUK("경상북도", "15", ROOT),
        GYEONGBUK_POHANG("포항시", "1", GYEONGBUK),
        GYEONGBUK_GYEONGJU("경주시", "2", GYEONGBUK),
        GYEONGBUK_GIMCHEON("김천시", "3", GYEONGBUK),
        GYEONGBUK_ANDONG("안동시", "4", GYEONGBUK),
        GYEONGBUK_GUMI("구미시", "5", GYEONGBUK),
        GYEONGBUK_YEONGCHEON("영천시", "6", GYEONGBUK),

    // 경상남도
    GYEONGNAM("경상남도", "16", ROOT),
        GYEONGNAM_CHANGWON("창원시", "1", GYEONGNAM),
        GYEONGNAM_JINJU("진주시", "2", GYEONGNAM),
        GYEONGNAM_TONGYEONG("통영시", "3", GYEONGNAM),
        GYEONGNAM_SACHEON("사천시", "4", GYEONGNAM),
        GYEONGNAM_GIMHAE("김해시", "5", GYEONGNAM),

    // 제주도
    JEJU("제주도", "17", ROOT),
        JEJU_JEJU("제주시", "1", JEJU),
        JEJU_SEOGWIPO("서귀포시", "2", JEJU);



    private final String name;
    private final String code;
    private final HierarchicalCategory parent;
    private final List<HierarchicalCategory> children;
    private final int depth;

    RegionCategory(String name, String code, HierarchicalCategory parent) {
        this.name = name;
        this.parent = parent;
        this.code = code;
        children = new ArrayList<>();

        // root
        if(parent == null){
            depth = 0;
        } else {
            depth = 1 + parent.getDepth();
            parent.getChildren().add(this);
        }
    }

    @Override
    public String getCode() {
        if(parent == null) return code;

        return parent.getCode() + "-" + code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        if(parent == null) return name;

        return parent.getPath() + "/" + name;
    }

    @Override
    public int getDepth() {
        return depth;
    }


    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public boolean isRoot() {
        return this == ROOT || getParent() == ROOT;
    }

    @Override
    public List<HierarchicalCategory> getChildren() {
        return children;
    }

    @Override
    public HierarchicalCategory getParent() {
        return parent;
    }

    @Override
    public HierarchicalCategory getRoot() {
        HierarchicalCategory t = this;
        while(!t.isRoot()) {
            t = t.getParent();
        }

        return t;
    }
}
