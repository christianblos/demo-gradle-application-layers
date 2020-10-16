package com.example.demo.domain.model;

import lombok.EqualsAndHashCode;

/**
 * A position on the map (like "A1").
 */
@EqualsAndHashCode
public class MapPosition {

	private final char x;
	private final int y;

	public MapPosition(char x, int y) {
		this.x = Character.toUpperCase(x);
		this.y = y;
	}

	public MapPosition(int x, int y) {
		this.x = (char) ('A' + x - 1);
		this.y = y;
	}

	public char x() {
		return x;
	}

	public int y() {
		return y;
	}

	public MapPosition plusX(int value) {
		if (value == 0) {
			return this;
		}
		return new MapPosition((char) (x + value), y);
	}

	public MapPosition plusY(int value) {
		if (value == 0) {
			return this;
		}
		return new MapPosition(x, y + value);
	}

	public MapPosition neighbour(ShipDirection direction) {
		char newX = (char) (x + direction.xModifier());
		int newY = y + direction.yModifier();
		return new MapPosition(newX, newY);
	}

	public int compareX(MapPosition position) {
		return (int) position.x - (int) x;
	}

	public int compareY(MapPosition position) {
		return position.y - y;
	}

	@Override
	public String toString() {
		return x + String.valueOf(y);
	}

}
