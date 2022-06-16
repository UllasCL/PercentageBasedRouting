package com.poc.RechargePoc.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Vendor selection component.
 */
@Slf4j
@Component
public class VendorSelectionComponent {

  /**
   * The Server list.
   */
  List<String> serverList = new ArrayList<>();
  private static Integer position = 0;

  /**
   * Gets vendor.
   *
   * @param vendors the vendors
   * @return the vendor
   */
  public String getVendor(Map<String, Integer> vendors) {

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
}
