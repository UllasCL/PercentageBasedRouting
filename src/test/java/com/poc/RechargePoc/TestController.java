package com.poc.RechargePoc;

import org.springframework.web.client.RestTemplate;

/**
 * The type Test controller.
 */
public class TestController {
  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    call100APIs();
    callModule5Apis();
  }

  /**
   * Call module 5 apis.
   */
  private static void callModule5Apis() {
    for (int i = 0; i < 100; i = i + 5) {
      callApi(i);
    }
  }

  /**
   * Call 100 ap is.
   */
  private static void call100APIs() {
    for (int i = 0; i < 100; i++) {
      callApi(i);
    }
  }

  /**
   * Call api.
   *
   * @param orderId the order id
   */
  private static void callApi(final Integer orderId) {
    RestTemplate restTemplate = new RestTemplate();

    String url = String.format("http://localhost:8080/recharge/poc/fulfilment/fulfill?orderId=%s",
        orderId);

    String answer = restTemplate.postForObject(url, null, String.class);
    System.out.println(answer);
  }
}
