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
	public ConcurrentHashMap<String, Chunk> chunks = new ConcurrentHashMap<String, Chunk>();
	public EntityOwn player;

	/*
	 * Update
	 */
	private float send;

	public void update(float delta) {
		if (player != null)
			player.poll(delta);
		for (Chunk chunk : chunks.values()) {
			chunk.update(delta);
		}
		for (Entity entity : entities.values()) {
			entity.update(delta);
		}
		send += delta;
		if (send >= 50) {
			send -= 50;
			if (player != null)
				player.send();
		}
	}

	/*
	 * Render
	 */
	public void render() {
		if (player != null)
			player.cam();
		for (Chunk chunk : chunks.values()) {
			GL11.glPushMatrix();
			chunk.render();
			GL11.glPopMatrix();
		}
		for (Entity entity : entities.values()) {
			GL11.glPushMatrix();
			entity.render();
			GL11.glPopMatrix();
		}
		if (player != null)
			player.gui();
	}

}
