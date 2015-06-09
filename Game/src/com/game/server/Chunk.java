package com.game.server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.game.util.Box;

public class Chunk {
	public int x, y;
	public int[] block;
	public Box col;
	public List<Box> blocks = new CopyOnWriteArrayList<Box>();

	public Chunk(int x, int y) {
		this.x = x;
		this.y = y;
		block = new int[256];
		col = new Box(x * 256 + 128, y * 256 + 128, 128, 128);
		gen();
	}

	public void setBlock(int x, int y, int id) {
		block[x + 16 * y] = id;
		gen();
	}

	public int getBlock(int x, int y) {
		return block[x + 16 * y];
	}

	// Collision check
	public Box check(Box box) {
		if (box.check(col)) {
			for (Box hit : blocks) {
				if (box.check(hit)) {
					return hit;
				}
			}
		}
		return null;
	}

	public void gen() {
		// Generate Collision
		blocks.clear();
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				if (getBlock(x, y) != 0) {
					blocks.add(new Box(this.x * 256 + 8 + (16 * x), this.y
							* 256 + 8 + (16 * y), 8, 8));
				}
			}
		}
	}
}
