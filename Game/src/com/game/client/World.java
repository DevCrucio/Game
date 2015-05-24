package com.game.client;

import java.util.concurrent.ConcurrentHashMap;

public class World {
	public GuiGame gg;

	public World(GuiGame gg) {
		this.gg = gg;
	}

	/*
	 * Entities [ID, Entity]
	 */
	public ConcurrentHashMap<Integer, Entity> entities = new ConcurrentHashMap<Integer, Entity>();

	/*
	 * Render
	 */
	public void update(float delta) {
		for (Entity entity : entities.values()) {
			entity.update(delta);
		}
	}

	/*
	 * Update
	 */
	public void render() {
		for (Entity entity : entities.values()) {
			entity.render();
		}
	}
}
