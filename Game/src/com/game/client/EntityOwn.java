package com.game.client;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.game.item.Inventory;
import com.game.packet.Color;
import com.game.packet.EntityMove;
import com.game.packet.ItemDrop;
import com.game.packet.ItemSwitch;
import com.game.packet.PlayerClick;
import com.game.util.Box;
import com.game.util.Text;
import com.game.util.Text.ALIGN;

public class EntityOwn extends Entity {
	public String name;
	public Box box, ground;
	public Color hair, body, arm, shoe;
	public gState state;
	public Inventory inv;
	public Box inventory;

	public EntityOwn(World world, int ID) {
		super(world, ID);
		box = new Box(x, y, 10, 18);
		inventory = new Box(480, 270, 144, 104);
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
		GL11.glColor3f(0.4f, 1.0f, 0.4f);
		GL11.glTranslatef(x, y + 30, 0);
		this.world.gg.gc.text.draw(name, 0.501f, Text.ALIGN.CENTER);
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
		if (!Display.isActive())
			state = gState.menu;
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
			while (Mouse.next()) {
				// Play hit animation with item
				if (Mouse.getEventButtonState() && Mouse.getEventButton() == 0) {
					int X = world.gg.gc.mouseX;
					int Y = world.gg.gc.mouseY;
					float absx = x + X - 960 / 2;
					float absy = y + Y - 540 / 2;
					PlayerClick pc = new PlayerClick();
					pc.x = absx;
					pc.y = absy;
					world.gg.client.sendTCP(pc);
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
			// Marking with Mouse
			while (Mouse.next()) {
				if (Mouse.getEventButtonState()) {
					if (Mouse.getEventButton() == 0) {
						int X = world.gg.gc.mouseX;
						int Y = world.gg.gc.mouseY;
						if (mark == -1) {
							// Normal Gui
							for (int x = 0; x < 2; x++) {
								for (int y = 0; y < 4; y++) {
									Box box = new Box(544 + x * 48,
											342 - y * 48, 20, 20);
									Box mou = new Box(X, Y, 1, 1);
									if (box.check(mou)) {
										mark = x + y * 2;
									}
								}
							}
							// Equip Gui
							for (int x = 0; x < 2; x++) {
								for (int y = 0; y < 3; y++) {
									Box box = new Box(368 + x * 122,
											294 - y * 48, 20, 20);
									Box mou = new Box(X, Y, 1, 1);
									if (box.check(mou)) {
										mark = 8 + x + y * 2;
									}
								}
							}
						} else {
							Box mouse = new Box(X, Y, 1, 1);
							if (mouse.check(inventory)) {
								// Normal Gui
								int switcher = -1;
								for (int x = 0; x < 2; x++) {
									for (int y = 0; y < 4; y++) {
										Box box = new Box(544 + x * 48,
												342 - y * 48, 20, 20);
										Box mou = new Box(X, Y, 1, 1);
										if (box.check(mou)) {
											switcher = x + y * 2;
										}
									}
								}
								// Equip Gui
								for (int x = 0; x < 2; x++) {
									for (int y = 0; y < 3; y++) {
										Box box = new Box(368 + x * 122,
												294 - y * 48, 20, 20);
										Box mou = new Box(X, Y, 1, 1);
										if (box.check(mou)) {
											switcher = 8 + x + y * 2;
										}
									}
								}
								if (switcher != -1) {
									ItemSwitch is = new ItemSwitch();
									is.mark = mark;
									is.switcher = switcher;
									world.gg.client.sendTCP(is);
								}
								mark = -1;
							} else {
								ItemDrop drop = new ItemDrop();
								drop.mark = mark;
								world.gg.client.sendTCP(drop);
								mark = -1;
							}
						}
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
	int mark = -1; // Inventory Slot Marker

	public void gui() {
		GL11.glColor3f(1, 1, 1);
		switch (state) {
		case game:

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
			//			GL11.glScalef(1.25f, 1.25f, 1);
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
					inv.drawItem(x + y * 2, world);
					if (mark == x + y * 2) {
						// Draw Marker
						GL11.glColor4f(1, 1, 1, 1);
						world.gg.store.get("Marker").bind();
						GL11.glBegin(GL11.GL_QUADS);
						GL11.glTexCoord2f(1, 0);
						GL11.glVertex2f(24, 24);
						GL11.glTexCoord2f(0, 0);
						GL11.glVertex2f(-24, 24);
						GL11.glTexCoord2f(0, 1);
						GL11.glVertex2f(-24, -24);
						GL11.glTexCoord2f(1, 1);
						GL11.glVertex2f(24, -24);
						GL11.glEnd();
					}
					GL11.glPopMatrix();
				}
			}
			for (int x = 0; x < 2; x++) {
				for (int y = 0; y < 3; y++) {
					GL11.glPushMatrix();
					GL11.glTranslatef(368 + x * 122, 294 - y * 48, 0);
					inv.drawItem(8 + x + y * 2, world);
					if (mark == 8 + x + y * 2) {
						// Draw Marker
						GL11.glColor4f(1, 1, 1, 1);
						world.gg.store.get("Marker").bind();
						GL11.glBegin(GL11.GL_QUADS);
						GL11.glTexCoord2f(1, 0);
						GL11.glVertex2f(24, 24);
						GL11.glTexCoord2f(0, 0);
						GL11.glVertex2f(-24, 24);
						GL11.glTexCoord2f(0, 1);
						GL11.glVertex2f(-24, -24);
						GL11.glTexCoord2f(1, 1);
						GL11.glVertex2f(24, -24);
						GL11.glEnd();
					}
					GL11.glPopMatrix();
				}
			}
			// Item Info
			int X = world.gg.gc.mouseX;
			int Y = world.gg.gc.mouseY;
			// Normal Gui
			for (int x = 0; x < 2; x++) {
				for (int y = 0; y < 4; y++) {
					if (inv.items[x + y * 2] != null
							&& inv.amount[x + y * 2] != 0) {
						Box box = new Box(544 + x * 48, 342 - y * 48, 20, 20);
						Box mou = new Box(X, Y, 1, 1);
						if (box.check(mou)) {
							GL11.glPushMatrix();
							GL11.glTranslatef(X, Y, 0);
							GL11.glColor4f(1, 1, 1, 1);
							world.gg.store.get("ItemInfo").bind();
							GL11.glBegin(GL11.GL_QUADS);
							GL11.glTexCoord2f(1, 0);
							GL11.glVertex2f(128, 0);
							GL11.glTexCoord2f(0, 0);
							GL11.glVertex2f(0, 0);
							GL11.glTexCoord2f(0, 1);
							GL11.glVertex2f(0, -128);
							GL11.glTexCoord2f(1, 1);
							GL11.glVertex2f(128, -128);
							GL11.glEnd();
							// Item Name
							GL11.glTranslatef(64, -25, 0);
							String rarity = "(Common)";
							switch (inv.items[x + y * 2].rarity) {
							case 1:
								// White - Uncommon
								GL11.glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
								rarity = "(Uncommon)";
								break;
							case 2:
								// Green - Rare
								GL11.glColor4f(0.2f, 0.8f, 0.3f, 1.0f);
								rarity = "(Rare)";
								break;
							case 3:
								// Blue - Very Rare
								GL11.glColor4f(0.3f, 0.3f, 0.8f, 1.0f);
								rarity = "(Very Rare)";
								break;
							case 4:
								// Orange - Superior
								GL11.glColor4f(0.8f, 0.6f, 0.2f, 1.0f);
								rarity = "(Superior)";
								break;
							case 5:
								// Purple - Experimental
								GL11.glColor4f(0.5f, 0.2f, 0.6f, 1.0f);
								rarity = "(Experimental)";
								break;
							case 6:
								// Yellow - Legendary
								GL11.glColor4f(0.7f, 0.7f, 0.2f, 1.0f);
								rarity = "(Legendary)";
								break;
							case 7:
								// Red - Ultimate
								GL11.glColor4f(0.5f, 0.2f, 0.2f, 1.0f);
								rarity = "(Ultimate)";
								break;
							default:
								// Grey - Common
								GL11.glColor3f(50f / 255f, 50f / 255f,
										50f / 255f);
								rarity = "(Common)";
							}
							world.gg.gc.text.draw(inv.items[x + y * 2].name,
									0.5f, Text.ALIGN.CENTER);
							GL11.glTranslatef(0, -8, 0);
							world.gg.gc.text.draw(rarity, 0.5f,
									Text.ALIGN.CENTER);
							// Item Meta
							GL11.glTranslatef(-47, -4, 0);
							String[] meta = inv.items[x + y * 2].meta
									.split("\\|");
							GL11.glColor3f(50f / 255f, 50f / 255f, 50f / 255f);
							for (int i = 0; i < meta.length; i++) {
								GL11.glTranslatef(0, -8, 0);
								world.gg.gc.text.draw(meta[i], 0.5f,
										Text.ALIGN.LEFT);
							}
							GL11.glPopMatrix();
						}
					}
				}
			}
			// Equip Gui
			for (int x = 0; x < 2; x++) {
				for (int y = 0; y < 3; y++) {
					if (inv.items[8 + x + y * 2] != null
							&& inv.amount[8 + x + y * 2] != 0) {
						Box box = new Box(368 + x * 122, 294 - y * 48, 20, 20);
						Box mou = new Box(X, Y, 1, 1);
						if (box.check(mou)) {
							GL11.glPushMatrix();
							GL11.glTranslatef(X, Y, 0);
							GL11.glColor4f(1, 1, 1, 1);
							world.gg.store.get("ItemInfo").bind();
							GL11.glBegin(GL11.GL_QUADS);
							GL11.glTexCoord2f(1, 0);
							GL11.glVertex2f(128, 0);
							GL11.glTexCoord2f(0, 0);
							GL11.glVertex2f(0, 0);
							GL11.glTexCoord2f(0, 1);
							GL11.glVertex2f(0, -128);
							GL11.glTexCoord2f(1, 1);
							GL11.glVertex2f(128, -128);
							GL11.glEnd();
							// Item Name
							GL11.glTranslatef(64, -25, 0);
							String rarity = "(Common)";
							switch (inv.items[8 + x + y * 2].rarity) {
							case 1:
								// White - Uncommon
								GL11.glColor4f(0.8f, 0.8f, 0.8f, 1.0f);
								rarity = "(Uncommon)";
								break;
							case 2:
								// Green - Rare
								GL11.glColor4f(0.2f, 0.8f, 0.3f, 1.0f);
								rarity = "(Rare)";
								break;
							case 3:
								// Blue - Very Rare
								GL11.glColor4f(0.3f, 0.3f, 0.8f, 1.0f);
								rarity = "(Very Rare)";
								break;
							case 4:
								// Orange - Superior
								GL11.glColor4f(0.8f, 0.6f, 0.2f, 1.0f);
								rarity = "(Superior)";
								break;
							case 5:
								// Purple - Experimental
								GL11.glColor4f(0.5f, 0.2f, 0.6f, 1.0f);
								rarity = "(Experimental)";
								break;
							case 6:
								// Yellow - Legendary
								GL11.glColor4f(0.7f, 0.7f, 0.2f, 1.0f);
								rarity = "(Legendary)";
								break;
							case 7:
								// Red - Ultimate
								GL11.glColor4f(0.5f, 0.2f, 0.2f, 1.0f);
								rarity = "(Ultimate)";
								break;
							default:
								// Grey - Common
								GL11.glColor3f(50f / 255f, 50f / 255f,
										50f / 255f);
								rarity = "(Common)";
							}
							world.gg.gc.text.draw(
									inv.items[8 + x + y * 2].name, 0.5f,
									Text.ALIGN.CENTER);
							GL11.glTranslatef(0, -8, 0);
							world.gg.gc.text.draw(rarity, 0.5f,
									Text.ALIGN.CENTER);
							// Item Meta
							GL11.glTranslatef(-47, -8, 0);
							String[] meta = inv.items[8 + x + y * 2].meta
									.split("\\|");
							GL11.glColor3f(50f / 255f, 50f / 255f, 50f / 255f);
							for (int i = 0; i < meta.length; i++) {
								GL11.glTranslatef(0, -8, 0);
								world.gg.gc.text.draw(meta[i], 0.5f,
										Text.ALIGN.LEFT);
							}
							GL11.glPopMatrix();
						}
					}
				}
			}
			break;
		case menu:
			world.gg.store.get("Menu").bind();
			GL11.glPushMatrix();
			GL11.glTranslatef(960 / 2, 540 / 2, 0);
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
			// Inventory
			GL11.glColor3f(50f / 255f, 50f / 255f, 50f / 255f);
			GL11.glTranslatef(960 / 2, 540 / 2 + 64, 0);
			world.gg.gc.text.draw("Menu", 1, ALIGN.CENTER);
			GL11.glTranslatef(0, -48, 0);
			world.gg.gc.text.draw("Main Menu", 1, ALIGN.CENTER);
			GL11.glTranslatef(-68, -48, 0);
			world.gg.gc.text.draw("Options", 1, ALIGN.CENTER);
			GL11.glTranslatef(136, 0, 0);
			world.gg.gc.text.draw("Exit", 1, ALIGN.CENTER);
			GL11.glTranslatef(-68, -48, 0);
			world.gg.gc.text.draw("Back to Game", 1, ALIGN.CENTER);
			GL11.glPopMatrix();
			break;
		}
	}

	// Gui State
	private enum gState {
		game, inventory, menu;
	}
}
