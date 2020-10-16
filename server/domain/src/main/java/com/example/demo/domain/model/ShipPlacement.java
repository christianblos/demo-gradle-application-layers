package com.example.demo.domain.model;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Represents a ship and its position on the map.
 */
public class ShipPlacement {

	private final Ship ship;
	private final Map<Integer, MapPosition> partPositions;

	public ShipPlacement(Ship ship, MapPosition position, ShipDirection direction) {
		this.ship = ship;
		this.partPositions = buildPartPositions(position, direction, ship.length());
	}

	public Ship ship() {
		return ship;
	}

	public MapPosition startPosition() {
		return partPositions.get(1);
	}

	public MapPosition endPosition() {
		return partPositions.get(ship.length());
	}

	public Set<MapPosition> getHitPositions() {
		return ship.getHitParts().stream()
			.map(partPositions::get)
			.collect(Collectors.toSet());
	}

	public boolean touches(ShipPlacement placement) {
		var minX = Math.min(placement.startPosition().x(), placement.endPosition().x()) - 1;
		var maxX = Math.max(placement.startPosition().x(), placement.endPosition().x()) + 1;
		var minY = Math.min(placement.startPosition().y(), placement.endPosition().y()) - 1;
		var maxY = Math.max(placement.startPosition().y(), placement.endPosition().y()) + 1;
		var area = new Area(new MapPosition((char) minX, minY), new MapPosition((char) maxX, maxY));
		return partPositions.values().stream().anyMatch(area::isInArea);
	}

	public Optional<ShipPart> getShipPart(MapPosition targetPosition) {
		return partPositions.entrySet().stream()
			.filter(entry -> entry.getValue().equals(targetPosition))
			.findAny()
			.map(entry -> new ShipPart(ship, entry.getKey()));
	}

	private Map<Integer, MapPosition> buildPartPositions(MapPosition startPosition, ShipDirection direction, int amount) {
		Map<Integer, MapPosition> positions = new TreeMap<>();
		positions.put(1, startPosition);

		while (positions.size() < amount) {
			int lastPart = positions.size();
			int nexPart = lastPart + 1;
			positions.put(nexPart, positions.get(lastPart).neighbour(direction));
		}

		return positions;
	}

	@Override
	public String toString() {
		return "ShipPlacement " + partPositions.values().stream()
			.map(MapPosition::toString)
			.collect(Collectors.joining(","));
	}
}
