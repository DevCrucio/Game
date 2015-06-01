package com.game.noise;

public class Interpolation {

	public static final Interpolation inst = new Interpolation();

	public float interpolate(float t, float y0, float x0, float y1, float x1) {
		float t2 = t * t;
		return (3 * t2 - 2 * t2 * t) * (y1 - y0) + y0;
	}

}
