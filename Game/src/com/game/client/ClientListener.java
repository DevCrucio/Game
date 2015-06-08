package com.game.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.item.Item;
import com.game.packet.Accept;
import com.game.packet.ChunkAdd;
import com.game.packet.ChunkRem;
import com.game.packet.EntityMove;
import com.game.packet.EntityRem;
import com.game.packet.ItemSend;
import com.game.packet.PlayerAdd;
import com.game.util.Misc;

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
		try {
			if (obj instanceof Accept) {
				Accept accept = (Accept) obj;
				gg.login(accept.ID, accept.name);
				gg.world.player.arm = accept.arm;
				gg.world.player.body = accept.body;
				gg.world.player.hair = accept.hair;
				gg.world.player.shoe = accept.shoe;
				gg.world.player.x = accept.x;
				gg.world.player.y = accept.y;
				gg.world.player.lookLeft = accept.lookLeft;
			} else if (obj instanceof PlayerAdd) {
				PlayerAdd ap = (PlayerAdd) obj;
				EntityPlayer ep = new EntityPlayer(gg.world, ap.ID);
				ep.name = ap.name;
				ep.x = ap.x;
				ep.lerpX = ap.x;
				ep.y = ap.y;
				ep.lerpY = ap.y;
				ep.lookLeft = ap.lookLeft;
				ep.arm = ap.arm;
				ep.body = ap.body;
				ep.hair = ap.hair;
				ep.shoe = ap.shoe;
				gg.world.entities.put(ap.ID, ep);
			} else if (obj instanceof EntityMove) {
				EntityMove em = (EntityMove) obj;
				Entity entity = gg.world.entities.get(em.ID);
				con.updateReturnTripTime();
				float ping = con.getReturnTripTime();
				entity.lerpX = em.x + em.dx * ping;
				entity.lerpY = em.y + em.dy * ping;
				entity.dx = em.dx;
				entity.dy = em.dy;
				entity.lookLeft = em.lookLeft;
			} else if (obj instanceof EntityRem) {
				EntityRem rem = (EntityRem) obj;
				gg.world.entities.remove(rem.ID);
			} else if (obj instanceof ChunkAdd) {
				ChunkAdd ca = (ChunkAdd) obj;
				Chunk chunk = new Chunk(ca.x, ca.y, ca.block);
				gg.world.chunks.put(ca.x + "x" + ca.y, chunk);
			} else if (obj instanceof ChunkRem) {
				ChunkRem cr = (ChunkRem) obj;
				gg.world.chunks.get(cr.x + "x" + cr.y).destroy();
				gg.world.chunks.remove(cr.x + "x" + cr.y);
			} else if (obj instanceof ItemSend) {
				ItemSend si = (ItemSend) obj;
				gg.world.player.inv.amount = si.amount;
				for (int i = 0; i < si.items.length; i++) {
					if (si.items[i] != -1) {
						gg.world.player.inv.items[i] = Item.items[si.items[i]]
								.clone();
						gg.world.player.inv.items[i].name = si.name[i];
						gg.world.player.inv.items[i].meta = si.meta[i];
					} else {
						gg.world.player.inv.items[i] = null;
					}
				}
			}
		} catch (NullPointerException e) {
			Misc.log("NullPointerException - Error");
		}

	}

	@Override
	public void disconnected(Connection con) {
		Misc.log("Disconnected? :O");
		System.exit(0);
	}
}
