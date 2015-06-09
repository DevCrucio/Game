package com.game.item;

import org.lwjgl.opengl.GL11;

import com.game.client.World;
import com.game.util.Text.ALIGN;

public class Inventory {
	// 8 Slot Inventory
	public Item[] items;
	public int[] amount;

	public Inventory() {
		items = new Item[14];
		amount = new int[14];
	}

	public void drawItem(int num, World world) {
		if (items[num] != null && amount[num] > 0) {
			GL11.glColor3f(189f / 255f, 189f / 255f, 189f / 255f);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(-8, -8);
			GL11.glVertex2f(8, -8);
			GL11.glVertex2f(8, 8);
			GL11.glVertex2f(-8, 8);
			GL11.glEnd();
			GL11.glColor4f(1, 1, 1, 1);
			world.gg.store.get("Item").bind();
			items[num].render();
			if (amount[num] != 1) {
				GL11.glPushMatrix();
				GL11.glColor3f(50f / 255f, 50f / 255f, 50f / 255f);
				GL11.glTranslatef(12, -14, 0);
				world.gg.gc.text.draw(amount[num] + "", 0.5f, ALIGN.RIGHT);
				GL11.glPopMatrix();
			}
		}
	}

	public boolean addItem(Item item, int amount) {
		for (int i = 0; i < 8; i++) {
			if (this.amount[i] == 0) {
				items[i] = null;
			}
			if (items[i] != null) {
				if (compare(items[i], item)) {
					if (this.amount[i] < items[i].maxStack) {
						if (this.amount[i] + amount <= items[i].maxStack) {
							this.amount[i] += amount;
							return true;
						} else {
							amount -= (items[i].maxStack - this.amount[i]);
							this.amount[i] = items[i].maxStack;
						}
					}
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			if (items[i] == null) {
				items[i] = item;
				this.amount[i] = amount;
				return true;
			}
		}
		return false;

	}

	public boolean addItem(int ID, int amount) {
		Item item = Item.items[ID].clone();
		return addItem(item, amount);
	}

	public boolean compare(Item i1, Item i2) {
		if (i1.ID == i2.ID && i1.meta.equals(i2.meta)
				&& i1.name.equals(i2.name) && i1.rarity == i2.rarity) {
			return true;
		}
		return false;
	}

	public int countItem(int ID) {
		int amo = 0;
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				if (items[i].ID == ID) {
					amo += amount[i];
				}
			}
		}
		return amo;
	}

	public boolean remItem(int ID, int amo) {
		if (countItem(ID) >= amo) {
			for (int i = 0; i < items.length; i++) {
				if (items[i] != null) {
					if (items[i].ID == ID) {
						if (amount[i] > amo) {
							amount[i] -= amo;
							return true;
						} else if (amount[i] == amo) {
							amount[i] = 0;
							items[i] = null;
							return true;
						} else {
							amo -= amount[i];
							amount[i] = 0;
							items[i] = null;
						}
					}
				}
			}
		}
		return false;
	}
}
