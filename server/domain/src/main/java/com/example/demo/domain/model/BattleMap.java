package com.example.demo.domain.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The map on which ships can be placed and attacked.
 */
public class BattleMap {

	private final Size size;
	private final Area area;
	private final List<ShipPlacement> placedShips = new ArrayList<>();
	private final Set<MapPosition> waterHits = new HashSet<>();

	public BattleMap(Size size) {
		this.area = new Area(size);
		this.size = size;
	}

	public Size size() {
		return size;
	}

	public void placeShip(Ship ship, MapPosition position, ShipDirection direction) {
		var placement = new ShipPlacement(ship, position, direction);

		if (!area.isInArea(position) || !area.isInArea(placement.endPosition())) {
			throw new IllegalArgumentException("Ship is outside of map");
		}

		if (placedShips.stream().anyMatch(p -> p.touches(placement))) {
			throw new IllegalArgumentException("Ship should not touch other ship");
		}

		placedShips.add(placement);
	}

	public List<ShipPlacement> getAllShipPlacements() {
		return placedShips;
	}

	public Set<MapPosition> getWaterHits() {
		return waterHits;
	}

	public AttackResult attack(MapPosition position) {
		return getShipPartOnPosition(position)
			.map(shipPart -> {
				Ship ship = shipPart.ship();
				ship.hit(shipPart.part());
				return ship.isSunk() ? AttackResult.SUNK : AttackResult.HIT;
			})
			.orElseGet(() -> {
				waterHits.add(position);
				return AttackResult.WATER;
			});
	}

	public Optional<ShipPart> getShipPartOnPosition(MapPosition position) {
		return placedShips.stream()
			.map(placement -> placement.getShipPart(position))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.findAny();
	}

}
