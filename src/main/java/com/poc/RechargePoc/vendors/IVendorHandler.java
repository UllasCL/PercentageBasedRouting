package com.poc.RechargePoc.vendors;

/**
 * The interface Vendor handler.
 */
public interface IVendorHandler {
  /**
   * Find vendor string.
   *
   * @param responseBody the response body
   * @return the string
   */
  String findVendor(final String responseBody);

  /**
   * Register.
   */
  void register();
}
