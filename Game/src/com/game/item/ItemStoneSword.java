package com.game.item;

import org.lwjgl.opengl.GL11;

public class ItemStoneSword extends TypeWeapon {

	public ItemStoneSword(int ID, int maxStack) {
		super(ID);
		this.maxStack = maxStack;
		this.name = "Stone Sword";
	}

	@Override
	public void render() {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0 / 32f, 1 / 32f);
		GL11.glVertex2f(-8, -8);
		GL11.glTexCoord2f(1 / 32f, 1 / 32f);
		GL11.glVertex2f(8, -8);
		GL11.glTexCoord2f(1 / 32f, 0 / 32f);
		GL11.glVertex2f(8, 8);
		GL11.glTexCoord2f(0 / 32f, 0 / 32f);
		GL11.glVertex2f(-8, 8);
		GL11.glEnd();
	}

	@Override
	public void renderEquiped() {
		GL11.glPushMatrix();
		GL11.glTranslatef(-16, 6, 0);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0 / 32f, 1 / 32f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(1 / 32f, 1 / 32f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(1 / 32f, 0 / 32f);
		GL11.glVertex2f(-16, 16);
		GL11.glTexCoord2f(0 / 32f, 0 / 32f);
		GL11.glVertex2f(16, 16);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
}
