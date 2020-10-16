package com.example.demo.adapter.repository;

import com.example.demo.domain.model.BattleMap;
import com.example.demo.domain.repository.BattleMapRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryBattleMapRepository implements BattleMapRepository {

	private final Map<UUID, BattleMap> games = new HashMap<>();

	@Override
	public void create(UUID gameId, BattleMap map) {
		games.put(gameId, map);
	}

	@Override
	public Optional<BattleMap> find(UUID gameId) {
		if (games.containsKey(gameId)) {
			return Optional.of(games.get(gameId));
		}
		return Optional.empty();
	}

}
