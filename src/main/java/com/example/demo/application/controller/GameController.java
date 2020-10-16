package com.example.demo.application.controller;

import com.example.demo.domain.model.MapPosition;
import com.example.demo.domain.usecase.AttackUseCase;
import com.example.demo.domain.usecase.GetGameStateUseCase;
import com.example.demo.domain.usecase.GetGameStateUseCase.MapPositionState;
import com.example.demo.domain.usecase.StartGameUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class GameController {

	private final StartGameUseCase startGame;
	private final GetGameStateUseCase getGameState;
	private final AttackUseCase attack;

	/**
	 * Start a new game and return the gameId
	 */
	@PostMapping("/start")
	public String start() {
		return startGame.execute().toString();
	}

	/**
	 * Print the current state of a game
	 */
	@PostMapping("/state")
	public String state(@RequestBody GetStateRequest request) {
		var gameId = UUID.fromString(request.getGameId());

		var response = getGameState.execute(gameId);
		Map<MapPosition, MapPositionState> states = response.getStates();

		StringBuilder result = new StringBuilder();
		result.append("   A B C D E F G H I J\n");

		for (int y = 1; y <= response.getMapSize().height(); y++) {
			result.append(String.format("%1$2s", y));

			for (int x = 1; x <= response.getMapSize().width(); x++) {
				var position = new MapPosition(x, y);
				if (states.containsKey(position)) {
					switch (states.get(position)) {
						case WATER:
							result.append(" ~");
							break;
						case HIT:
							result.append(" X");
							break;
						case HIT_SUNK:
							result.append(" √");
							break;
					}
				} else {
					result.append(" .");
				}
			}

			result.append("\n");
		}

		return result.toString();
	}

	/**
	 * Attack a position on the map
	 */
	@PostMapping("/attack")
	public String attack(@RequestBody AttackRequest request) {
		var gameId = UUID.fromString(request.getGameId());
		var position = new MapPosition(request.getX(), request.getY());
		return attack.execute(gameId, position).name();
	}

	@Data
	public static class GetStateRequest {
		String gameId;
	}

	@Data
	public static class AttackRequest {
		String gameId;
		char x;
		int y;
	}


}
