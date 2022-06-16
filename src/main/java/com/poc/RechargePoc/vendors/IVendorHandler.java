package com.poc.RechargePoc.vendors;

/**
 * The interface Vendor handler.
 */
public interface IVendorHandler {
  /**
   * Find vendor string.
   *
   * @param orderId the order id
   * @return the string
   */
  String findVendor(final String orderId);

  /**
   * Register.
   */
  void register();

  /**
   * Print.
   */
  void print();
}
