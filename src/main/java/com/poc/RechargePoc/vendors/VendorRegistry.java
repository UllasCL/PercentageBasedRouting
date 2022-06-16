package com.poc.RechargePoc.vendors;

/**
 * The type Vendor registry.
 */
public class VendorRegistry implements IRegistry<String, IVendorHandler> {
  /**
   * Register.
   *
   * @param key   the key
   * @param value the value
   */
  @Override
  public void register(final String key, final IVendorHandler value) {

  }

  /**
   * Get vendor handler.
   *
   * @param key the key
   * @return the vendor handler
   */
  @Override
  public IVendorHandler get(final String key) {
    return null;
  }
}
