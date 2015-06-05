package com.game.item;

import org.lwjgl.opengl.GL11;

public class ItemStonePick extends Item {

	public ItemStonePick(int ID, int maxStack) {
		super(ID);
		this.maxStack = maxStack;
		this.name = "Stone Pick";
	}

	@Override
	public void render() {
		GL11.glColor3f(0, 1, 0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-8, -8);
		GL11.glVertex2f(8, -8);
		GL11.glVertex2f(8, 8);
		GL11.glVertex2f(-8, 8);
		GL11.glEnd();
	}

}
