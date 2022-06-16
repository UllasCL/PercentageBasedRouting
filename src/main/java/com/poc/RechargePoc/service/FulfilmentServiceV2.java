package com.poc.RechargePoc.service;

import com.poc.RechargePoc.vendors.FulfilmentRegistry;
import com.poc.RechargePoc.vendors.VendorRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Fulfilment service v 2.
 */
@Slf4j
@Service
@AllArgsConstructor
public class FulfilmentServiceV2 {
  /**
   * The Fulfilment registry.
   */
  private final FulfilmentRegistry fulfilmentRegistry;

  /**
   * The Vendor registry.
   */
  private final VendorRegistry vendorRegistry;

  /**
   * Fulfil order string.
   *
   * @param orderId  the order id
   * @param operator the operator
   * @return the string
   */
  public String fulfilOrder(final String orderId, final String operator) {
    var vendor = vendorRegistry.get(operator).findVendor(orderId);
    if (vendor.isEmpty()) {
      log.error("Double fallback happened for order id {}", orderId);
      return "double fallback";
    }
    var result = fulfilmentRegistry.get(vendor).processFulfillment(orderId);
    if (result.isEmpty()) {
      log.error("Fulfilment Failed");
    }
    return result;
  }

  /**
   * Print.
   *
   * @param operator the operator
   */
  public void print(final String operator) {
    vendorRegistry.get(operator).print();
  }
}
