package com.example.demo.domain.usecase;

import com.example.demo.domain.model.AttackResult;
import com.example.demo.domain.model.BattleMap;
import com.example.demo.domain.model.MapPosition;
import com.example.demo.domain.repository.BattleMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttackUseCase {

	private final BattleMapRepository battleMapRepository;

	public AttackResult execute(UUID gameId, MapPosition position) {
		BattleMap map = battleMapRepository.find(gameId)
			.orElseThrow(() -> new IllegalArgumentException("Game does not exist"));

		AttackResult result = map.attack(position);

		if (result == AttackResult.SUNK && allSunk(map)) {
			return AttackResult.GAME_OVER;
		}

		return result;
	}

	private boolean allSunk(BattleMap map) {
		return map.getAllShipPlacements().stream().allMatch(placement -> placement.ship().isSunk());
	}
}
