package com.poc.RechargePoc.service;

import com.poc.RechargePoc.component.VendorSelectionComponent;
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

  @Autowired
  private VendorSelectionComponent vendorSelectionComponent;
  /**
   * The constant vendorsMap.
   */
  public static Map<String, Integer> vendorsMap = new ConcurrentHashMap<>();

  static {
    vendorsMap.put("SS", 40);
    vendorsMap.put("PAY1", 35);
    vendorsMap.put("JRI", 25);
  }

  /**
   * The constant orderVendor.
   */
  public static Map<String, String> orderVendor = new ConcurrentHashMap<>();

  private static int totalRequest = 0;
  private static int SS = 0;
  private static int PAY1 = 0;
  private static int JRI = 0;

  /**
   * Fulfil order string.
   *
   * @param orderId the order id
   * @return the string
   */
  public String fulfilOrder(String orderId) {
    totalRequest++;
    var selectedVendor = vendorSelectionComponent.getVendor(vendorsMap);
    if (selectedVendor == null){
      log.error("Invalid vendor selected");
      return Strings.EMPTY;
    }
    if (orderVendor.containsKey(orderId)) {
      log.info("First vendor {} for orderId {}", orderVendor.get(orderId), orderId);
      while (selectedVendor.equals(orderVendor.get(orderId))) {
        selectedVendor = vendorSelectionComponent.getVendor(vendorsMap);
      }
      orderVendor.put(orderId, selectedVendor);
      log.info("Fallback vendor {} for orderId {}", selectedVendor, orderId);
    }
    orderVendor.put(orderId, selectedVendor);
    log.info("Selected vendor {} for orderId {}\n", selectedVendor, orderId);
    updateVendors(selectedVendor);
    if (totalRequest % 100 ==0) {
      print();
    }
    return selectedVendor;
  }

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

  private void print() {
    log.info("SS :" + SS);
    log.info("JRI :" + JRI);
    log.info("PAY1 :" + PAY1);
  }
}
