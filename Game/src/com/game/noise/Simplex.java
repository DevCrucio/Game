package com.game.noise;

public class Simplex {

	private static Layer[] generateLayers(int smoothness, int octaves,
			long seed, Interpolation interp) {
		int potSmoothness = 1;
		while (potSmoothness < smoothness) {
			potSmoothness *= 2;
		}
		int density = potSmoothness;

		Layer[] layers = new Layer[octaves];
		for (int i = 0; i < layers.length; i++) {
			layers[i] = new Layer(seed, density, interp);
			density *= 2;
		}
		return layers;
	}

	private static int[] generateStrengths(int octaves) {
		int strength = 1;
		int[] strengths = new int[octaves];
		for (int i = 0; i < octaves; i++) {
			strengths[i] = strength;
			strength *= 2;
		}
		return strengths;
	}

	protected final Layer[] layers;
	protected final int[] strengths;
	protected final int strengthSum;
	protected final int[] offsets;

	public Simplex(long seed, int octaves, int smoothness, Interpolation interp) {
		this(seed, generateLayers(smoothness, octaves, seed, interp),
				generateStrengths(octaves));
	}

	public Simplex(long seed, Layer[] layers, int[] strengths) {
		if (layers.length != strengths.length)
			throw new IllegalArgumentException(
					"layers.length != strengths.length");
		this.layers = layers;
		this.strengths = strengths;

		int sum = 0;
		for (int i = 0; i < strengths.length; i++)
			sum += strengths[i];
		this.strengthSum = sum;

		FastRandom rand = new FastRandom(seed);

		offsets = new int[layers.length * 2];
		for (int i = 0; i < offsets.length; i++) {
			offsets[i] = rand.randInt(128);
		}
	}

	public float get(float x, float y) {
		float val = 0f;
		for (int i = 0; i < layers.length; i++) {
			val += layers[i].get(x + offsets[i * 2], y + offsets[i * 2 + 1])
					* strengths[i];
		}
		return val / strengthSum;
	}

}
