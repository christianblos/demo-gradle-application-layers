package com.example.demo.domain.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * A ship that can be placed on the map and attacked.
 */
public class Ship {

	private final Map<Integer, Boolean> partsHit = new TreeMap<>();

	public Ship(int length) {
		for (int part = 1; part <= length; part++) {
			partsHit.put(part, false);
		}
	}

	public int length() {
		return partsHit.size();
	}

	public void hit(int part) {
		if (!partsHit.containsKey(part)) {
			throw new IllegalArgumentException(String.format("Part %s doesn't exist", part));
		}
		partsHit.put(part, true);
	}

	public boolean isSunk() {
		return partsHit.values().stream().allMatch(hit -> hit);
	}

	public Set<Integer> getHitParts() {
		return partsHit.entrySet().stream()
			.filter(Map.Entry::getValue)
			.map(Map.Entry::getKey)
			.collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		return "Ship " + partsHit.values().stream()
			.map(hit -> hit ? "[x]" : "[ ]")
			.collect(Collectors.joining());
	}
}
