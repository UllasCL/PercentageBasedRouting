package com.poc.RechargePoc.vendors.impl;

import com.poc.RechargePoc.constants.Constants;
import com.poc.RechargePoc.vendors.FulfilmentRegistry;
import com.poc.RechargePoc.vendors.IFulfilmentHandler;
import com.poc.RechargePoc.vendors.dummyCall.DummyAPICall;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  private DummyAPICall dummyAPICall;

  /**
   * Process callback.
   *
   * @param responseBody the response body
   */
  @Override
  public void processCallback(final String responseBody) {
    log.info("JRI fulfilment");
    try {
     var result = dummyAPICall.dummyJRICall(responseBody);
    } catch (Exception e) {
      log.error("JRI fulfilment failed");
    }
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
