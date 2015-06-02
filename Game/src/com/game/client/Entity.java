package com.game.client;

public abstract class Entity {
	public final int ID;
	public final World world;
	public float x, y, dx, dy;
	public float lerpX, lerpY;
	public boolean lookLeft;

	public Entity(World world, int ID) {
		this.ID = ID;
		this.world = world;
	}

	public abstract void update(float delta);

	public abstract void render();

	public void renderName() {

	}
}
