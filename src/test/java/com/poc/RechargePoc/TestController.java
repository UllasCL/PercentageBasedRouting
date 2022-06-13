package com.poc.RechargePoc;

import org.springframework.web.client.RestTemplate;

public class TestController {
  public static void main(String[] args) {
    for (int j = 1; j <= 2; j++) {
      for (int i = 1; i <= 100; i++) {
        callApi(i);
      }
    }
//    for (int i = 2; i <= 100; i=i+2) {
//      callApi(i);
//    }
  }

  private static void callApi(final Integer orderId) {
    RestTemplate restTemplate = new RestTemplate();

    String url = String.format("http://localhost:8080/recharge/poc/fulfilment/fulfill?orderId=%s",
        orderId);

    String answer = restTemplate.postForObject(url, null, String.class);
    System.out.println(answer);

  }
}
