package com.game.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.game.packet.EntityMove;
import com.game.util.Box;
import com.game.util.Text;

public class EntityOwn extends Entity {
	public String name;
	public Box box, ground;

	public EntityOwn(World world, int ID) {
		super(world, ID);
		box = new Box(x, y, 5, 5);
		ground = new Box(x, y - box.getDy() - 1, 5, 1);
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
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			dx = -.1f;
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
		GL11.glTranslatef(x, y, 0);
		GL11.glColor4f(0.8f, 0.4f, 0.2f, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-5, -5);
		GL11.glVertex2f(5, -5);
		GL11.glVertex2f(5, 5);
		GL11.glVertex2f(-5, 5);
		GL11.glEnd();
		// Text Rendering
		GL11.glColor3f(0.4f, 1.0f, 0.4f);
		GL11.glTranslatef(0, 10, 0);
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
		world.gg.client.sendUDP(em);
	}
}
