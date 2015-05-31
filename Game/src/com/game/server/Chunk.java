package com.game.server;

public class Chunk {
	public int x, y;
	public int[] block;

	public Chunk(int x, int y) {
		this.x = x;
		this.y = y;
		block = new int[256];
	}

	public void setBlock(int x, int y, int id) {
		block[x + 16 * y] = id;
	}

	public int getBlock(int x, int y) {
		return block[x + 16 * y];
	}
}
