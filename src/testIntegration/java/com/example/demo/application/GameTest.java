package com.example.demo.application;

import com.example.demo.application.controller.GameController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameTest {

	@Autowired
	public TestRestTemplate restTemplate;

	@Test
	public void testGame() {
		ResponseEntity<String> response = restTemplate.postForEntity("/start", null, String.class);
		assertNotNull(response.getBody());

		UUID gameId = UUID.fromString(response.getBody());

		/*
		   A B C D E F G H I J
		 1 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 2 ~ X ~ ~ ~ ~ ~ ~ ~ ~
		 3 ~ X ~ ~ ~ ~ ~ ~ ~ ~
		 4 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 5 ~ ~ X X X X X ~ ~ ~
		 6 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 7 ~ ~ ~ ~ ~ X ~ X ~ ~
		 8 X X ~ ~ ~ X ~ X ~ ~
		 9 ~ ~ ~ ~ ~ X ~ X ~ ~
		10 ~ ~ ~ ~ ~ ~ ~ X ~ ~
		 */
		assertEquals("WATER", attack(gameId, 'A', 1));
		assertEquals("HIT", attack(gameId, 'B', 2));
		assertEquals("SUNK", attack(gameId, 'B', 3));
		assertEquals("HIT", attack(gameId, 'A', 8));
		assertEquals("SUNK", attack(gameId, 'B', 8));
		assertEquals("HIT", attack(gameId, 'C', 5));
		assertEquals("HIT", attack(gameId, 'D', 5));

		assertState(gameId, "" +
			"   A B C D E F G H I J\n" +
			" 1 ~ . . . . . . . . .\n" +
			" 2 . √ . . . . . . . .\n" +
			" 3 . √ . . . . . . . .\n" +
			" 4 . . . . . . . . . .\n" +
			" 5 . . X X . . . . . .\n" +
			" 6 . . . . . . . . . .\n" +
			" 7 . . . . . . . . . .\n" +
			" 8 √ √ . . . . . . . .\n" +
			" 9 . . . . . . . . . .\n" +
			"10 . . . . . . . . . .\n");

		assertEquals("HIT", attack(gameId, 'E', 5));
		assertEquals("HIT", attack(gameId, 'F', 5));
		assertEquals("SUNK", attack(gameId, 'G', 5));
		assertEquals("HIT", attack(gameId, 'F', 9));
		assertEquals("HIT", attack(gameId, 'F', 8));
		assertEquals("SUNK", attack(gameId, 'F', 7));
		assertEquals("WATER", attack(gameId, 'H', 6));
		assertEquals("HIT", attack(gameId, 'H', 7));
		assertEquals("HIT", attack(gameId, 'H', 8));
		assertEquals("HIT", attack(gameId, 'H', 9));
		assertEquals("GAME_OVER", attack(gameId, 'H', 10));

		assertState(gameId, "" +
			"   A B C D E F G H I J\n" +
			" 1 ~ . . . . . . . . .\n" +
			" 2 . √ . . . . . . . .\n" +
			" 3 . √ . . . . . . . .\n" +
			" 4 . . . . . . . . . .\n" +
			" 5 . . √ √ √ √ √ . . .\n" +
			" 6 . . . . . . . ~ . .\n" +
			" 7 . . . . . √ . √ . .\n" +
			" 8 √ √ . . . √ . √ . .\n" +
			" 9 . . . . . √ . √ . .\n" +
			"10 . . . . . . . √ . .\n");
	}

	private String attack(UUID gameId, char x, int y) {
		var request = new GameController.AttackRequest();
		request.setGameId(gameId.toString());
		request.setX(x);
		request.setY(y);

		ResponseEntity<String> response = restTemplate.postForEntity("/attack", request, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());

		return response.getBody();
	}

	private void assertState(UUID gameId, String expected) {
		var request = new GameController.GetStateRequest();
		request.setGameId(gameId.toString());

		ResponseEntity<String> response = restTemplate.postForEntity("/state", request, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expected, response.getBody());
	}

}
