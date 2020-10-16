package com.example.demo.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BattleMapTest {

	@ParameterizedTest
	@MethodSource("ships_can_be_placed_inside_of_map_provider")
	public void ships_can_be_placed_inside_of_map(MapPosition position, ShipDirection direction) {
		/*
		   A B C D E
		 1 ~ ~ ~ ~ ~
		 2 ~ ~ ~ ~ ~
		 3 ~ ~ ~ ~ ~
		 4 ~ ~ ~ ~ ~
		 5 ~ ~ ~ ~ ~
		 */
		var map = new BattleMap(new Size(5, 5));
		var ship = new Ship(3);
		map.placeShip(ship, position, direction);
	}

	public static Stream<Arguments> ships_can_be_placed_inside_of_map_provider() {
		return Stream.of(
			Arguments.of(new MapPosition('A', 1), ShipDirection.RIGHT),
			Arguments.of(new MapPosition('A', 1), ShipDirection.DOWN),
			Arguments.of(new MapPosition('E', 1), ShipDirection.LEFT),
			Arguments.of(new MapPosition('E', 1), ShipDirection.DOWN),
			Arguments.of(new MapPosition('A', 5), ShipDirection.UP),
			Arguments.of(new MapPosition('A', 5), ShipDirection.RIGHT),
			Arguments.of(new MapPosition('E', 5), ShipDirection.UP),
			Arguments.of(new MapPosition('E', 5), ShipDirection.LEFT)
		);
	}

	@ParameterizedTest
	@MethodSource("ships_can_not_be_placed_outside_of_map_provider")
	public void ships_can_not_be_placed_outside_of_map(MapPosition position, ShipDirection direction) {
		/*
		   A B C D E
		 1 ~ ~ ~ ~ ~
		 2 ~ ~ ~ ~ ~
		 3 ~ ~ ~ ~ ~
		 4 ~ ~ ~ ~ ~
		 5 ~ ~ ~ ~ ~
		 */
		var map = new BattleMap(new Size(5, 5));
		var ship = new Ship(3);

		assertThrows(IllegalArgumentException.class, () ->
			map.placeShip(ship, position, direction));
	}

	public static Stream<Arguments> ships_can_not_be_placed_outside_of_map_provider() {
		return Stream.of(
			Arguments.of(new MapPosition('B', 2), ShipDirection.UP),
			Arguments.of(new MapPosition('B', 2), ShipDirection.LEFT),
			Arguments.of(new MapPosition('D', 1), ShipDirection.RIGHT),
			Arguments.of(new MapPosition('C', 4), ShipDirection.DOWN)
		);
	}

	@ParameterizedTest
	@MethodSource("ships_can_not_touch_each_other_provider")
	public void ships_can_not_touch_each_other(MapPosition position, ShipDirection direction) {
		/*
		   A B C D E F G H I J
		 1 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 2 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~    OO = ship
		 3 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~    !  = position not allowed for other ships
		 4 ~ ~ ! ! ! ! ~ ~ ~ ~
		 5 ~ ~ ! O O ! ~ ~ ~ ~
		 6 ~ ~ ! ! ! ! ~ ~ ~ ~
		 7 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 8 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 9 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		10 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 */

		BattleMap battleMap = new BattleMap(new Size(10, 10));
		battleMap.placeShip(new Ship(2), new MapPosition('D', 5), ShipDirection.RIGHT);

		assertThrows(IllegalArgumentException.class, () ->
			battleMap.placeShip(new Ship(2), position, direction));
	}

	public static Stream<Arguments> ships_can_not_touch_each_other_provider() {
		return Stream.of(
			Arguments.of(new MapPosition('B', 4), ShipDirection.RIGHT),
			Arguments.of(new MapPosition('D', 5), ShipDirection.RIGHT),
			Arguments.of(new MapPosition('E', 3), ShipDirection.DOWN),
			Arguments.of(new MapPosition('G', 6), ShipDirection.LEFT),
			Arguments.of(new MapPosition('C', 7), ShipDirection.UP)
		);
	}

	@Test
	public void test_getAllShips() {
		BattleMap battleMap = new BattleMap(new Size(10, 5));
		battleMap.placeShip(new Ship(3), new MapPosition('D', 2), ShipDirection.LEFT);
		battleMap.placeShip(new Ship(4), new MapPosition('H', 2), ShipDirection.DOWN);

		assertEquals(2, battleMap.getAllShipPlacements().size());
	}

	@Test
	public void test_getShipPartOnPosition() {
		/*
		   A B C D E F G H I J
		 1 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 2 ~ <---A ~ ~ ~ B ~ ~
		 3 ~ ~ ~ ~ ~ ~ ~ | ~ ~
		 4 ~ ~ ~ ~ ~ ~ ~ | ~ ~
		 5 ~ ~ ~ ~ ~ ~ ~ V ~ ~
		 */

		var shipA = new Ship(3);
		var shipB = new Ship(4);

		BattleMap battleMap = new BattleMap(new Size(10, 5));
		battleMap.placeShip(shipA, new MapPosition('D', 2), ShipDirection.LEFT);
		battleMap.placeShip(shipB, new MapPosition('H', 2), ShipDirection.DOWN);

		assertShipPartOnPosition(battleMap, 'D', 2, shipA, 1);
		assertShipPartOnPosition(battleMap, 'B', 2, shipA, 3);
		assertShipPartOnPosition(battleMap, 'H', 2, shipB, 1);
		assertShipPartOnPosition(battleMap, 'H', 3, shipB, 2);
		assertShipPartOnPosition(battleMap, 'H', 5, shipB, 4);
		assertNoShipPartOnPosition(battleMap, 'D', 1);
		assertNoShipPartOnPosition(battleMap, 'A', 2);
		assertNoShipPartOnPosition(battleMap, 'H', 1);
		assertNoShipPartOnPosition(battleMap, 'I', 4);
	}

	private void assertShipPartOnPosition(BattleMap battleMap, char x, int y, Ship ship, int part) {
		ShipPart shipPart = battleMap.getShipPartOnPosition(new MapPosition(x, y)).orElseThrow();
		assertEquals(ship, shipPart.ship());
		assertEquals(part, shipPart.part());
	}

	private void assertNoShipPartOnPosition(BattleMap battleMap, char x, int y) {
		assertTrue(battleMap.getShipPartOnPosition(new MapPosition(x, y)).isEmpty());
	}

}