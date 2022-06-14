package com.poc.RechargePoc.vendors.dummyCall;

import com.poc.RechargePoc.constants.Constants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The type Dummy api call.
 */
@Slf4j
@Service
public class DummyAPICall {

  /**
   * The constant SS_COUNT.
   */
  private static int SS_COUNT = 0;
  /**
   * The constant PAY1_COUNT.
   */
  private static int PAY1_COUNT = 0;
  /**
   * The constant JRI_COUNT.
   */
  private static int JRI_COUNT = 0;

  /**
   * Dummy ss call.
   *
   * @param orderId the order id
   * @return the string
   * @throws Exception the exception
   */
  @CircuitBreaker(name = "ss", fallbackMethod = "fallback")
  public String dummySSCall(String orderId) throws Exception {
    SS_COUNT++;
    RestTemplate restTemplate = new RestTemplate();
    String url = String.format(
        "http://localhost:8080/recharge/poc/fulfilment/randomSuccess?orderId=%s", orderId);
    restTemplate.postForObject(url, null, String.class);
    return Constants.SS;
  }

  /**
   * Dummy pay 1 call.
   *
   * @param orderId the order id
   * @return the string
   * @throws Exception the exception
   */
  @CircuitBreaker(name = "payOne", fallbackMethod = "fallback")
  public String dummyPAY1Call(String orderId) throws Exception {
    PAY1_COUNT++;
    RestTemplate restTemplate = new RestTemplate();

    String url = String.format(
        "http://localhost:8080/recharge/poc/fulfilment/randomSuccess?orderId=%s", orderId);
    restTemplate.postForObject(url, null, String.class);
    return Constants.PAY1;
  }

  /**
   * Dummy jri call.
   *
   * @param orderId the order id
   * @return the string
   */
  @CircuitBreaker(name = "jri", fallbackMethod = "fallback")
  public String dummyJRICall(String orderId) {
    JRI_COUNT++;
    RestTemplate restTemplate = new RestTemplate();
    String url = String.format(
        "http://localhost:8080/recharge/poc/fulfilment/randomSuccess?orderId=%s", orderId);
    restTemplate.postForObject(url, null, String.class);
    return Constants.JRI;
  }

  /**
   * Test.
   *
   * @param e the e
   * @return the string
   */
  private String fallback(Exception e) {
    log.info("fallback");
    // TODO Reschedule fulfillment with simulated time gap.
    return "fallback";
  }
}
