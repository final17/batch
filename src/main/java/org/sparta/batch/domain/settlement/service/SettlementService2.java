//package org.sparta.batch.domain.settlement.service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.sparta.batch.common.resttemplate.TossPaymentsService;
//import org.sparta.batch.domain.payment.entity.Payment;
//import org.sparta.batch.domain.payment.enums.PaymentMethod;
//import org.sparta.batch.domain.payment.enums.Status;
//import org.sparta.batch.domain.payment.repository.PaymentRepository;
//import org.sparta.batch.domain.settlement.dto.SettlementDto;
//import org.sparta.batch.domain.settlement.dto.SettlementFeesDto;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.OffsetDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class SettlementService2 {
//
//    private final TossPaymentsService tossPaymentsService;
//    private final PaymentRepository paymentRepository;
//
//    public List<SettlementDto> getSettlements(String startDate, String endDate, Integer page , Integer size) {
//        List<SettlementDto> settlements = new ArrayList<>();
//        try {
//            ResponseEntity<String> responseEntity;
//            String responseBody;
//            JsonNode jsonNode;
//            ObjectMapper mapper = new ObjectMapper();
//            responseEntity = tossPaymentsService.settlements(startDate , endDate , page , size);
//
//            log.info("settlements 요청 결과 : {}" , responseEntity.getBody());
//
//            responseBody = responseEntity.getBody();
//            jsonNode = mapper.readTree(responseBody);
//            if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                for (JsonNode node : jsonNode) {
//                    SettlementDto settlementDto = organize(node);
//                    settlements.add(settlementDto);
//                }
//            }
//        } catch(Exception e) {
//            throw new RuntimeException("데이터 파싱 에러");
//        }
//        return settlements;
//    }
//
//    private SettlementDto organize(JsonNode node) {
//        SettlementDto settlementDto = new SettlementDto();
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
//
//        settlementDto.setMId(node.get("mId").textValue());
//        settlementDto.setPaymentKey(node.get("paymentKey").textValue());
//        settlementDto.setTransactionKey(node.get("transactionKey").textValue());
//        settlementDto.setOrderId(node.get("orderId").textValue());
//        settlementDto.setCurrency(node.get("currency").textValue());
//        settlementDto.setMethod(PaymentMethod.of(node.get("method").textValue()));
//        settlementDto.setAmount(node.get("totalAmount").asLong());
//
//        settlementDto.setApprovedAt(OffsetDateTime.parse(node.get("approvedAt").textValue(), formatter));
//
//        settlementDto.setSoldDate(LocalDate.parse(node.get("soldDate").textValue()));
//        settlementDto.setPaidOutDate(LocalDate.parse(node.get("paidOutDate").textValue()));
//
//        List<SettlementFeesDto> settlementFeesDtos = new ArrayList<>();
//        if (node.has("fees") && node.get("fees").isArray()) {
//            JsonNode feesNodes = node.get("fees");
//            for (JsonNode feesNode : feesNodes) {
//                SettlementFeesDto settlementFeesDto = new SettlementFeesDto();
//                settlementFeesDto.setType(feesNode.get("type").textValue());
//                settlementFeesDto.setSupplyAmount(feesNode.get("supplyAmount").asLong());
//                settlementFeesDtos.add(settlementFeesDto);
//            }
//        }
//        settlementDto.setSettlementFeesDtos(settlementFeesDtos);
//
//        if (node.has("cancel")) {
//            settlementDto.setStatus(Status.CANCELLED);
//        } else {
//            settlementDto.setStatus(Status.COMPLETED);
//        }
//
//        Payment payment = paymentRepository.findByOrderId(settlementDto.getOrderId());
//        settlementDto.setUser(payment.getUser());
//        settlementDto.setStore(payment.getStore());
//
//        return settlementDto;
//    }
//}
