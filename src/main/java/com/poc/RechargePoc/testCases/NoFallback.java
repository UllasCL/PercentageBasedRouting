//package com.poc.RechargePoc.testCases;
//
//
//import com.poc.RechargePoc.service.FulfilmentService;
//
//public class NoFallback {
//  public static void main(String[] args) {
//    var fulfilmentService = new FulfilmentService();
//
//    var SS = 0;
//    var PAY1 =0;
//    var JRI = 0;
//
//    for (int i=0; i< 100; i++) {
//      var selectedVendor = fulfilmentService.fulfilOrder("");
//      System.out.println(selectedVendor);
//      switch(selectedVendor){
//        case "SS":{ SS++;
//        continue;}
//        case "JRI":{ JRI++;
//          continue;}
//        case "PAY1":{ PAY1++;
//        }
//      }
//    }
//    System.out.println("SS :"+SS);
//    System.out.println("JRI :"+JRI);
//    System.out.println("PAY! :"+PAY1);
//  }
//}
