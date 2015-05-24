package com.game.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.game.packet.EntityMove;

public class EntityPlayer extends Entity {
	public String name;

	public EntityPlayer(World world, int ID) {
		super(world, ID);
	}

	public void poll(float delta) {
		// UP DOWN
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			dy = 1f;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			dy = -1f;
		} else {
			dy = 0;
		}
		// LEFT RIGHT
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			dx = 1f;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			dx = -1f;
		} else {
			dx = 0;
		}
		// Speed
		float speed = .1f;
		float current = (float) Math.sqrt(dx * dx + dy * dy);
		if (current > speed) {
			dx = dx / current * speed;
			dy = dy / current * speed;
		}
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
		y += dy * delta;
	}

	@Override
	public void render() {
		// Base Quad Rendering
		GL11.glTranslatef(x, y, 0);
		GL11.glColor4f(0.8f, 0.4f, 0.2f, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-5, -5);
		GL11.glVertex2f(5, -5);
		GL11.glVertex2f(5, 5);
		GL11.glVertex2f(-5, 5);
		GL11.glEnd();
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
		world.gg.client.sendUDP(em);
	}
}
