package com.poc.RechargePoc.service;

import com.poc.RechargePoc.component.VendorSelectionComponent;
import com.poc.RechargePoc.constants.Constants;
import com.poc.RechargePoc.vendors.FulfilmentRegistry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Fulfilment service.
 */
@Slf4j
@Service
public class FulfilmentService {

  /**
   * The Vendor selection component.
   */
  @Autowired
  private VendorSelectionComponent vendorSelectionComponent;

  /**
   * The Fulfilment registry.
   */
  @Autowired
  private FulfilmentRegistry fulfilmentRegistry;
  /**
   * The constant vendorsMap.
   */
  public static Map<String, Integer> vendorsMap = new ConcurrentHashMap<>();

  static {
    vendorsMap.put(Constants.SS, 40);
    vendorsMap.put(Constants.PAY1, 35);
    vendorsMap.put(Constants.JRI, 25);
  }

  /**
   * The constant orderVendor.
   */
  public static Map<String, String> orderVendor = new ConcurrentHashMap<>();

  /**
   * The constant totalRequest.
   */
  private static int totalRequest = 0;
  /**
   * The constant SS.
   */
  private static int SS = 0;
  /**
   * The constant PAY1.
   */
  private static int PAY1 = 0;
  /**
   * The constant JRI.
   */
  private static int JRI = 0;

  /**
   * The constant SS.
   */
  private static int FALLBACK_SS = 0;
  /**
   * The constant PAY1.
   */
  private static int FALLBACK_PAY1 = 0;
  /**
   * The constant JRI.
   */
  private static int FALLBACK_JRI = 0;
  /**
   * The constant totalFallbackRequest.
   */
  private static int totalFallbackRequest = 0;
  /**
   * The constant totalFallbackOnSaveVendor.
   */
  private static int totalFallbackOnSameVendor = 0;

  /**
   * Fulfil order string.
   *
   * @param orderId the order id
   * @return the string
   */
  public String fulfilOrder(String orderId) throws Exception {
    return fulfilmentRegistry.get(getVendor(orderId)).processCallback(orderId);
  }

  /**
   * Gets vendor.
   *
   * @param orderId the order id
   * @return the vendor
   */
  private String getVendor(String orderId) throws Exception {
    //    var fallbackCount = 0; // max allowed is 2
    totalRequest++;
    var selectedVendor = vendorSelectionComponent.getVendor(vendorsMap);
    if (selectedVendor == null) {
      log.error("Invalid vendor selected");
      return Strings.EMPTY;
    }
    if (orderVendor.containsKey(orderId)) {
      //      log.info("First vendor {} for orderId {}", orderVendor.get(orderId), orderId);
      while (selectedVendor.equals(orderVendor.get(orderId))) {
        //        fallbackCount++;
        selectedVendor = vendorSelectionComponent.getVendor(vendorsMap);
        totalFallbackOnSameVendor++;
        //        if (fallbackCount > 2) {
        //          throw new Exception("Invalid vendor case");
        //        }
      }
      orderVendor.put(orderId, selectedVendor);
      //      log.info("Fallback vendor {} for orderId {}", selectedVendor, orderId);
      totalFallbackRequest++;
      updateFallbackVendors(selectedVendor);
    }
    orderVendor.put(orderId, selectedVendor);
    log.info("Selected vendor {} for orderId {}\n", selectedVendor, orderId);
    updateVendors(selectedVendor);
    if (totalRequest % 100 == 0) {
      print();
    }
    return selectedVendor;
  }

  /**
   * Update fallback vendors.
   *
   * @param selectedVendor the selected vendor
   */
  private void updateFallbackVendors(final String selectedVendor) {
    switch (selectedVendor) {
      case "SS": {
        FALLBACK_SS++;
        break;
      }
      case "JRI": {
        FALLBACK_JRI++;
        break;
      }
      case "PAY1": {
        FALLBACK_PAY1++;
        break;
      }
    }
  }

  /**
   * Update vendors.
   *
   * @param selectedVendor the selected vendor
   */
  private void updateVendors(final String selectedVendor) {
    switch (selectedVendor) {
      case "SS": {
        SS++;
        break;
      }
      case "JRI": {
        JRI++;
        break;
      }
      case "PAY1": {
        PAY1++;
        break;
      }
    }
  }

  /**
   * Print.
   */
  private void print() {
    log.info(Constants.SS + "% {} ", (SS * 100 / totalRequest));
    log.info(Constants.PAY1 + "% {} ", (PAY1 * 100 / totalRequest));
    log.info(Constants.JRI + "% {} ", (JRI * 100 / totalRequest));

    log.info("Total requests {} ", totalRequest);
    log.info("Total fallback requests {} ", totalFallbackRequest);
    log.info("Total same vendor fallback {} ", totalFallbackOnSameVendor);

    log.info(Constants.SS + " served {} fallback requests.", FALLBACK_SS);
    log.info(Constants.PAY1 + " served {} fallback requests.", FALLBACK_PAY1);
    log.info(Constants.JRI + " served {} fallback requests.", FALLBACK_JRI);

  }
}
