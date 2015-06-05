package com.game.server;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.game.file.Tag;
import com.game.file.TagBoolean;
import com.game.file.TagFloat;
import com.game.file.TagInt;
import com.game.file.TagString;
import com.game.file.TagSubtag;
import com.game.item.Inventory;
import com.game.item.Item;
import com.game.packet.ChunkAdd;
import com.game.packet.ChunkRem;
import com.game.packet.Color;
import com.game.packet.EntityMove;
import com.game.packet.EntityRem;
import com.game.packet.SendItem;
import com.game.util.Misc;

public class EntityPlayer extends Entity {
	public String name;
	public Connection con;
	public int ping;
	public Tag tag;
	List<Chunk> chunks = new CopyOnWriteArrayList<Chunk>();
	public Color hair, arm, body, shoe;
	public Inventory inv;

	public EntityPlayer(World world, int ID, Connection con, Tag tag) {
		super(world, ID);
		this.con = con;
		con.addListener(new PlayerListener());
		this.tag = tag;
		inv = new Inventory();
		// Load Inventory from tag
		TagSubtag main = (TagSubtag) tag;
		if (main.hasTag("Inventory")) {
			TagSubtag inv = (TagSubtag) main.getTag("Inventory");
			for (int i = 0; i < 14; i++) {
				TagSubtag item = (TagSubtag) inv.getTag("Item" + i);
				TagInt id = (TagInt) item.getTag("ID");
				if (id.getValue() != -1) {
					this.inv.items[i] = Item.items[id.getValue()];
					TagInt am = (TagInt) item.getTag("Amount");
					this.inv.amount[i] = am.getValue();
					TagString name = (TagString) item.getTag("Name");
					this.inv.items[i].name = name.getValue();
					TagString meta = (TagString) item.getTag("Meta");
					this.inv.items[i].meta = meta.getValue();
				}
			}
		} else {
			TagSubtag inv = new TagSubtag("Inventory");
			for (int i = 0; i < 14; i++) {
				TagSubtag item = new TagSubtag("Item" + i);
				// Write data inside
				TagInt id = new TagInt("ID");
				id.setValue(-1);
				item.addTag(id);
				TagInt am = new TagInt("Amount");
				am.setValue(0);
				item.addTag(am);
				TagString name = new TagString("Name");
				name.setValue("");
				item.addTag(name);
				TagString meta = new TagString("Meta");
				meta.setValue("");
				item.addTag(meta);
				inv.addTag(item);
			}
			main.addTag(inv);
		}
		sendInv();

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
				return;
			}
		}
		// Adding Chunks
		for (int ax = cx - 2; ax <= cx + 2; ax++) {
			for (int ay = cy - 2; ay <= cy + 2; ay++) {
				if (!hasChunk(ax, ay)) {
					addChunk(ax, ay);
					return;
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

	public void sendInv() {
		SendItem si = new SendItem();
		si.items = new int[inv.items.length];
		si.amount = new int[inv.amount.length];
		si.name = new String[inv.amount.length];
		si.meta = new String[inv.amount.length];
		for (int i = 0; i < inv.items.length; i++) {
			if (inv.items[i] == null) {
				si.items[i] = -1;
				si.amount[i] = 0;
			} else {
				si.items[i] = inv.items[i].ID;
				si.amount[i] = inv.amount[i];
				si.name[i] = inv.items[i].name;
				si.meta[i] = inv.items[i].meta;
			}
		}
		con.sendTCP(si);
	}

	private class PlayerListener extends Listener {
		@Override
		public void received(Connection con, Object obj) {
			con.updateReturnTripTime();
			ping = con.getReturnTripTime();
			if (obj instanceof EntityMove) {
				EntityMove em = (EntityMove) obj;
				x = em.x;
				y = em.y;
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
			// Put locations
			TagSubtag user = (TagSubtag) tag;
			TagSubtag loc = (TagSubtag) user.getTag("Location");
			TagFloat tx = (TagFloat) loc.getTag("X");
			tx.setValue(x);
			TagFloat ty = (TagFloat) loc.getTag("Y");
			ty.setValue(y);
			TagBoolean tlookLeft = (TagBoolean) loc.getTag("LookLeft");
			tlookLeft.setValue(lookLeft);
			// Put Inventory
			TagSubtag inve = (TagSubtag) ((TagSubtag) tag).getTag("Inventory");
			for (int i = 0; i < 14; i++) {
				TagSubtag item = (TagSubtag) inve.getTag("Item" + i);
				TagInt id = (TagInt) item.getTag("ID");
				TagInt am = (TagInt) item.getTag("Amount");
				TagString name = (TagString) item.getTag("Name");
				TagString meta = (TagString) item.getTag("Meta");
				if (inv.items[i] != null) {
					id.setValue(inv.items[i].ID);
					am.setValue(inv.amount[i]);
					name.setValue(inv.items[i].name);
					meta.setValue(inv.items[i].meta);
				} else {
					id.setValue(-1);
					am.setValue(0);
					name.setValue("");
					meta.setValue("");
				}
			}
			// Save complete
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
