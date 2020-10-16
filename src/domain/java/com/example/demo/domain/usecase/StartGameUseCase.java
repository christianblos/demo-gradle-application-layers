package com.example.demo.domain.usecase;

import com.example.demo.domain.model.BattleMap;
import com.example.demo.domain.model.MapPosition;
import com.example.demo.domain.model.Ship;
import com.example.demo.domain.model.ShipDirection;
import com.example.demo.domain.model.Size;
import com.example.demo.domain.repository.BattleMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StartGameUseCase {

	private final BattleMapRepository battleMapRepository;

	public UUID execute() {
		UUID gameId = UUID.randomUUID();

		/* Hard coded positions:

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

		BattleMap map = new BattleMap(new Size(10, 10));
		map.placeShip(new Ship(2), new MapPosition('B', 2), ShipDirection.DOWN);
		map.placeShip(new Ship(2), new MapPosition('A', 8), ShipDirection.RIGHT);
		map.placeShip(new Ship(3), new MapPosition('F', 9), ShipDirection.UP);
		map.placeShip(new Ship(4), new MapPosition('H', 7), ShipDirection.DOWN);
		map.placeShip(new Ship(5), new MapPosition('G', 5), ShipDirection.LEFT);

		battleMapRepository.create(gameId, map);

		return gameId;
	}
}
