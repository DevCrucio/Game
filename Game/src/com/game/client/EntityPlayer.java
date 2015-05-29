package com.game.client;

import org.lwjgl.opengl.GL11;

public class EntityPlayer extends Entity {
	public String name;

	public EntityPlayer(World world, int ID) {
		super(world, ID);
	}

	@Override
	public void update(float delta) {
		// TODO: Collision Detection
		float lerp = 0.2f;
		// Lerp X
		x = x + ((lerpX - x) * lerp);
		y = y + ((lerpY - y) * lerp);

	}

	@Override
	public void render() {
		// Base Quad Rendering
		GL11.glTranslatef(x, y, 0);
		GL11.glColor4f(0.8f, 0.4f, 0.2f, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-5, -5);
		GL11.glVertex2f(5, -5);
		GL11.glVertex2f(5, 5);
		GL11.glVertex2f(-5, 5);
		GL11.glEnd();
	}

}
