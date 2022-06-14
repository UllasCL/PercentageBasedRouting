package com.poc.RechargePoc.vendors.impl;

import com.poc.RechargePoc.constants.Constants;
import com.poc.RechargePoc.vendors.FulfilmentRegistry;
import com.poc.RechargePoc.vendors.IFulfilmentHandler;
import com.poc.RechargePoc.vendors.dummyCall.DummyAPICall;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

/**
 * The type Jri fulfilment.
 */
@Slf4j
@Component
@AllArgsConstructor
public class JRIFulfilment implements IFulfilmentHandler {

  /**
   * The Fulfilment registry.
   */
  private final FulfilmentRegistry fulfilmentRegistry;

  /**
   * The Dummy api call.
   */
  private final DummyAPICall dummyAPICall;

  /**
   * Process callback.
   *
   * @param orderId the order id
   * @return the string
   */
  @Override
  public String processCallback(final String orderId) {
    log.info("JRI fulfilment initiated");
    try {
      return dummyAPICall.dummyJRICall(orderId, Constants.JRI);
    } catch (Exception e) {
      log.error("JRI fulfilment failed");
    }
    return Strings.EMPTY;
  }

  /**
   * Register.
   */
  @PostConstruct
  @Override
  public void register() {
    fulfilmentRegistry.register(Constants.JRI, this);
  }
}
