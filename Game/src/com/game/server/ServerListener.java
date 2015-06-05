package com.game.server;

import java.io.File;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.file.Tag;
import com.game.file.TagString;
import com.game.file.TagSubtag;
import com.game.packet.Login;
import com.game.util.Misc;

public class ServerListener extends Listener {
	public GameServer gs;

	public ServerListener(GameServer gs) {
		this.gs = gs;
	}

	@Override
	public void received(Connection con, Object obj) {
		if (obj instanceof Login) {
			Login login = (Login) obj;
			boolean offline = true;
			File file = new File("./server/" + login.name + ".cgx");
			if (!file.exists()) {
				// Account does not exist
				con.close();
				return;
			}
			TagSubtag user = (TagSubtag) Tag.load(file);
			TagSubtag info = (TagSubtag) user.getTag("CharInfo");
			TagString pass = (TagString) info.getTag("Password");
			TagString name = (TagString) info.getTag("Username");
			for (Entity entity : gs.world.entities.values()) {
				if (entity instanceof EntityPlayer) {
					EntityPlayer ep = (EntityPlayer) entity;
					if (ep.name.equalsIgnoreCase(name.getValue())) {
						offline = false;
						break;
					}
				}
			}
			if (offline) {
				if (login.pass.equals(pass.getValue())) {
					gs.world.login(name.getValue(), con, user);
					Misc.log(name.getValue() + " joined the Game.");
				} else {
					// Password incorrect
					con.close();
				}
			} else {
				// Already Logged In
				con.close();
			}
		}
	}
}
