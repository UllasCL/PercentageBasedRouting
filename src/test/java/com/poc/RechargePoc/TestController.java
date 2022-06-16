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
//    callAPIs();
        print();
  }

  /**
   * Call 100 ap is.
   */
  private static void callAPIs() {
    for (int i = 0; i < Constants.numberOfRequests; i++) {
      callApi(i);
//      try {
//        Thread.sleep(500);
//      } catch (InterruptedException e) {
//        System.out.println(e.getMessage());
//      }
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


  /**
   * Print.
   */
  private static void print() {
    RestTemplate restTemplate = new RestTemplate();

    String url = "http://localhost:8080/recharge/poc/fulfilment/print";

    String answer = restTemplate.getForObject(url, String.class);
    System.out.println(answer);
  }
}
