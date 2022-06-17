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
//    callOrderApis();
        printResultForAllOperators();
  }

  /**
   * Call 100 ap is.
   *
   * @param operator        the operator
   * @param numberOfApiCall the number of api call
   */
  private static void callAPIs(final String operator, final Integer numberOfApiCall) {
    for (int i = 0; i < numberOfApiCall; i++) {
      callApi(i, operator);
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
   * @param orderId  the order id
   * @param operator the operator
   */
  private static void callApi(final Integer orderId, final String operator) {
    RestTemplate restTemplate = new RestTemplate();

    String url = String.format("http://localhost:8080/recharge/poc/fulfilment/fulfill?orderId=%s"
        + "&operator=%s", orderId, operator);

    String answer = restTemplate.postForObject(url, null, String.class);
    System.out.println(answer);
  }

  private static void callOrderApis(){
    new Thread(() -> callAPIs("AIRTEL", Constants._10kRequests)).start();
    new Thread(() -> callAPIs("JIO", Constants._10kRequests)).start();
    new Thread(() ->  callAPIs("VI", Constants._10kRequests)).start();
  }
  /**
   * Print result for all operators.
   */
  private static void printResultForAllOperators() {
    print("AIRTEL");
    print("JIO");
    print("VI");
  }

  /**
   * Print.
   *
   * @param operator the operator
   */
  private static void print(final String operator) {
    RestTemplate restTemplate = new RestTemplate();

    String url = String.format("http://localhost:8080/recharge/poc/fulfilment/print?operator=%s",
        operator);

    String answer = restTemplate.postForObject(url, null, String.class);
    System.out.println(answer);
  }
}
