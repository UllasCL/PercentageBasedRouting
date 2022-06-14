package com.poc.RechargePoc;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * The type Recharge poc application tests.
 */
@SpringBootTest
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
public class RechargePocApplicationTests {
	/**
	 * The Web test client.
	 */
	@Autowired
  private WebTestClient webTestClient;

	/**
	 * Test.
	 *
	 * @param repetitionInfo the repetition info
	 */
	@RepeatedTest(10)
  public void test(RepetitionInfo repetitionInfo) {
    int orderId = 1 + 1;
    webTestClient.get()
        .uri("http://localhost:8080/recharge/poc/fulfilment/fulfill?orderId={orderId}", orderId)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchange()
        .expectStatus()
        .isOk();
  }
}

