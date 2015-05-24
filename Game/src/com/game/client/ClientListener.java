package com.game.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.packet.Accept;

public class ClientListener extends Listener {

	private GuiGame gg;

	public ClientListener(GuiGame guiGame) {
		this.gg = guiGame;
	}

	@Override
	public void connected(Connection arg0) {
		gg.connected();
	}

	@Override
	public void received(Connection con, Object obj) {
		if (obj instanceof Accept) {
			Accept accept = (Accept) obj;
			gg.login(accept.ID, accept.name);
		}
	}

}
