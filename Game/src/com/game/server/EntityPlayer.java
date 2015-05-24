package com.game.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.packet.EntityMove;
import com.game.packet.EntityRem;

public class EntityPlayer extends Entity {
	public String name;
	public Connection con;

	public EntityPlayer(World world, int ID, Connection con) {
		super(world, ID);
		this.con = con;
		con.addListener(new PlayerListener());
	}

	@Override
	public void update(float delta) {
		x += dx * delta;
		y += dy * delta;
	}

	@Override
	public void send() {
		EntityMove em = new EntityMove();
		em.x = x;
		em.y = y;
		em.dx = dx;
		em.dy = dy;
		em.ID = ID;
		world.gs.server.sendToAllExceptUDP(con.getID(), em);
	}

	private class PlayerListener extends Listener {
		@Override
		public void received(Connection con, Object obj) {
			if (obj instanceof EntityMove) {
				EntityMove em = (EntityMove) obj;
				x = em.x;
				y = em.y;
				dx = em.dx;
				dy = em.dy;
			}
		}

		@Override
		public void disconnected(Connection con) {
			EntityRem rem = new EntityRem();
			rem.ID = ID;
			world.gs.server.sendToAllTCP(rem);
			world.entities.remove(ID);
		}
	}
}
