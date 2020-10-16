package com.example.demo.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapPositionTest {

	@Test
	public void test_two_positions_are_equal() {
		assertEquals(new MapPosition('A', 2), new MapPosition('A', 2));
		assertEquals(new MapPosition('c', 4), new MapPosition('C', 4));
		assertEquals(new MapPosition('A', 1), new MapPosition(1, 1));
		assertEquals(new MapPosition('B', 3), new MapPosition(2, 3));
	}

	@Test
	public void test_plusX() {
		var position = new MapPosition('D', 5);
		assertEquals(new MapPosition('G', 5), position.plusX(3));
		assertEquals(new MapPosition('B', 5), position.plusX(-2));
	}

	@Test
	public void test_plusY() {
		var position = new MapPosition('D', 5);
		assertEquals(new MapPosition('D', 8), position.plusY(3));
		assertEquals(new MapPosition('D', 3), position.plusY(-2));
	}

	@Test
	public void test_neighbour() {
		var position = new MapPosition('D', 5);
		assertEquals(new MapPosition('D', 4), position.neighbour(ShipDirection.UP));
		assertEquals(new MapPosition('D', 6), position.neighbour(ShipDirection.DOWN));
		assertEquals(new MapPosition('C', 5), position.neighbour(ShipDirection.LEFT));
		assertEquals(new MapPosition('E', 5), position.neighbour(ShipDirection.RIGHT));
	}

	@Test
	public void test_compareX() {
		var position = new MapPosition('D', 5);
		assertEquals(0, position.compareX(new MapPosition('D', 1)));
		assertEquals(1, position.compareX(new MapPosition('E', 1)));
		assertEquals(2, position.compareX(new MapPosition('F', 1)));
		assertEquals(-1, position.compareX(new MapPosition('C', 1)));
	}

	@Test
	public void test_compareY() {
		var position = new MapPosition('D', 5);
		assertEquals(0, position.compareY(new MapPosition('A', 5)));
		assertEquals(1, position.compareY(new MapPosition('A', 6)));
		assertEquals(2, position.compareY(new MapPosition('A', 7)));
		assertEquals(-1, position.compareY(new MapPosition('A', 4)));
	}

}