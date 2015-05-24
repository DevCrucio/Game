package com.game.client;

import org.lwjgl.opengl.GL11;

public class EntityPlayer extends Entity {

	public EntityPlayer(World world, int ID) {
		super(world, ID);
	}

	public void poll(float delta) {

	}

	public void cam() {

		GL11.glTranslatef(480 - x, 270 - y, 0);
	}

	public void gui() {

	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render() {
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
