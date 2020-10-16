package com.example.demo.domain.model;

import lombok.EqualsAndHashCode;

/**
 * A rectangle area with map positions.
 */
@EqualsAndHashCode
public class Area {

	private final MapPosition topLeft;
	private final MapPosition bottomRight;

	public Area(Size size) {
		topLeft = new MapPosition('A', 1);
		bottomRight = topLeft.plusX(size.width() - 1).plusY(size.height() - 1);
	}

	public Area(MapPosition topLeft, MapPosition bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	public boolean isInArea(MapPosition position) {
		return topLeft.compareX(position) >= 0
			&& topLeft.compareY(position) >= 0
			&& bottomRight.compareX(position) <= 0
			&& bottomRight.compareY(position) <= 0;
	}

}
