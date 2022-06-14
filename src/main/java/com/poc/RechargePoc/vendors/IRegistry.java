package com.poc.RechargePoc.vendors;

/**
 * The interface Registry.
 *
 * @param <P> the type parameter
 * @param <Q> the type parameter
 */
public interface IRegistry<P, Q> {
  /**
   * Register.
   *
   * @param key   the key
   * @param value the value
   */
  void register(final P key, final Q value);

  /**
   * Get q.
   *
   * @param key the key
   * @return the q
   */
  Q get(final P key);
}
