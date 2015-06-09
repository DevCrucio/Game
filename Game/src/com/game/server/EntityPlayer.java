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
import com.game.item.TypeBlock;
import com.game.item.TypeBody;
import com.game.item.TypeBottle;
import com.game.item.TypeHelmet;
import com.game.item.TypePickaxe;
import com.game.item.TypeShield;
import com.game.item.TypeShoe;
import com.game.item.TypeWeapon;
import com.game.packet.ChunkAdd;
import com.game.packet.ChunkChange;
import com.game.packet.ChunkRem;
import com.game.packet.Color;
import com.game.packet.EntityItemAdd;
import com.game.packet.EntityMove;
import com.game.packet.EntityRem;
import com.game.packet.ItemDrop;
import com.game.packet.ItemEquip;
import com.game.packet.ItemSend;
import com.game.packet.ItemSwitch;
import com.game.packet.PlayerClick;
import com.game.util.Box;
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
		box = new Box(x, y, 10, 18);
		inv = new Inventory();
		// Load Inventory from tag
		TagSubtag main = (TagSubtag) tag;
		if (main.hasTag("Inventory")) {
			TagSubtag inv = (TagSubtag) main.getTag("Inventory");
			for (int i = 0; i < 14; i++) {
				TagSubtag item = (TagSubtag) inv.getTag("Item" + i);
				TagInt id = (TagInt) item.getTag("ID");
				if (id.getValue() != -1) {
					this.inv.items[i] = Item.items[id.getValue()].clone();
					TagInt am = (TagInt) item.getTag("Amount");
					this.inv.amount[i] = am.getValue();
					TagString name = (TagString) item.getTag("Name");
					this.inv.items[i].name = name.getValue();
					TagString meta = (TagString) item.getTag("Meta");
					this.inv.items[i].meta = meta.getValue();
					TagInt rare = (TagInt) item.getTag("Rarity");
					this.inv.items[i].rarity = rare.getValue();
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
				TagInt rare = new TagInt("Rarity");
				rare.setValue(0);
				item.addTag(rare);
				inv.addTag(item);
			}
			main.addTag(inv);
		}
		sendInv();

	}

	public void updateBox() {
		box.setX(x);
		box.setY(y);
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
		updateBox();

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
		ItemSend si = new ItemSend();
		si.items = new int[inv.items.length];
		si.amount = new int[inv.amount.length];
		si.name = new String[inv.amount.length];
		si.meta = new String[inv.amount.length];
		si.rarity = new int[inv.amount.length];
		for (int i = 0; i < inv.items.length; i++) {
			if (inv.items[i] == null) {
				si.items[i] = -1;
				si.amount[i] = 0;
			} else {
				si.items[i] = inv.items[i].ID;
				si.amount[i] = inv.amount[i];
				si.name[i] = inv.items[i].name;
				si.meta[i] = inv.items[i].meta;
				si.rarity[i] = inv.items[i].rarity;
			}
		}
		con.sendTCP(si);
		// Send Inventory to all others
		ItemEquip ei = new ItemEquip();
		ei.ID = this.ID;
		ei.items = new int[6];
		ei.amount = new int[6];
		ei.name = new String[6];
		ei.meta = new String[6];
		ei.rarity = new int[6];
		for (int i = 0; i < 6; i++) {
			if (inv.items[i + 8] == null) {
				ei.items[i] = -1;
				ei.amount[i] = 0;
			} else {
				ei.items[i] = inv.items[i + 8].ID;
				ei.amount[i] = inv.amount[i + 8];
				ei.name[i] = inv.items[i + 8].name;
				ei.meta[i] = inv.items[i + 8].meta;
				ei.rarity[i] = inv.items[i + 8].rarity;
			}
		}
		world.gs.server.sendToAllExceptTCP(con.getID(), ei);
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
			} else if (obj instanceof ItemSwitch) {
				ItemSwitch is = (ItemSwitch) obj;
				boolean sw = false;
				if (inv.items[is.mark] != null
						&& inv.items[is.switcher] != null) {
					if (inv.compare(inv.items[is.mark], inv.items[is.switcher])) {
						inv.items[is.mark] = null;
						inv.amount[is.switcher] += inv.amount[is.mark];
						inv.amount[is.mark] = 0;
						inv.items[is.mark] = null;
						sendInv();
						return;
					}
				}
				if (is.mark < 8 && is.switcher < 8) {
					sw = true;
				} else if (is.mark >= 8 && is.switcher >= 8) {
					sw = false;
				} else {
					if (is.mark >= 8) {
						int t = is.mark;
						is.mark = is.switcher;
						is.switcher = t;
					}
					switch (is.switcher) {
					case 8: // Helm
						if (inv.items[is.mark] instanceof TypeHelmet
								|| inv.items[is.mark] == null) {
							sw = true;
						}
						break;
					case 9: // Schwert
						if (inv.items[is.mark] instanceof TypeWeapon
								|| inv.items[is.mark] == null) {
							sw = true;
						}
						break;
					case 10: // Brust
						if (inv.items[is.mark] instanceof TypeBody
								|| inv.items[is.mark] == null) {
							sw = true;
						}

						break;
					case 11: // Schild
						if (inv.items[is.mark] instanceof TypeShield
								|| inv.items[is.mark] == null) {
							sw = true;
						}
						break;
					case 12: // Fuß
						if (inv.items[is.mark] instanceof TypeShoe
								|| inv.items[is.mark] == null) {
							sw = true;
						}
						break;
					case 13: // Sauerstoff
						if (inv.items[is.mark] instanceof TypeBottle
								|| inv.items[is.mark] == null) {
							sw = true;
						}
						break;
					}
				}
				if (sw) {
					Item temp = inv.items[is.mark];
					int tempa = inv.amount[is.mark];
					inv.items[is.mark] = inv.items[is.switcher];
					inv.amount[is.mark] = inv.amount[is.switcher];
					inv.items[is.switcher] = temp;
					inv.amount[is.switcher] = tempa;
					sendInv();
				}
			} else if (obj instanceof ItemDrop) {
				ItemDrop drop = (ItemDrop) obj;
				if (inv.items[drop.mark] != null) {
					if (inv.amount[drop.mark] >= 2) {
						inv.amount[drop.mark]--;
						EntityItem ei = new EntityItem(world, world.getID());
						ei.ItemID = inv.items[drop.mark].ID;
						ei.x = x;
						ei.y = y;
						ei.dy = .2f;
						ei.dx = .2f;
						if (lookLeft)
							ei.dx = -.2f;
						world.entities.put(ei.ID, ei);
						EntityItemAdd eia = new EntityItemAdd();
						eia.ID = ei.ID;
						eia.ItemID = ei.ItemID;
						eia.x = ei.x;
						eia.y = ei.y;
						world.gs.server.sendToAllTCP(eia);
					} else {
						if (inv.amount[drop.mark] == 1) {
							EntityItem ei = new EntityItem(world, world.getID());
							ei.ItemID = inv.items[drop.mark].ID;
							ei.x = x;
							ei.y = y;
							ei.dy = .2f;
							ei.dx = .2f;
							if (lookLeft)
								ei.dx = -.2f;
							world.entities.put(ei.ID, ei);
							EntityItemAdd eia = new EntityItemAdd();
							eia.ID = ei.ID;
							eia.ItemID = ei.ItemID;
							eia.x = ei.x;
							eia.y = ei.y;
							world.gs.server.sendToAllTCP(eia);
						}
						inv.amount[drop.mark] = 0;
						inv.items[drop.mark] = null;
					}
					sendInv();
				}
			} else if (obj instanceof PlayerClick) {
				PlayerClick pc = (PlayerClick) obj;
				if (Math.sqrt((pc.x - x) * (pc.x - x) + (pc.y - y) * (pc.y - y)) <= 100) {
					if (inv.items[9] != null) {
						if (inv.items[9] instanceof TypePickaxe) {
							// Remove Block
							float absX = (pc.x - Misc.mod(pc.x, 16)) / 16;
							float absY = (pc.y - Misc.mod(pc.y, 16)) / 16;
							float cX = (absX - Misc.mod(absX, 16)) / 16;
							float cY = (absY - Misc.mod(absY, 16)) / 16;
							for (Chunk chu : chunks) {
								if (chu.x == (int) cX && chu.y == (int) cY) {
									if (chu.getBlock((int) Misc.mod(absX, 16),
											(int) Misc.mod(absY, 16)) != 0) {
										int block = chu.getBlock(
												(int) Misc.mod(absX, 16),
												(int) Misc.mod(absY, 16));
										Block.drop(
												block,
												(pc.x - Misc.mod(pc.x, 16)) + 8,
												(pc.y - Misc.mod(pc.y, 16)) + 8,
												world);
										chu.setBlock((int) Misc.mod(absX, 16),
												(int) Misc.mod(absY, 16), 0);
										ChunkChange cc = new ChunkChange();
										cc.x = (int) Misc.mod(absX, 16);
										cc.y = (int) Misc.mod(absY, 16);
										cc.cx = (int) cX;
										cc.cy = (int) cY;
										cc.id = 0;
										world.gs.server.sendToAllTCP(cc);
									}
								}
							}
						} else if (inv.items[9] instanceof TypeBlock) {
							float absX = (pc.x - Misc.mod(pc.x, 16)) / 16;
							float absY = (pc.y - Misc.mod(pc.y, 16)) / 16;
							float cX = (absX - Misc.mod(absX, 16)) / 16;
							float cY = (absY - Misc.mod(absY, 16)) / 16;
							for (Chunk chu : chunks) {
								if (chu.x == (int) cX && chu.y == (int) cY) {
									if (chu.getBlock((int) Misc.mod(absX, 16),
											(int) Misc.mod(absY, 16)) == 0) {
										Box box = new Box(
												(pc.x - Misc.mod(pc.x, 16)) + 8,
												(pc.y - Misc.mod(pc.y, 16)) + 8,
												8, 8);
										boolean valid = true;
										for (Entity entity : world.entities
												.values()) {
											if (box.check(entity.box)) {
												valid = false;
											}
										}
										if (valid) {
											TypeBlock tb = (TypeBlock) inv.items[9];
											inv.amount[9]--;
											if (inv.amount[9] == 0) {
												inv.items[9] = null;
											}
											sendInv();
											chu.setBlock(
													(int) Misc.mod(absX, 16),
													(int) Misc.mod(absY, 16),
													tb.BlockID);
											ChunkChange cc = new ChunkChange();
											cc.x = (int) Misc.mod(absX, 16);
											cc.y = (int) Misc.mod(absY, 16);
											cc.cx = (int) cX;
											cc.cy = (int) cY;
											cc.id = tb.BlockID;
											world.gs.server.sendToAllTCP(cc);
										}
									}
								}
							}
						}
					}
				}
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
				TagInt rare = (TagInt) item.getTag("Rarity");
				if (inv.items[i] != null) {
					id.setValue(inv.items[i].ID);
					am.setValue(inv.amount[i]);
					name.setValue(inv.items[i].name);
					meta.setValue(inv.items[i].meta);
					rare.setValue(inv.items[i].rarity);
				} else {
					id.setValue(-1);
					am.setValue(0);
					name.setValue("");
					meta.setValue("");
					rare.setValue(0);
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
