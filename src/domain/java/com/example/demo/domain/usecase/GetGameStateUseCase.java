package com.example.demo.domain.usecase;

import com.example.demo.domain.model.BattleMap;
import com.example.demo.domain.model.MapPosition;
import com.example.demo.domain.model.ShipPlacement;
import com.example.demo.domain.model.Size;
import com.example.demo.domain.repository.BattleMapRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetGameStateUseCase {

	private final BattleMapRepository battleMapRepository;

	public GetGameStateResponse execute(UUID gameId) {
		BattleMap map = battleMapRepository.find(gameId)
			.orElseThrow(() -> new IllegalArgumentException("Game does not exist"));

		Map<MapPosition, MapPositionState> states = new HashMap<>();

		for (ShipPlacement placement : map.getAllShipPlacements()) {
			for (MapPosition hitPosition : placement.getHitPositions()) {
				states.put(hitPosition, placement.ship().isSunk() ? MapPositionState.HIT_SUNK : MapPositionState.HIT);
			}
		}

		map.getWaterHits().forEach(position -> states.put(position, MapPositionState.WATER));

		return new GetGameStateResponse(map.size(), states);
	}

	public enum MapPositionState {
		WATER,
		HIT,
		HIT_SUNK,
	}

	@RequiredArgsConstructor
	@Getter
	public static class GetGameStateResponse {
		private final Size mapSize;
		private final Map<MapPosition, MapPositionState> states;
	}
}
