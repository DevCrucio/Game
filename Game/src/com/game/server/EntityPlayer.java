package com.game.server;

import java.io.File;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.file.Tag;
import com.game.packet.EntityMove;
import com.game.packet.EntityRem;

public class EntityPlayer extends Entity {
	public String name;
	public Connection con;
	public Tag tag;

	public EntityPlayer(World world, int ID, Connection con, Tag tag) {
		super(world, ID);
		this.con = con;
		con.addListener(new PlayerListener());
		this.tag = tag;
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
		em.lookLeft = lookLeft;
		world.gs.server.sendToAllExceptUDP(con.getID(), em);
	}

	private class PlayerListener extends Listener {
		@Override
		public void received(Connection con, Object obj) {
			if (obj instanceof EntityMove) {
				EntityMove em = (EntityMove) obj;
				con.updateReturnTripTime();
				float ping = con.getReturnTripTime();
				x = em.x + em.dx * ping;
				y = em.y + em.dy * ping;
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
			Tag.save(tag, new File(tag.getName()));
		}
	}
}
