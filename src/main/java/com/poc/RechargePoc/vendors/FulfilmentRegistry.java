package com.poc.RechargePoc.vendors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Fulfilment registry.
 */
@Slf4j
@Component
public class FulfilmentRegistry implements IRegistry<String, IFulfilmentHandler> {

  /**
   * The constant FulfilmentRegistry.
   */
  private static final Map<String, IFulfilmentHandler> FulfilmentRegistry =
      new ConcurrentHashMap<>();

  /**
   * Register.
   *
   * @param key   the key
   * @param value the value
   */
  @Override
  public void register(final String key, final IFulfilmentHandler value) {
    FulfilmentRegistry.put(key, value);

  }

  /**
   * Get fulfilment handler.
   *
   * @param key the key
   * @return the fulfilment handler
   */
  @Override
  public IFulfilmentHandler get(final String key) {
    if (!FulfilmentRegistry.containsKey(key)) {
      log.error("Invalid request");
    }
    return FulfilmentRegistry.get(key);
  }
}
