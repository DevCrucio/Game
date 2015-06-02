package com.game.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.game.packet.Color;
import com.game.packet.EntityMove;
import com.game.util.Box;
import com.game.util.Text;

public class EntityOwn extends Entity {
	public String name;
	public Box box, ground;
	public Color hair, body, arm, shoe;

	public EntityOwn(World world, int ID) {
		super(world, ID);
		box = new Box(x, y, 10, 18);
		ground = new Box(x, y - box.getDy() - 1, 5, 1);
		hair = new Color();
		body = new Color();
		arm = new Color();
		shoe = new Color();
	}

	public void poll(float delta) {
		// JUMP
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (world.check(ground) != null && dy <= 0) {
				dy = .2f;
			}
		}
		// LEFT RIGHT
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			dx = .1f;
			lookLeft = false;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			dx = -.1f;
			lookLeft = true;
		} else {
			dx = 0;
		}
		// Speed
	}

	public void cam() {
		GL11.glTranslatef(480 - x, 270 - y, 0);
	}

	public void gui() {

	}

	@Override
	public void update(float delta) {
		// TODO: Collision Detection
		x += dx * delta;
		updateBox();
		Box check = world.check(box);
		if (check != null)
			while (true) {
				if (dx > 0) {
					x = check.getX() - check.getDx() - box.getDx() - 0.001f;
				} else if (dx < 0) {
					x = check.getX() + check.getDx() + box.getDx() + 0.001f;
				}
				updateBox();
				check = world.check(box);
				if (check == null || dx == 0) {
					dx = 0;
					break;
				}
			}
		dy -= 0.001f * delta;
		y += dy * delta;
		updateBox();
		check = world.check(box);
		if (check != null)
			while (true) {
				if (dy > 0) {
					y = check.getY() - check.getDy() - box.getDy() - 0.001f;
				} else if (dy < 0) {
					y = check.getY() + check.getDy() + box.getDy() + 0.001f;
				}
				updateBox();
				check = world.check(box);
				if (check == null || dy == 0) {
					dy = 0;
					break;
				}

			}
		updateBox();
	}

	// Box update
	public void updateBox() {
		box.setX(x);
		box.setY(y);
		ground.setX(x);
		ground.setY(y - box.getDy() - ground.getDy());
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
		// Text Rendering
		if (!lookLeft)
			GL11.glScalef(-1, 1, 1);
		GL11.glColor3f(0.4f, 1.0f, 0.4f);
		GL11.glTranslatef(0, 32, 0);
		this.world.gg.gc.text.draw(name, 0.5f, Text.ALIGN.CENTER);
	}

	/*
	 * Send
	 */
	public void send() {
		EntityMove em = new EntityMove();
		em.x = x;
		em.y = y;
		em.dx = dx;
		em.dy = dy;
		em.lookLeft = lookLeft;
		world.gg.client.sendUDP(em);
	}
}
