package com.poc.RechargePoc.controllers;

import com.poc.RechargePoc.service.FulfilmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Fulfillment controller.
 */
@RestController
@RequestMapping("/fulfilment")
public class FulfillmentController {

  /**
   * The Fulfilment service.
   */
  @Autowired
  private FulfilmentService fulfilmentService;

  /**
   * Gets app version.
   *
   * @param orderId  the order id
   * @param operator the operator
   * @return the app version
   */
  @PostMapping(path = {"/fulfill"}, produces = {MediaType.TEXT_PLAIN_VALUE})
  public ResponseEntity<String> fullFilOrder(
      @RequestParam final String orderId,
      @RequestParam final String operator) {
    return new ResponseEntity<>(String.format("Selected vendor %s for order id: %s",
        fulfilmentService.fulfilOrder(orderId, operator), orderId), HttpStatus.OK);
  }

  /**
   * Random success response entity.
   *
   * @param orderId the order id
   * @return the response entity
   */
  @PostMapping(path = {"/randomSuccess"}, produces = {MediaType.TEXT_PLAIN_VALUE})
  public ResponseEntity<String> randomSuccess(@RequestParam final String orderId) {
    return new ResponseEntity<>(String.format("Random %s for order id: %s",
        fulfilmentService.randomSuccess(orderId), orderId), HttpStatus.OK);
  }

  /**
   * Print response entity.
   *
   * @return the response entity
   */
  @GetMapping(path = {"/print"})
  public ResponseEntity print() {
    fulfilmentService.print();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

