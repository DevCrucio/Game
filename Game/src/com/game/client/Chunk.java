package com.game.client;

import org.lwjgl.opengl.GL11;

public class Chunk {
	public int x, y;
	public int[] block; // 16x16
	public boolean gen;

	public Chunk(int x, int y, int[] block) {
		this.x = x;
		this.y = y;
		this.block = block;
		gen = true;
	}

	public void setBlock(int x, int y, int id) {
		block[x + 16 * y] = id;
		gen = true;
	}

	// update
	public void update(float delta) {

	}

	// render
	public void render() {
		if (gen) {
			gen();
		}
		GL11.glColor3f(1, 0, 0);
		GL11.glTranslatef(x * 256, y * 256, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(0, 0);
		GL11.glVertex2f(256, 0);
		GL11.glVertex2f(256, 256);
		GL11.glVertex2f(0, 256);
		GL11.glEnd();
	}

	// generate VBO and Collision Box
	public void gen() {
		gen = false;
		// TODO generate mesh
	}
}
