package com.example.demo.domain.model;

import lombok.RequiredArgsConstructor;

/**
 * Represents a part of a ship that can be attacked.
 */
@RequiredArgsConstructor
public class ShipPart {

	private final Ship ship;
	private final int part;

	public Ship ship() {
		return ship;
	}

	public int part() {
		return part;
	}
}
