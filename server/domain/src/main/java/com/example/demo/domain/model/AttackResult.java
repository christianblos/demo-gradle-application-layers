package com.example.demo.domain.model;

/**
 * The result of an attack on the map.
 */
public enum AttackResult {

	/**
	 * No ship was hit
	 */
	WATER,

	/**
	 * A part of a ship was hit, but the ship is still alive
	 */
	HIT,

	/**
	 * All parts of the ship were hit and the ship sank
	 */
	SUNK,

	/**
	 * All ships sank. Game is over.
	 */
	GAME_OVER,

}
