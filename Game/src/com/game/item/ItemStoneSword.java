package com.game.item;

import org.lwjgl.opengl.GL11;

public class ItemStoneSword extends Item {

	public ItemStoneSword(int ID, int maxStack) {
		super(ID);
		this.maxStack = maxStack;
		this.name = "Stone Sword";
	}

	@Override
	public void render() {
		GL11.glColor3f(1, 0, 0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-8, -8);
		GL11.glVertex2f(8, -8);
		GL11.glVertex2f(8, 8);
		GL11.glVertex2f(-8, 8);
		GL11.glEnd();
	}

}
