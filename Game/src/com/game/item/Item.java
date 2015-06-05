package com.game.item;

public abstract class Item {
	public abstract void render();

	/*
	 * Standard Methods
	 */
	public final int ID;
	public int maxStack;
	public String name = "";
	public String meta = "";

	public Item(int ID) {
		this.ID = ID;
		items[ID] = this;
		maxStack = 100;
	}

	public static void render(int num) {
		if (items[num] != null) {
			items[num].render();
		}
	}

	public static Item[] items = new Item[1024];
	/*
	 * Items
	 */
	public static Item ITEM_STONESWORD = new ItemStoneSword(0, 1);
	public static Item ITEM_STONEPICKAXE = new ItemStonePick(1, 16);
}
