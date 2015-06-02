package com.game.item;

public abstract class Item {
	public abstract void render();

	/*
	 * Standard Methods
	 */
	public Item(int ID) {
		items[ID] = this;
	}

	public static void render(int num) {
		if (items[num] != null) {
			items[num].render();
		}
	}

	private static Item[] items = new Item[1024];
	/*
	 * Items
	 */
	public static Item ITEM_STONESWORD = new ItemStoneSword(0);
}
