package com.poc.RechargePoc.vendors.dummyCall;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
   * @throws Exception the exception
   */
  //   @CircuitBreaker(name = "ss", fallbackMethod = "getAllAvailableProducts")
  public void dummySSCall() throws Exception {
    SS_COUNT++;
    if (SS_COUNT > 5) {
      throw new Exception("SS crossed 5 requests");
    }
  }

  /**
   * Dummy pay 1 call.
   *
   * @throws Exception the exception
   */
  // @CircuitBreaker(name = "payOne", fallbackMethod = "getAllAvailableProducts")
  public void dummyPAY1Call() throws Exception {
    PAY1_COUNT++;
    if (PAY1_COUNT > 10) {
      throw new Exception("PAY1 crossed 10 requests");
    }
  }

  /**
   * Dummy jri call.
   *
   * @param orderId the order id
   */
  @CircuitBreaker(name = "jri", fallbackMethod = "test")
  public String dummyJRICall(String orderId) {
    JRI_COUNT++;
    RestTemplate restTemplate = new RestTemplate();
    String url = String.format("http://localhost:8081/test/%s", orderId);
    restTemplate.postForObject(url, null, String.class);
    return "success";
  }

  /**
   * Test.
   */
  private String test(Exception e) {
    log.info("fallback");
    return "fallback";
  }
}
