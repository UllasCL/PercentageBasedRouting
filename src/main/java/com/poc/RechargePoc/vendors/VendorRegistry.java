package com.poc.RechargePoc.vendors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Vendor registry.
 */
@Slf4j
@Component
public class VendorRegistry implements IRegistry<String, IVendorHandler> {

  /**
   * The constant VendorRegistry.
   */
  private static final Map<String, IVendorHandler> VendorRegistry =
      new ConcurrentHashMap<>();

  /**
   * Register.
   *
   * @param key   the key
   * @param value the value
   */
  @Override
  public void register(final String key, final IVendorHandler value) {
    VendorRegistry.put(key, value);
  }

  /**
   * Get vendor handler.
   *
   * @param key the key
   * @return the vendor handler
   */
  @Override
  public IVendorHandler get(final String key) {
    if (!VendorRegistry.containsKey(key)) {
      log.error("Invalid request");
    }
    return VendorRegistry.get(key);
  }
}
