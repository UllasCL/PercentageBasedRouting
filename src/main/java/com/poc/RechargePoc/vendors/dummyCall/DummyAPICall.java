package com.poc.RechargePoc.vendors.dummyCall;

import com.poc.RechargePoc.constants.Constants;
import com.poc.RechargePoc.service.FulfilmentService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The type Dummy api call.
 */
@Slf4j
@Service
public class DummyAPICall {

  /**
   * The Is fallback required.
   */
  @Value("${fallback.isRequired}")
  private Boolean isFallbackRequired;

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
   * Fallback string.
   *
   * @param orderId the order id
   * @param vendor  the vendor
   * @param e       the e
   * @return the string
   */
  private String fallback(String orderId, String vendor, CallNotPermittedException e) {
    log.error("Circuit is opened for vendor {}", vendor);
    log.info("Fallback orderId {} and old vendor is {}", orderId, vendor);
    updateCircuitOpen(vendor);
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
    if ((random.nextInt(100) < Constants.FAILURE_RATE) && isFallbackRequired) {
      //log.info("Order id {} random number less than {}", orderId, 10);
      url = String.format(
          "http://localhost:8080/recharge/poc/fulfilment/invalid?orderId=%s", orderId);
    }
    restTemplate.postForObject(url, null, String.class);
    log.info("Order fulfilled for order Id {} \n", orderId);
    return vendor;
  }

  /**
   * Call api again string.
   *
   * @param orderId the order id
   * @param vendor  the vendor
   */
  private void rescheduledCall(String orderId, String vendor) {
    log.info("Rescheduled orderId {} \n", orderId);

    RestTemplate restTemplate = new RestTemplate();
    String url = String.format("http://localhost:8080/recharge/poc/fulfilment/fulfill?orderId=%s",
        orderId);

    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    restTemplate.postForObject(url, null, String.class);
  }

  /**
   * Update circuit open.
   *
   * @param selectedVendor the selected vendor
   */
  private void updateCircuitOpen(final String selectedVendor) {
    switch (selectedVendor) {
      case "SS": {
        FulfilmentService.SS_CIRCUIT_OPEN++;
        break;
      }
      case "JRI": {
        FulfilmentService.JRI_CIRCUIT_OPEN++;
        break;
      }
      case "PAY1": {
        FulfilmentService.PAY1_CIRCUIT_OPEN++;
        break;
      }
    }
  }

}
