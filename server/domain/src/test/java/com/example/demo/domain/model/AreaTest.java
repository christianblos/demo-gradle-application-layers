package com.example.demo.domain.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AreaTest {

	@ParameterizedTest
	@MethodSource("isInAreaProvider")
	public void test_isInArea(MapPosition position, boolean expected) {
		var area = new Area(new Size(2, 3));
		assertEquals(expected, area.isInArea(position));
	}

	public static Stream<Arguments> isInAreaProvider() {
		return Stream.of(
			Arguments.of(new MapPosition('A', 1), true),
			Arguments.of(new MapPosition('A', 2), true),
			Arguments.of(new MapPosition('A', 3), true),
			Arguments.of(new MapPosition('A', 4), false),
			Arguments.of(new MapPosition('B', 1), true),
			Arguments.of(new MapPosition('C', 1), false),
			Arguments.of(new MapPosition('B', 2), true),
			Arguments.of(new MapPosition('A', -1), false),
			Arguments.of(new MapPosition((char) ((int) 'A' - 1), 1), false)
		);
	}

}