package com.game.client;

import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.opengl.GL11;

public class World {
	public GuiGame gg;

	public World(GuiGame gg) {
		this.gg = gg;
	}

	/*
	 * Entities [ID, Entity]
	 */
	public ConcurrentHashMap<Integer, Entity> entities = new ConcurrentHashMap<Integer, Entity>();
	public EntityPlayer player;

	/*
	 * Update
	 */
	private float send;

	public void update(float delta) {
		player.poll(delta);
		for (Entity entity : entities.values()) {
			entity.update(delta);
		}
		send += delta;
		if (send >= 50) {
			send -= 50;
			player.send();
		}
	}

	/*
	 * Render
	 */
	public void render() {
		player.cam();
		GL11.glColor4f(0.2f, 0.8f, 0.4f, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-5, -5);
		GL11.glVertex2f(5, -5);
		GL11.glVertex2f(5, 5);
		GL11.glVertex2f(-5, 5);
		GL11.glEnd();
		for (Entity entity : entities.values()) {
			GL11.glPushMatrix();
			entity.render();
			GL11.glPopMatrix();
		}
		player.gui();
	}

}
