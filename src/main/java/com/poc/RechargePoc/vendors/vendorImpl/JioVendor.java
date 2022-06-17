package com.poc.RechargePoc.vendors.vendorImpl;

import com.poc.RechargePoc.constants.Constants;
import com.poc.RechargePoc.vendors.IVendorHandler;
import com.poc.RechargePoc.vendors.VendorRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;


/**
 * The type Airtel vendor.
 */
@Slf4j
@Component
@AllArgsConstructor
public class JioVendor implements IVendorHandler {

  /**
   * The Vendor registry.
   */
  private final VendorRegistry vendorRegistry;

  /**
   * The Server list.
   */
  List<String> serverList;
  /**
   * The constant position.
   */
  private static Integer position = 0;
  /**
   * The constant orderVendor.
   */
  public static Map<String, String> orderVendor = new ConcurrentHashMap<>();

  /**
   * The constant orderFallback.
   */
  public static Map<String, Integer> orderFallback = new ConcurrentHashMap<>();
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
  public static int SS_CIRCUIT_OPEN = 0;
  /**
   * The constant PAY1.
   */
  public static int PAY1_CIRCUIT_OPEN = 0;
  /**
   * The constant JRI.
   */
  public static int JRI_CIRCUIT_OPEN = 0;
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
   * The constant totalDoubleFallback.
   */
  private static int totalDoubleFallbackRequests = 0;

  /**
   * Find vendor string.
   *
   * @param orderId the order id
   * @return the string
   */
  @Override
  public String findVendor(final String orderId) {
    totalRequest++;
    var selectedVendor = getVendor(Constants.jioVendorsMap);
    if (orderVendor.containsKey(orderId)) {
      log.info("First vendor was {} for orderId {}", orderVendor.get(orderId), orderId);
      while (selectedVendor.equals(orderVendor.get(orderId))) {
        selectedVendor = getVendor(Constants.jioVendorsMap);
        totalFallbackOnSameVendor++;
        orderFallback.put(orderId, orderFallback.get(orderId) + Constants.ONE);
        if (orderFallback.get(orderId) > Constants.FALLBACK_COUNT) {
          log.error("Invalid vendor selected");
          totalDoubleFallbackRequests++;
          return Strings.EMPTY;
        }
      }
      orderVendor.put(orderId, selectedVendor);
      // log.info("Fallback vendor {} for orderId {}", selectedVendor, orderId);
      totalFallbackRequest++;
      updateFallbackVendors(selectedVendor);
    } else {
      orderFallback.put(orderId, Constants.ZERO);
    }
    orderVendor.put(orderId, selectedVendor);
    log.info("Selected vendor {} for orderId {}", selectedVendor, orderId);
    updateVendors(selectedVendor);
    return selectedVendor;
  }

  /**
   * Register.
   */
  @Override
  @PostConstruct
  public void register() {
    vendorRegistry.register(Constants.JIO, this);
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
  @Override
  public void print() {

    log.info("\n-------------------------------------------------------------------JIO Result"
        + "-------------------------------------------------------------------\n");

    log.info("\n----------------------------------------------------------Requested % distribution"
        + "-------------------------------------------------------------------\n");

    Constants.jioVendorsMap.forEach((key, value) -> log.info("{} {}", key, value));

    log.info("\n----------------------------------------------------------Actual % distribution"
        + "-------------------------------------------------------------------\n");
    log.info(Constants.SS + "% {} ", (SS * 100 / (float) totalRequest));
    log.info(Constants.PAY1 + "% {} ", (PAY1 * 100 / (float) totalRequest));
    log.info(Constants.JRI + "% {} \n", (JRI * 100 / (float) totalRequest));

    log.info("\n----------------------------------------------------------Requested data"
        + "-------------------------------------------------------------------\n");
    log.info("Total requests {} ", totalRequest);
    log.info("Total requests without fallback {} ", totalRequest - totalFallbackRequest);
    log.info("Total fallback requests {} \n", totalFallbackRequest);
    log.info("Total same vendor fallback {} ", totalFallbackOnSameVendor);
    log.info("Total double fallback requests {} ", totalDoubleFallbackRequests);

    log.info("\n----------------------------------------------------------Fallback data"
        + "-------------------------------------------------------------------\n");
    log.info(Constants.SS + " served {} fallback requests.", FALLBACK_SS);
    log.info(Constants.PAY1 + " served {} fallback requests.", FALLBACK_PAY1);
    log.info(Constants.JRI + " served {} fallback requests.\n", FALLBACK_JRI);

    log.info("\n----------------------------------------------------------Fallback % distribution"
        + "-------------------------------------------------------------------\n");
    log.info(Constants.SS + "fallback % {} ", (FALLBACK_SS * 100 / (float) totalRequest));
    log.info(Constants.PAY1 + "fallback % {} ", (FALLBACK_PAY1 * 100 / (float) totalRequest));
    log.info(Constants.JRI + "fallback % {} \n", (FALLBACK_JRI * 100 / (float) totalRequest));

    log.info("\n\n----------------------------------------------------------Circuit break data"
        + "-------------------------------------------------------------------\n");
    log.info("Total requests when SS circuit is open {} ", SS_CIRCUIT_OPEN);
    log.info("Total requests when PAY1 circuit is open {} ", PAY1_CIRCUIT_OPEN);
    log.info("Total requests when JRI circuit is open {} \n", JRI_CIRCUIT_OPEN);

    //    orderFallback.forEach((key, value) -> {
    //      if (value > 2) {
    //        log.info("{} {}", key, value);
    //      }
    //    });
  }

  /**
   * Gets vendor.
   *
   * @param vendors the vendors
   * @return the vendor
   */
  private String getVendor(Map<String, Integer> vendors) {

    Set<String> servers = vendors.keySet();

    Iterator<String> iterator = servers.iterator();
    if (serverList.isEmpty()) {
      while (iterator.hasNext()) {
        String serverItem = iterator.next();
        Integer weight = vendors.get(serverItem);
        if (weight > 0) {
          for (int i = 0; i < weight; i++) {
            serverList.add(serverItem);
          }
        }
      }
      log.info("initial serverList {}", serverList);
      Collections.shuffle(serverList, new Random(36236553229450L));
      log.info("post shuffle serverList {}", serverList);
    }

    synchronized (position) {
      if (position >= serverList.size()) {
        position = 0;
        log.info("Position crossed serverList size");
      }

      String target = serverList.get(position);
      position++;
      return target;
    }
  }

  /**
   * Clear stored data.
   */
  @Override
  public void clear() {
    FALLBACK_PAY1 = 0;
    FALLBACK_JRI = 0;
    FALLBACK_SS = 0;
    SS = 0;
    PAY1 = 0;
    JRI = 0;
    totalRequest = 0;
    totalFallbackRequest = 0;
    totalFallbackOnSameVendor = 0;
    orderVendor.clear();
    orderFallback.clear();
    log.info("Jio stored data cleared");
  }
}
