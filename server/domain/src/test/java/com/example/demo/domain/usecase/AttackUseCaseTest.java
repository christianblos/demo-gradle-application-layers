package com.example.demo.domain.usecase;

import com.example.demo.domain.model.AttackResult;
import com.example.demo.domain.model.BattleMap;
import com.example.demo.domain.model.MapPosition;
import com.example.demo.domain.model.Ship;
import com.example.demo.domain.model.ShipDirection;
import com.example.demo.domain.model.Size;
import com.example.demo.domain.repository.BattleMapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AttackUseCaseTest {

	private UUID gameId;
	private AttackUseCase attack;
	private BattleMapRepository battleMapRepository;

	@BeforeEach
	void setUp() {
		gameId = UUID.randomUUID();
		battleMapRepository = mock(BattleMapRepository.class);
		attack = new AttackUseCase(battleMapRepository);
	}

	@Test
	void execute() {
		/*
		   A B C D E
		 1 X X X ~ ~
		 2 ~ ~ ~ ~ ~
		 3 ~ ~ ~ X ~
		 4 ~ ~ ~ X ~
		 5 ~ ~ ~ ~ ~
		 */
		BattleMap map = new BattleMap(new Size(5, 5));
		map.placeShip(new Ship(3), new MapPosition('A', 1), ShipDirection.RIGHT);
		map.placeShip(new Ship(2), new MapPosition('D', 4), ShipDirection.UP);
		when(battleMapRepository.find(gameId)).thenReturn(Optional.of(map));

		assertEquals(AttackResult.WATER, attack.execute(gameId, new MapPosition('A', 2)));
		assertEquals(AttackResult.HIT, attack.execute(gameId, new MapPosition('A', 1)));
		assertEquals(AttackResult.HIT, attack.execute(gameId, new MapPosition('B', 1)));
		assertEquals(AttackResult.HIT, attack.execute(gameId, new MapPosition('B', 1)));
		assertEquals(AttackResult.SUNK, attack.execute(gameId, new MapPosition('C', 1)));
		assertEquals(AttackResult.SUNK, attack.execute(gameId, new MapPosition('B', 1)));
		assertEquals(AttackResult.WATER, attack.execute(gameId, new MapPosition('D', 2)));
		assertEquals(AttackResult.HIT, attack.execute(gameId, new MapPosition('D', 3)));
		assertEquals(AttackResult.GAME_OVER, attack.execute(gameId, new MapPosition('D', 4)));
	}
}