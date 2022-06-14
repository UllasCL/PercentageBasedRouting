package com.poc.RechargePoc.vendors.dummyCall;

import com.poc.RechargePoc.constants.Constants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Random;
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
   * Dummy ss call.
   *
   * @param orderId the order id
   * @param vendor  the vendor
   * @return the string
   */
  @CircuitBreaker(name = "ss", fallbackMethod = "fallback")
  public String dummySSCall(String orderId, String vendor) {
    return callRandomSuccess(orderId, Constants.SS);
  }

  /**
   * Dummy pay 1 call.
   *
   * @param orderId the order id
   * @param vendor  the vendor
   * @return the string
   */
  @CircuitBreaker(name = "payOne", fallbackMethod = "fallback")
  public String dummyPAY1Call(String orderId, String vendor) {
    return callRandomSuccess(orderId, Constants.PAY1);
  }

  /**
   * Dummy jri call.
   *
   * @param orderId the order id
   * @param vendor  the vendor
   * @return the string
   */
  @CircuitBreaker(name = "jri", fallbackMethod = "fallback")
  public String dummyJRICall(String orderId, String vendor) {
    return callRandomSuccess(orderId, Constants.JRI);
  }

  /**
   * Test.
   *
   * @param orderId the order id
   * @param vendor  the vendor
   * @param e       the e
   * @return the string
   */
  private String fallback(String orderId, String vendor, Exception e) {
    log.info("Fallback orderId {} and old vendor is {}", orderId, vendor);
    // Reschedule fulfillment with simulated time gap.
    rescheduledCall(orderId, vendor);
    return vendor;
  }

  /**
   * Call random success.
   *
   * @param orderId the order id
   * @param vendor  the vendor
   * @return the string
   */
  private String callRandomSuccess(String orderId, String vendor) {
    RestTemplate restTemplate = new RestTemplate();
    Random random = new Random();
    String url = String.format(
        "http://localhost:8080/recharge/poc/fulfilment/randomSuccess?orderId=%s", orderId);
    if (random.nextInt(100) < 10) {
      //log.info("Order id {} random number less than {}", orderId, 10);
      url = String.format(
          "http://localhost:8080/recharge/poc/fulfilment/invalid?orderId=%s", orderId);
    }
    restTemplate.postForObject(url, null, String.class);
    return vendor;
  }

  /**
   * Call api again string.
   *
   * @param orderId the order id
   * @param vendor  the vendor
   */
  private void rescheduledCall(String orderId, String vendor) {
    log.info("Rescheduled order {}", orderId);

    RestTemplate restTemplate = new RestTemplate();
    String url = String.format("http://localhost:8080/recharge/poc/fulfilment/fulfill?orderId=%s",
        orderId);

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    restTemplate.postForObject(url, null, String.class);
  }
}
