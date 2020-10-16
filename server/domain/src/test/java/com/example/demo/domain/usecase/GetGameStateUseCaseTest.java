package com.example.demo.domain.usecase;

import com.example.demo.domain.model.BattleMap;
import com.example.demo.domain.model.MapPosition;
import com.example.demo.domain.model.Ship;
import com.example.demo.domain.model.ShipDirection;
import com.example.demo.domain.model.Size;
import com.example.demo.domain.repository.BattleMapRepository;
import com.example.demo.domain.usecase.GetGameStateUseCase.MapPositionState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetGameStateUseCaseTest {

	private UUID gameId;
	private GetGameStateUseCase getGameState;
	private BattleMapRepository battleMapRepository;

	@BeforeEach
	void setUp() {
		gameId = UUID.randomUUID();
		battleMapRepository = mock(BattleMapRepository.class);
		getGameState = new GetGameStateUseCase(battleMapRepository);
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

		map.attack(new MapPosition('A', 2));
		map.attack(new MapPosition('B', 1));
		map.attack(new MapPosition('D', 3));
		map.attack(new MapPosition('D', 4));
		map.attack(new MapPosition('D', 5));

		var response = getGameState.execute(gameId);

		assertEquals(5, response.getStates().size());
		assertEquals(MapPositionState.WATER, response.getStates().get(new MapPosition('A', 2)));
		assertEquals(MapPositionState.HIT, response.getStates().get(new MapPosition('B', 1)));
		assertEquals(MapPositionState.HIT_SUNK, response.getStates().get(new MapPosition('D', 3)));
		assertEquals(MapPositionState.HIT_SUNK, response.getStates().get(new MapPosition('D', 4)));
		assertEquals(MapPositionState.WATER, response.getStates().get(new MapPosition('D', 5)));
	}

}