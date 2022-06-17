package com.poc.RechargePoc.vendors;

/**
 * The interface Call back handler.
 */
public interface IFulfilmentHandler {

  /**
   * Process callback.
   *
   * @param orderId  the order id
   * @param operator the operator
   * @return the string
   */
  String processFulfillment(final String orderId, final String operator);

  /**
   * Register.
   */
  void register();

}
