package com.game.client;

public abstract class EntityPlayer extends Entity {

	public EntityPlayer(World world, int ID) {
		super(world, ID);
	}

	public abstract void poll(float delta);

	public abstract void cam();

	public abstract void gui();

}
