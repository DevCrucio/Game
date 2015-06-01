package com.game.client;

import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.opengl.GL11;

import com.game.util.Box;

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
		gg.store.get("Terrain").bind();
		GL11.glColor4f(1, 1, 1, 1);
		for (Chunk chunk : chunks.values()) {
			GL11.glPushMatrix();
			chunk.render();
			GL11.glPopMatrix();
		}
		gg.store.get("Terrain").unbind();
		//		GL11.glColor4f(0.8f, 0.2f, 0.2f, 1.0f);
		//		for (Chunk chunk : chunks.values()) {
		//			chunk.hitBox();
		//		}
		for (Entity entity : entities.values()) {
			GL11.glPushMatrix();
			entity.render();
			GL11.glPopMatrix();
		}
		if (player != null)
			player.gui();
	}

	/*
	 * Collision Check
	 */
	public Box check(Box box) {
		for (Chunk chunk : chunks.values()) {
			Box hit = chunk.check(box);
			if (hit != null) {
				return hit;
			}
		}
		return null;
	}

}
