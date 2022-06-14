package com.poc.RechargePoc.vendors;

/**
 * The interface Call back handler.
 */
public interface IFulfilmentHandler {

  /**
   * Process callback.
   *
   * @param responseBody the response body
   */
  String processCallback(final String responseBody);

  /**
   * Register.
   */
  void register();

}
