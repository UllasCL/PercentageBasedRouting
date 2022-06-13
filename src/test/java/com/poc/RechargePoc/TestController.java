package com.poc.RechargePoc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class TestController {
  public static void main(String[] args) {
    for (int i=0;i<100;i++) {
      callApi(i);
    }
  }

  private static void callApi(final Integer orderId){
    RestTemplate restTemplate = new RestTemplate();

    String url = String.format("http://localhost:8080/recharge/poc/fulfilment/fulfill?orderId=%s",orderId);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String answer = restTemplate.postForObject(url, null, String.class);
    System.out.println(answer);

  }
}
