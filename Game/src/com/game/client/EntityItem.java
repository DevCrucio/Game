package com.game.client;

import org.lwjgl.opengl.GL11;

import com.game.item.Item;

public class EntityItem extends Entity {
	public int ItemID;

	public EntityItem(World world, int ID) {
		super(world, ID);
	}

	@Override
	public void update(float delta) {
		// Collision Detection
		float lerp = 0.2f;
		// Lerp X
		x = x + ((lerpX - x) * lerp);
		y = y + ((lerpY - y) * lerp);
	}

	@Override
	public void render() {
		this.world.gg.store.get("Item").bind();
		GL11.glTranslatef(x, y, 0);
		GL11.glColor4f(1, 1, 1, 1);
		Item.items[ItemID].render();
	}
}
