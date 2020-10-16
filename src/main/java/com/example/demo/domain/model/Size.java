package com.example.demo.domain.model;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Represents a two dimensional size.
 */
@EqualsAndHashCode
@RequiredArgsConstructor
public class Size {

	private final int width;
	private final int height;

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

}
