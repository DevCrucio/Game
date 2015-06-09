package com.game.server;

import com.game.util.Box;

public abstract class Entity {
	public final int ID;
	public final World world;
	public float x, y, dx, dy;
	public boolean lookLeft;
	public Box box;

	public Entity(World world, int ID) {
		this.ID = ID;
		this.world = world;
	}

	public abstract void update(float delta);

	public abstract void send();
}
