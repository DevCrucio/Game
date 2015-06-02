package com.game.item;

import org.lwjgl.opengl.GL11;

public class ItemStoneSword extends Item {

	public ItemStoneSword(int ID) {
		super(ID);

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
