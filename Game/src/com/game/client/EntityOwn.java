package com.game.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.game.item.Inventory;
import com.game.packet.Color;
import com.game.packet.EntityMove;
import com.game.util.Box;
import com.game.util.Text;
import com.game.util.Text.ALIGN;

public class EntityOwn extends Entity {
	public String name;
	public Box box, ground;
	public Color hair, body, arm, shoe;
	public gState state;
	public Inventory inv;

	public EntityOwn(World world, int ID) {
		super(world, ID);
		box = new Box(x, y, 10, 18);
		ground = new Box(x, y - box.getDy() - 1, 5, 1);
		hair = new Color();
		body = new Color();
		arm = new Color();
		shoe = new Color();
		state = gState.game;
		inv = new Inventory();
	}

	public void cam() {
		GL11.glTranslatef(480 - x, 270 - y, 0);
	}

	@Override
	public void update(float delta) {
		// Collision Detection
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
	}

	@Override
	public void renderName() {
		// Text Rendering
		GL11.glColor3f(0.4f, 1.0f, 0.4f);
		GL11.glTranslatef(x, y + 30, 0);
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

	// Update 
	public void poll(float delta) {
		switch (state) {
		case game:
			// JUMP
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				if (world.check(ground) != null && dy <= 0) {
					dy = .4f;
				}
			}
			// LEFT RIGHT
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				dx = .2f;
				lookLeft = false;
			} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				dx = -.2f;
				lookLeft = true;
			} else {
				dx = 0;
			}
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {// pressed
					if (Keyboard.getEventKey() == Keyboard.KEY_E) {
						dx = 0;
						state = gState.inventory;
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
						dx = 0;
						state = gState.menu;
					}
				}
			}
			break;
		case inventory:
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {// pressed
					if (Keyboard.getEventKey() == Keyboard.KEY_E) {
						state = gState.game;
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
						state = gState.menu;
					}
				}
			}
			break;
		case menu:
			while (Keyboard.next()) {
				if (Keyboard.getEventKeyState()) {// pressed
					if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
						state = gState.game;
					}
				}
			}
			break;
		}
	}

	// Gui Render

	public void gui() {
		GL11.glColor3f(1, 1, 1);
		switch (state) {
		case game:
			world.gg.gc.text.draw("Game", 1f, ALIGN.LEFT);
			break;
		case inventory:
			world.gg.store.get("Inv").bind();
			GL11.glPushMatrix();
			GL11.glTranslatef(960 / 2, 540 / 2, 0);
			//			GL11.glScalef(2, 2, 1);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(152, 112);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(-152, 112);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(-152, -112);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(152, -112);
			GL11.glEnd();
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			// Player
			GL11.glTranslatef(960 / 2 - 48, 540 / 2 - 24, 0);
			GL11.glScalef(2, 2, 1);
			if (lookLeft)
				GL11.glScalef(-1, 1, 1);
			GL11.glTranslatef(-x, -y + 2, 0);
			render();
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			// Inventory
			GL11.glColor3f(50f / 255f, 50f / 255f, 50f / 255f);
			GL11.glTranslatef(960 / 2 - 48, 540 / 2 + 64, 0);
			world.gg.gc.text.draw("Inventory", 1, ALIGN.CENTER);
			GL11.glPopMatrix();
			for (int x = 0; x < 2; x++) {
				for (int y = 0; y < 4; y++) {
					GL11.glPushMatrix();
					GL11.glTranslatef(544 + x * 48, 342 - y * 48, 0);
					inv.drawItem(x + y * 2);
					GL11.glPopMatrix();
				}
			}
			for (int x = 0; x < 2; x++) {
				for (int y = 0; y < 3; y++) {
					GL11.glPushMatrix();
					GL11.glTranslatef(368 + x * 122, 294 - y * 48, 0);
					inv.drawItem(8 + x + y * 2);
					GL11.glPopMatrix();
				}
			}
			break;
		case menu:
			world.gg.gc.text.draw("Menu", 1f, ALIGN.LEFT);
			break;
		}
	}

	// Gui State
	private enum gState {
		game, inventory, menu;
	}
}
