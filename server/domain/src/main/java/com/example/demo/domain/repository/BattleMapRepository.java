package com.example.demo.domain.repository;

import com.example.demo.domain.model.BattleMap;

import java.util.Optional;
import java.util.UUID;

public interface BattleMapRepository {

	void create(UUID gameId, BattleMap map);

	Optional<BattleMap> find(UUID gameId);
}
