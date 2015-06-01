package com.game.noise;

public class Noise {
	private Simplex noise;

	public Noise(long seed, int octave, int smooth) {
		noise = new Simplex(seed, octave, smooth, new Interpolation());
	}

	public float get(float x, float y) {
		return noise.get(x, y);
	}
}
