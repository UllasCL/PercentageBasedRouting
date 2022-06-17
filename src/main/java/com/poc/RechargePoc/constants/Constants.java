package com.poc.RechargePoc.constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Constants.
 */
public class Constants {
  /**
   * The constant SS.
   */
  public static String SS = "SS";
  /**
   * The constant PAY1.
   */
  public static String PAY1 = "PAY1";
  /**
   * The constant JRI.
   */
  public static String JRI = "JRI";
  /**
   * The constant AIRTEL.
   */
  public static String AIRTEL = "AIRTEL";
  /**
   * The constant JIO.
   */
  public static String JIO = "JIO";
  /**
   * The constant VI.
   */
  public static String VI = "VI";
  /**
   * The constant ZERO.
   */
  public static Integer ZERO = 0;
  /**
   * The constant ONE.
   */
  public static Integer ONE = 1;

  /**
   * The constant airtelVendorsMap.
   */
  public static Map<String, Integer> airtelVendorsMap = new ConcurrentHashMap<>();
  /**
   * The constant jioVendorsMap.
   */
  public static Map<String, Integer> jioVendorsMap = new ConcurrentHashMap<>();
  /**
   * The constant ViVendorsMap.
   */
  public static Map<String, Integer> viVendorsMap = new ConcurrentHashMap<>();

  static {
    airtelVendorsMap.put(Constants.SS, 35);
    airtelVendorsMap.put(Constants.PAY1, 50);
    airtelVendorsMap.put(Constants.JRI, 15);
  }

  static {
    jioVendorsMap.put(Constants.SS, 25);
    jioVendorsMap.put(Constants.PAY1, 25);
    jioVendorsMap.put(Constants.JRI, 50);
  }

  static {
    viVendorsMap.put(Constants.SS, 70);
    viVendorsMap.put(Constants.PAY1, 20);
    viVendorsMap.put(Constants.JRI, 10);
  }

  /**
   * The constant FALLBACK_COUNT.
   */
  public static Integer FALLBACK_COUNT = 2;
  /**
   * The constant SS.
   */
  public static Integer SS_PER = 40;
  /**
   * The constant PAY1.
   */
  public static Integer PAY1_PER = 35;
  /**
   * The constant JRI.
   */
  public static Integer JRI_PER = 25;
  /**
   * The constant FAILURE_RATE.
   */
  public static Integer FAILURE_RATE = 10;
}