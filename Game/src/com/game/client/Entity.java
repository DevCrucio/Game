package com.game.client;

public abstract class Entity {
	public final int ID;
	public final World world;
	public float x, y;

	public Entity(World world, int ID) {
		this.ID = ID;
		this.world = world;
	}

	public abstract void update(float delta);

	public abstract void render();
}
