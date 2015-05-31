package com.game.server;

import java.io.File;

import com.game.file.Tag;
import com.game.file.TagInt;
import com.game.file.TagSubtag;

public class Region {
	public int x, y;
	public TagSubtag tag;

	public Region(int x, int y, TagSubtag tag) {
		this.x = x;
		this.y = y;
		this.tag = tag;
	}

	public Chunk getChunk(int x, int y) {
		Chunk chunk = new Chunk(x, y);
		if (tag.hasTag("c" + x + "x" + y)) {
			// Load
			TagSubtag ct = (TagSubtag) tag.getTag("c" + x + "x" + y);
			TagSubtag block = (TagSubtag) ct.getTag("block");
			for (int ix = 0; ix < 16; ix++) {
				for (int iy = 0; iy < 16; iy++) {
					TagInt ti = (TagInt) block.getTag("b" + ix + "x" + iy);
					chunk.setBlock(ix, iy, ti.getValue());
				}
			}
		} else {
			// Generate :O
			for (int ix = 0; ix < 16; ix++) {
				for (int iy = 0; iy < 16; iy++) {
					chunk.setBlock(ix, iy, (int) Math.floor(Math.random() * 4));
				}
			}
		}
		return chunk;
	}

	public void setChunk(Chunk chunk) {
		if (tag.hasTag("c" + chunk.x + "x" + chunk.y)) {
			tag.remTag("c" + chunk.x + "x" + chunk.y);
		}
		// ChunkTag
		TagSubtag ct = new TagSubtag("c" + chunk.x + "x" + chunk.y);
		TagSubtag block = new TagSubtag("block");
		// put blocks
		for (int ix = 0; ix < 16; ix++) {
			for (int iy = 0; iy < 16; iy++) {
				TagInt ti = new TagInt("b" + ix + "x" + iy);
				ti.setValue(chunk.getBlock(ix, iy));
				block.addTag(ti);
			}
		}
		// Add tags
		ct.addTag(block);
		tag.addTag(ct);

	}

	public void isActive(World world) {
		boolean active = false;
		for (Chunk chunk : world.chunks) {
			int rx = (int) Math.floor(chunk.x / 32f);
			int ry = (int) Math.floor(chunk.y / 32f);
			if (rx == x && ry == y) {
				active = true;
				break;
			}
		}
		if (!active) {
			// Remove
			world.regions.remove(this);
			save(this);
		}
	}

	// Region contains 32x32 Chunks
	// Static Save and Load
	public static void save(Region r) {
		File file = new File("./server/world/r" + r.x + "x" + r.y + ".cgx");
		Tag.save(r.tag, file);
	}

	public static Region load(int x, int y) {
		File file = new File("./server/world/r" + x + "x" + y + ".cgx");
		if (file.exists()) {
			Tag tag = Tag.load(file);
			Region region = new Region(x, y, (TagSubtag) tag);
			return region;
		} else {
			Region region = new Region(x, y, new TagSubtag("Region"));
			return region;
		}
	}
}
