package com.game.client;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.game.util.Box;

public class Chunk {
	public int x, y;
	public int[] block; // 16x16
	public boolean gen;
	public Box col;
	public List<Box> blocks = new ArrayList<Box>();

	public Chunk(int x, int y, int[] block) {
		this.x = x;
		this.y = y;
		this.block = block;
		gen = true;
		col = new Box(x * 256 + 128, y * 256 + 128, 128, 128);
	}

	public void setBlock(int x, int y, int id) {
		block[x + 16 * y] = id;
		gen = true;
	}

	public int getBlock(int x, int y) {
		return block[x + 16 * y];
	}

	// update
	public void update(float delta) {

	}

	// render
	public void render() {
		if (gen) {
			gen();
		}
		GL11.glTranslatef(x * 256, y * 256, 0);
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				GL11.glPushMatrix();
				GL11.glTranslatef(x * 16, y * 16, 0);
				int value = getBlock(x, y);
				if (value == 1) {
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(0f / 32f, 1f / 32f);
					GL11.glVertex2f(-0.005f, -0.005f);
					GL11.glTexCoord2f(1f / 32f, 1f / 32f);
					GL11.glVertex2f(16.005f, -0.005f);
					GL11.glTexCoord2f(1f / 32f, 0f / 32f);
					GL11.glVertex2f(16.005f, 16.005f);
					GL11.glTexCoord2f(0f / 32f, 0f / 32f);
					GL11.glVertex2f(-0.005f, 16.005f);
					GL11.glEnd();
				}
				if (value == 2) {
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(1f / 32f, 1f / 32f);
					GL11.glVertex2f(-0.005f, -0.005f);
					GL11.glTexCoord2f(2f / 32f, 1f / 32f);
					GL11.glVertex2f(16.005f, -0.005f);
					GL11.glTexCoord2f(2f / 32f, 0f / 32f);
					GL11.glVertex2f(16.005f, 16.005f);
					GL11.glTexCoord2f(1f / 32f, 0f / 32f);
					GL11.glVertex2f(-0.005f, 16.005f);
					GL11.glEnd();
				}
				if (value == 3) {
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glTexCoord2f(2f / 32f, 1f / 32f);
					GL11.glVertex2f(-0.005f, -0.005f);
					GL11.glTexCoord2f(3f / 32f, 1f / 32f);
					GL11.glVertex2f(16.005f, -0.005f);
					GL11.glTexCoord2f(3f / 32f, 0f / 32f);
					GL11.glVertex2f(16.005f, 16.005f);
					GL11.glTexCoord2f(2f / 32f, 0f / 32f);
					GL11.glVertex2f(-0.005f, 16.005f);
					GL11.glEnd();
				}
				GL11.glPopMatrix();
			}
		}
	}

	// generate VBO and Collision Box
	public void gen() {
		gen = false;
		// TODO generate mesh
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

	// Destroy model
	public void destroy() {

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

	// Box Render
	public void hitBox() {
		col.renderHitBox();
		for (Box box : blocks) {
			box.renderHitBox();
		}
	}
}
