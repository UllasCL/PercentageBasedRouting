//package com.poc.RechargePoc.testCases;
//
//import com.poc.RechargePoc.service.FulfilmentService;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class WithFallback {
//  public static void main(String[] args) {
//    // TODO Fallback based on request number so save req number + Selected vendor
//    // TODO If same vendor got selected on fallback ignore and try again.
//    var fulfilmentService = new FulfilmentService();
//
//    var SS = 0;
//    var PAY1 =0;
//    var JRI = 0;
//    Map<Integer,String> requestWiseVendor = new ConcurrentHashMap<>();
//
//    for (int i=0; i< 100; i++) {
//      var selectedVendor = fulfilmentService.fulfilOrder();
//      if (selectedVendor == null){
//        break;
//      }
//      switch(selectedVendor){
//        case "SS":{ SS++;
//          requestWiseVendor.put(i,"SS");
//          break;}
//        case "JRI":{ JRI++;
//          requestWiseVendor.put(i,"JRI");
//          break;}
//        case "PAY1":{ PAY1++;
//          requestWiseVendor.put(i,"PAY1");
//          break;
//        }
//      }
//      if (i==19){
//        var vendor = fulfilmentService.fulfilOrder();
//        while(vendor.equals(requestWiseVendor.get(i))){
//          vendor = fulfilmentService.fulfilOrder();
//        }
//        System.out.println("Fallback vendor"+vendor);
//      }
//      if (i==60){
//        var vendor = fulfilmentService.fulfilOrder();
//        while(vendor.equals(requestWiseVendor.get(i))){
//          vendor = fulfilmentService.fulfilOrder();
//        }
//        System.out.println("Fallback vendor"+vendor);
//      }
//    }
//    System.out.println("SS :"+SS);
//    System.out.println("JRI :"+JRI);
//    System.out.println("PAY1 :"+PAY1);
//
//    requestWiseVendor.forEach((key, value) -> System.out.println(key + " " + value));
//  }
//}
