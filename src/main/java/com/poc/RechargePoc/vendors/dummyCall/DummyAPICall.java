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

  //  /**
  //   * The Jri.
  //   */
  //  @Autowired
  //  private CircuitBreaker jri;

  /**
   * Dummy ss call.
   *
   * @param orderId the order id
   * @return the string
   */
    @CircuitBreaker(name = "ss", fallbackMethod = "fallback")
  public String dummySSCall(String orderId, String vendor) {
    SS_COUNT++;
    return callRandomSuccess(orderId, Constants.SS);
  }

  /**
   * Dummy pay 1 call.
   *
   * @param orderId the order id
   * @return the string
   */
    @CircuitBreaker(name = "payOne", fallbackMethod = "fallback")
  public String dummyPAY1Call(String orderId,String vendor) {
    PAY1_COUNT++;
    return callRandomSuccess(orderId, Constants.PAY1);
  }

  /**
   * Dummy jri call.
   *
   * @param orderId the order id
   * @return the string
   */
  @CircuitBreaker(name = "jri", fallbackMethod = "fallback")
  public String dummyJRICall(String orderId,String vendor) {
    JRI_COUNT++;
    return callRandomSuccess(orderId, Constants.JRI);
  }

  /**
   * Test.
   *
   * @param orderId the order id
   * @param e       the e
   * @return the string
   */
  private String fallback(String orderId,String vendor, Exception e) {
    log.info("fallback orderId {} and vendor is {}",orderId, vendor);
    // TODO Reschedule fulfillment with simulated time gap.
    return e.getMessage();
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
    String url= String.format(
        "http://localhost:8080/recharge/poc/fulfilment/randomSuccess?orderId=%s", orderId);
    if(Integer.parseInt(orderId)%5==0){
       url = String.format(
          "http://localhost:8080/recharge/poc/fulfilment/invalid?orderId=%s", orderId);
    }

    restTemplate.postForObject(url, null, String.class);
    return vendor;
  }
}
