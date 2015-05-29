package com.game.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.packet.Accept;
import com.game.packet.EntityMove;
import com.game.packet.EntityRem;
import com.game.packet.PlayerAdd;

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
			gg.world.player.x = accept.x;
			gg.world.player.y = accept.y;
			gg.world.player.lookLeft = accept.lookLeft;
		}
		if (obj instanceof PlayerAdd) {
			PlayerAdd ap = (PlayerAdd) obj;
			EntityPlayer ep = new EntityPlayer(gg.world, ap.ID);
			ep.name = ap.name;
			ep.x = ap.x;
			ep.y = ap.y;
			ep.lookLeft = ap.lookLeft;
			gg.world.entities.put(ap.ID, ep);
		}
		if (obj instanceof EntityMove) {
			EntityMove em = (EntityMove) obj;
			Entity entity = gg.world.entities.get(em.ID);
			con.updateReturnTripTime();
			float ping = con.getReturnTripTime();
			entity.lerpX = em.x + em.dx * ping;
			entity.lerpY = em.y + em.dy * ping;
			entity.dx = em.dx;
			entity.dy = em.dy;
			entity.lookLeft = em.lookLeft;
		}
		if (obj instanceof EntityRem) {
			EntityRem rem = (EntityRem) obj;
			gg.world.entities.remove(rem.ID);
		}
	}

}
