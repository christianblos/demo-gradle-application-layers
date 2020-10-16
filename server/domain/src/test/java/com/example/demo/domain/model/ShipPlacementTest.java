package com.example.demo.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShipPlacementTest {

	@Test
	public void testStartPosition() {
		var placement = new ShipPlacement(new Ship(3), new MapPosition('D', 5), ShipDirection.UP);
		assertEquals(new MapPosition('D', 5), placement.startPosition());
	}

	@ParameterizedTest
	@MethodSource("endPositionProvider")
	public void test_endPosition(ShipDirection direction, MapPosition expectedPosition) {
		var placement = new ShipPlacement(new Ship(3), new MapPosition('D', 5), direction);
		assertEquals(expectedPosition, placement.endPosition());
	}

	public static Stream<Arguments> endPositionProvider() {
		return Stream.of(
			Arguments.of(ShipDirection.UP, new MapPosition('D', 3)),
			Arguments.of(ShipDirection.DOWN, new MapPosition('D', 7)),
			Arguments.of(ShipDirection.LEFT, new MapPosition('B', 5)),
			Arguments.of(ShipDirection.RIGHT, new MapPosition('F', 5))
		);
	}

	@ParameterizedTest
	@MethodSource("touchesProvider")
	public void test_touches(ShipPlacement placement) {
		/*
		   A B C D E F G H I J
		 1 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 2 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~    OO = ship
		 3 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~    !  = position not allowed for other ships
		 4 ~ ~ ! ! ! ! ! ~ ~ ~
		 5 ~ ~ ! O O O ! ~ ~ ~
		 6 ~ ~ ! ! ! ! ! ~ ~ ~
		 7 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 8 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 9 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		10 ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
		 */
		var placed = new ShipPlacement(new Ship(3), new MapPosition('F', 5), ShipDirection.LEFT);
		assertTrue(placed.touches(placement));
	}

	public static Stream<Arguments> touchesProvider() {
		return Stream.of(
			Arguments.of(new ShipPlacement(new Ship(3), new MapPosition('A', 4), ShipDirection.RIGHT)),
			Arguments.of(new ShipPlacement(new Ship(2), new MapPosition('C', 3), ShipDirection.DOWN)),
			Arguments.of(new ShipPlacement(new Ship(1), new MapPosition('G', 4), ShipDirection.UP)),
			Arguments.of(new ShipPlacement(new Ship(2), new MapPosition('G', 7), ShipDirection.UP)),
			Arguments.of(new ShipPlacement(new Ship(4), new MapPosition('J', 6), ShipDirection.LEFT)),
			Arguments.of(new ShipPlacement(new Ship(5), new MapPosition('E', 3), ShipDirection.DOWN))
		);
	}

	@ParameterizedTest
	@MethodSource("getShipPartProvider")
	public void test_getShipPart(MapPosition targetPosition, int expected) {
		/*
		   A B C D E
		 1 ~ ~ ~ ~ ~
		 2 ~ ~ ~ X ~
		 3 ~ ~ ~ X ~
		 4 ~ ~ ~ ~ ~
		 */
		var placement = new ShipPlacement(new Ship(2), new MapPosition('D', 2), ShipDirection.DOWN);
		assertEquals(expected, placement.getShipPart(targetPosition).map(ShipPart::part).orElse(-1));
	}

	public static Stream<Arguments> getShipPartProvider() {
		return Stream.of(
			Arguments.of(new MapPosition('D', 1), -1),
			Arguments.of(new MapPosition('D', 2), 1),
			Arguments.of(new MapPosition('D', 3), 2),
			Arguments.of(new MapPosition('D', 4), -1),
			Arguments.of(new MapPosition('C', 2), -1),
			Arguments.of(new MapPosition('E', 3), -1)
		);
	}

}