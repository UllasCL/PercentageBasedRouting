package com.poc.RechargePoc.controllers;

import com.poc.RechargePoc.service.FulfilmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

  @Autowired
  private FulfilmentService fulfilmentService;

  /**
   * Gets app version.
   *
   * @param orderId the order id
   * @return the app version
   */
  @PostMapping(path = {"/fulfill"}, produces = {MediaType.TEXT_PLAIN_VALUE})
  public ResponseEntity<String> fullFilOrder(@RequestParam final String orderId) {
    return new ResponseEntity<>(String.format("Selected vendor %s for order id: %s",
        fulfilmentService.fulfilOrder(orderId), orderId), HttpStatus.OK);
  }
}

