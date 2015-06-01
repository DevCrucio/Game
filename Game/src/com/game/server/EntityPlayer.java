package com.game.server;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.file.Tag;
import com.game.file.TagBoolean;
import com.game.file.TagFloat;
import com.game.file.TagSubtag;
import com.game.packet.ChunkAdd;
import com.game.packet.ChunkRem;
import com.game.packet.EntityMove;
import com.game.packet.EntityRem;
import com.game.util.Misc;

public class EntityPlayer extends Entity {
	public String name;
	public Connection con;
	public Tag tag;
	List<Chunk> chunks = new CopyOnWriteArrayList<Chunk>();

	public EntityPlayer(World world, int ID, Connection con, Tag tag) {
		super(world, ID);
		this.con = con;
		con.addListener(new PlayerListener());
		this.tag = tag;

	}

	@Override
	public void update(float delta) {
		int cx = (int) Math.floor(x / 256);
		int cy = (int) Math.floor(y / 256);
		// Removing Chunks
		for (Chunk chunk : chunks) {
			boolean rem = true;
			out: for (int ax = cx - 2; ax <= cx + 2; ax++) {
				for (int ay = cy - 2; ay <= cy + 2; ay++) {
					if (chunk.x == ax && chunk.y == ay) {
						rem = false;
						break out;
					}
				}
			}
			if (rem) {
				remChunk(chunk.x, chunk.y);
			}
		}
		// Adding Chunks
		for (int ax = cx - 2; ax <= cx + 2; ax++) {
			for (int ay = cy - 2; ay <= cy + 2; ay++) {
				if (!hasChunk(ax, ay)) {
					addChunk(ax, ay);
				}
			}
		}

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
				lookLeft = em.lookLeft;
			}
		}

		@Override
		public void disconnected(Connection con) {
			EntityRem rem = new EntityRem();
			rem.ID = ID;
			world.gs.server.sendToAllTCP(rem);
			world.entities.remove(ID);
			TagSubtag user = (TagSubtag) tag;
			TagSubtag loc = (TagSubtag) user.getTag("Location");
			TagFloat tx = (TagFloat) loc.getTag("X");
			tx.setValue(x);
			TagFloat ty = (TagFloat) loc.getTag("Y");
			ty.setValue(y);
			TagBoolean tlookLeft = (TagBoolean) loc.getTag("LookLeft");
			tlookLeft.setValue(lookLeft);
			Tag.save(tag, new File(tag.getName()));
			Misc.log(name + " disconnected.");
		}
	}

	// Chunk methods
	public boolean hasChunk(int x, int y) {
		for (Chunk chunk : chunks) {
			if (chunk.x == x && chunk.y == y) {
				return true;
			}
		}
		return false;
	}

	public void addChunk(int x, int y) {
		Chunk chunk = world.getChunk(x, y);
		ChunkAdd ca = new ChunkAdd();
		ca.x = chunk.x;
		ca.y = chunk.y;
		ca.block = chunk.block;
		chunks.add(chunk);
		con.sendTCP(ca);
	}

	public void remChunk(int x, int y) {
		for (Chunk chunk : chunks) {
			if (chunk.x == x && chunk.y == y) {
				ChunkRem cr = new ChunkRem();
				cr.x = x;
				cr.y = y;
				con.sendTCP(cr);
				chunks.remove(chunk);
				return;
			}
		}
	}
}
