package com.game.server;

import com.game.packet.EntityItemAdd;

public class Block {
	public static void drop(int ID, float x, float y, World world) {
		EntityItem ei = new EntityItem(world, world.getID());
		switch (ID) {
		case 1:
			ei.ItemID = 2;
			break;
		case 2:
			ei.ItemID = 2;
			break;
		case 3:
			ei.ItemID = 3;
			break;
		}
		//ei.ItemID = inv.items[drop.mark].ID;
		ei.x = x;
		ei.y = y;
		ei.dy = .0f;
		ei.dx = .0f;
		world.entities.put(ei.ID, ei);
		EntityItemAdd eia = new EntityItemAdd();
		eia.ID = ei.ID;
		eia.ItemID = ei.ItemID;
		eia.x = ei.x;
		eia.y = ei.y;
		world.gs.server.sendToAllTCP(eia);
	}
}
