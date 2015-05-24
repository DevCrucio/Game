package com.game.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.packet.Login;

public class ServerListener extends Listener {
	public GameServer gs;

	public ServerListener(GameServer gs) {
		this.gs = gs;
	}

	@Override
	public void received(Connection con, Object obj) {
		if (obj instanceof Login) {
			Login login = (Login) obj;
			if (login.name.equals("Test") && login.pass.equals("abc123")) {
				gs.world.login("Test", con);
			}
		}
	}
}
