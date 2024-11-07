package org.sparta.batch.common.resttemplate;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@Component
public class TossPaymentsService {

    private final RestTemplate restTemplate;

    @Value("${TOSS_PAY_TEST_SECRET_KEY}")
    private String secretKey;

    private String token;

    @PostConstruct
    public void init() {
        String testSecretApiKey = secretKey + ":";
        token = "Basic " + new String(Base64.getEncoder().encode(testSecretApiKey.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 정산 조회
     * */
    public ResponseEntity<String> settlements(String startDate, String endDate, Integer page , Integer size) {
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.set("Content-Type", "application/json; charset=UTF-8");

        HttpEntity<String> entity = new HttpEntity<>("{}", headers);

        String url = "https://api.tosspayments.com/v1/settlements";
        String requestUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate)
                .queryParam("page", page)
                .queryParam("size", size)
                .toUriString();

        // GET 요청 보내기
        return restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);
    }
}
