package com.game.server;

import java.util.Scanner;

import com.game.util.Misc;

public class Console extends Thread {
	public GameServer gs;

	public Console(GameServer gs) {
		this.gs = gs;
		start();
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			String line = sc.nextLine();
			String[] part = line.split(" ");
			// Commands
			if (part.length == 1) {
				if (part[0].equalsIgnoreCase("stop")) {
					gs.server.close();
					gs.server.stop();
					gs.run = false;
					World.generator.save();
					break;
				}
				if (part[0].equalsIgnoreCase("list")) {
					for (Entity entity : gs.world.entities.values()) {
						if (entity instanceof EntityPlayer) {
							EntityPlayer ep = (EntityPlayer) entity;
							Misc.log(ep.name + " [ID] " + ep.ID + " [Ping] "
									+ ep.ping);
						}
					}
				}
			}
			if (part.length == 2) {
				if (part[0].equalsIgnoreCase("kick")) {
					Entity entity = gs.world.entities.get(Integer
							.parseInt(part[1]));
					if (entity instanceof EntityPlayer) {
						EntityPlayer ep = (EntityPlayer) entity;
						ep.con.close();
						continue;
					}
				}
			}
			if (part.length == 4) {
				// Give PlayerID ID Amount
				if (part[0].equalsIgnoreCase("give")) {
					Entity entity = gs.world.entities.get(Integer
							.parseInt(part[1]));
					if (entity instanceof EntityPlayer) {
						EntityPlayer ep = (EntityPlayer) entity;
						ep.inv.addItem(Integer.parseInt(part[2]),
								Integer.parseInt(part[3]));
						ep.sendInv();
						continue;
					}
				}
				// Remove PlayerID ID Amount
				if (part[0].equalsIgnoreCase("remove")) {
					Entity entity = gs.world.entities.get(Integer
							.parseInt(part[1]));
					if (entity instanceof EntityPlayer) {
						EntityPlayer ep = (EntityPlayer) entity;
						ep.inv.remItem(Integer.parseInt(part[2]),
								Integer.parseInt(part[3]));
						ep.sendInv();
						continue;
					}
				}
			}
		}
		sc.close();
	}
}
