package com.game.server;

import java.io.File;
import java.util.Random;

import com.game.file.Tag;
import com.game.file.TagInt;
import com.game.file.TagSubtag;
import com.game.noise.Noise;

public class MapGen {
	// Base Variables
	private int seed;
	private TagSubtag tag;

	// Load and Save stuff
	public MapGen(int seed) {
		File file = new File("./server/world/data.cgx");
		if (file.exists()) {
			tag = (TagSubtag) Tag.load(file);
			TagInt sTag = (TagInt) tag.getTag("seed");
			seed = sTag.getValue();
		} else {
			this.seed = seed;
			tag = new TagSubtag("Data");
			TagInt sTag = new TagInt("seed");
			sTag.setValue(seed);
			tag.addTag(sTag);
		}
		Random ran = new Random(seed);
		gras = new Noise(ran.nextLong(), 4, 2);
		dirt = new Noise(ran.nextLong(), 3, 2);
	}

	public void save() {
		File file = new File("./server/world/data.cgx");
		Tag.save(tag, file);
	}

	public MapGen() {
		this((int) System.currentTimeMillis());
	}

	/*
	 * Chunk generation Code
	 */
	public void generate(Chunk chunk) {
		for (int x = 0; x < 16; x++) {
			int ax = x + chunk.x * 16;
			int gHeight = (int) (gras.get(ax, 0) * 5);
			int dHeight = gHeight - (int) (dirt.get(ax, 0) * 2) - 3;
			for (int y = 0; y < 16; y++) {
				int ay = y + chunk.y * 16;
				if (ay > gHeight) {
					chunk.setBlock(x, y, 0);
				} else if (ay == gHeight) {
					chunk.setBlock(x, y, 1);
				} else if (ay >= dHeight) {
					chunk.setBlock(x, y, 2);
				} else {
					chunk.setBlock(x, y, 3);
				}

			}
		}
	}

	/*
	 * Noise
	 */
	private Noise gras;
	private Noise dirt;

}
