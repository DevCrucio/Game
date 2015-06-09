package com.game.client;

import org.lwjgl.opengl.GL11;

import com.game.item.Inventory;
import com.game.packet.Color;
import com.game.util.Text;

public class EntityPlayer extends Entity {
	public String name;
	public Color hair, body, arm, shoe;
	public Inventory inv;

	public EntityPlayer(World world, int ID) {
		super(world, ID);
		hair = new Color();
		body = new Color();
		arm = new Color();
		shoe = new Color();
		inv = new Inventory();
	}

	@Override
	public void update(float delta) {
		// Collision Detection
		float lerp = 0.2f;
		// Lerp X
		x = x + ((lerpX - x) * lerp);
		y = y + ((lerpY - y) * lerp * 2.5f);

	}

	@Override
	public void render() {
		// Base Quad Rendering
		this.world.gg.store.get("Char").bind();
		GL11.glTranslatef(x, y - 2, 0);
		if (!lookLeft)
			GL11.glScalef(-1, 1, 1);
		// Arm Back
		GL11.glPushMatrix();
		GL11.glTranslatef(2, -2, 0);
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glTexCoord2f(3f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(4f / 4f, 1f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(4f / 4f, 0f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(3f / 4f, 0f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glColor4f(arm.getR(), arm.getG(), arm.getB(), 1);
		GL11.glTexCoord2f(3f / 4f, 2f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(4f / 4f, 2f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(4f / 4f, 1f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(3f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glEnd();
		GL11.glPopMatrix();
		// Foot Back
		GL11.glPushMatrix();
		GL11.glTranslatef(-6, -12, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glTexCoord2f(2f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(3f / 4f, 1f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(3f / 4f, 0f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(2f / 4f, 0f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glColor4f(shoe.getR(), shoe.getG(), shoe.getB(), 1);
		GL11.glTexCoord2f(2f / 4f, 2f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(3f / 4f, 2f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(3f / 4f, 1f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(2f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glEnd();
		GL11.glPopMatrix();
		// Foot Front
		GL11.glPushMatrix();
		GL11.glTranslatef(4, -12, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glTexCoord2f(2f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(3f / 4f, 1f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(3f / 4f, 0f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(2f / 4f, 0f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glColor4f(shoe.getR(), shoe.getG(), shoe.getB(), 1);
		GL11.glTexCoord2f(2f / 4f, 2f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(3f / 4f, 2f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(3f / 4f, 1f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(2f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glEnd();
		GL11.glPopMatrix();
		// Body
		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glTexCoord2f(1f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(2f / 4f, 1f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(2f / 4f, 0f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(1f / 4f, 0f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glColor4f(body.getR(), body.getG(), body.getB(), 1);
		GL11.glTexCoord2f(1f / 4f, 2f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(2f / 4f, 2f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(2f / 4f, 1f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(1f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glEnd();
		GL11.glPopMatrix();
		// Head
		GL11.glPushMatrix();
		GL11.glTranslatef(-2, 8, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glTexCoord2f(0f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(1f / 4f, 1f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(1f / 4f, 0f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(0f / 4f, 0f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glColor4f(1, 0, 0, 1);
		GL11.glTexCoord2f(0f / 4f, 2f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(1f / 4f, 2f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(1f / 4f, 1f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(0f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glEnd();
		GL11.glPopMatrix();
		// Hair
		GL11.glPushMatrix();
		GL11.glTranslatef(-2, 8, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glTexCoord2f(0f / 4f, 3f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(1f / 4f, 3f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(1f / 4f, 2f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(0f / 4f, 2f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glColor4f(hair.getR(), hair.getG(), hair.getB(), 1);
		GL11.glTexCoord2f(0f / 4f, 4f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(1f / 4f, 4f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(1f / 4f, 3f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(0f / 4f, 3f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glEnd();
		GL11.glPopMatrix();
		// Arm Front
		GL11.glPushMatrix();
		GL11.glTranslatef(2, -2, 0);
		if (inv.items[9] != null) {
			world.gg.store.get("Item").bind();
			inv.items[9].renderEquiped();
			world.gg.store.get("Char").bind();
		}
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glTexCoord2f(3f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(4f / 4f, 1f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(4f / 4f, 0f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(3f / 4f, 0f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glColor4f(arm.getR(), arm.getG(), arm.getB(), 1);
		GL11.glTexCoord2f(3f / 4f, 2f / 4f);
		GL11.glVertex2f(-16, -16);
		GL11.glTexCoord2f(4f / 4f, 2f / 4f);
		GL11.glVertex2f(16, -16);
		GL11.glTexCoord2f(4f / 4f, 1f / 4f);
		GL11.glVertex2f(16, 16);
		GL11.glTexCoord2f(3f / 4f, 1f / 4f);
		GL11.glVertex2f(-16, 16);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	@Override
	public void renderName() {
		// Text Rendering
		GL11.glColor3f(0.2f, 1.0f, 1.0f);
		GL11.glTranslatef(x, y + 30, 0);
		this.world.gg.gc.text.draw(name, 0.501f, Text.ALIGN.CENTER);
	}

}
