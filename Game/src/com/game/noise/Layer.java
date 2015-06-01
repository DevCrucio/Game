package com.game.noise;

public class Layer {

	protected final long seed;
	protected final int density;
	protected final FastRandom rand;
	protected final Interpolation interpolator;

	public Layer(final int density, final Interpolation interpolator) {
		this(new FastRandom().randLong(), density, interpolator);
	}

	public Layer(final long seed, final int density,
			final Interpolation interpolator) {
		this.density = density;
		this.seed = seed;
		this.rand = new FastRandom();
		this.interpolator = interpolator;
	}

	public float get(float x, float y) {
		final int cellX0 = (int) Math.floor(x / density);
		final int cellY0 = (int) Math.floor(y / density);
		final int cellX1 = (int) Math.floor(x / density) + 1;
		final int cellY1 = (int) Math.floor(y / density) + 1;

		final float value00 = getRand(cellX0, cellY0).randBool() ? -1f : 1f;
		final float value10 = getRand(cellX1, cellY0).randBool() ? -1f : 1f;
		final float value11 = getRand(cellX1, cellY1).randBool() ? -1f : 1f;
		final float value01 = getRand(cellX0, cellY1).randBool() ? -1f : 1f;

		final float relativeX = x - (cellX0 * density);
		final float relativeY = y - (cellY0 * density);
		final float timeX = relativeX / density;
		final float timeY = relativeY / density;

		final float interpolationHoriz0 = interpolator.interpolate(timeX,
				value00, 0, value10, 1);
		final float interpolationHoriz1 = interpolator.interpolate(timeX,
				value01, 0, value11, 1);
		final float interpolationVert = interpolator.interpolate(timeY,
				interpolationHoriz0, 0f, interpolationHoriz1, 1f);

		return interpolationVert;
	}

	private static long hashWang(long key) {
		key = (~key) + (key << 21);
		key = key ^ (key >>> 24);
		key = (key + (key << 3)) + (key << 8);
		key = key ^ (key >>> 14);
		key = (key + (key << 2)) + (key << 4);
		key = key ^ (key >>> 28);
		key = key + (key << 31);

		return key;
	}

	private FastRandom getRand(int x, int y) {
		rand.setSeed(hashWang((x << 17) ^ y) + seed);
		return rand;
	}

}
