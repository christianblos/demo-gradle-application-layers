package com.example.demo.domain.model;

/**
 * The direction of a ship on the map relative to the placed position.
 */
public enum ShipDirection {

	UP(0, -1),
	DOWN(0, 1),
	LEFT(-1, 0),
	RIGHT(1, 0);

	private final int xModifier;
	private final int yModifier;

	ShipDirection(int xModifier, int yModifier) {
		this.xModifier = xModifier;
		this.yModifier = yModifier;
	}

	public int xModifier() {
		return xModifier;
	}

	public int yModifier() {
		return yModifier;
	}

}
