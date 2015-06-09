package com.game.server;

import com.game.item.Item;
import com.game.packet.EntityMove;
import com.game.packet.EntityRem;
import com.game.util.Box;

public class EntityItem extends Entity {
	public int ItemID;
	public Item item;
	public float time;

	public EntityItem(World world, int ID) {
		super(world, ID);
		box = new Box(x, y, 7, 7);
		time = 0;
	}

	@Override
	public void update(float delta) {
		time += delta;
		if (time >= 300000) {
			// Remove me after 5 minutes
			world.entities.remove(ID);
			EntityRem er = new EntityRem();
			er.ID = ID;
			world.gs.server.sendToAllTCP(er);
		}
		updateBox();
		if (world.chunked(box)) {
			// Collision Detection
			x += dx * delta;
			if (dx > 0) {
				dx -= 0.0002f * delta;
				if (dx < 0)
					dx = 0;
			} else if (dx < 0) {
				dx += 0.0002f * delta;
				if (dx > 0)
					dx = 0;
			}
			updateBox();
			Box check = world.check(box);
			if (check != null)
				while (true) {
					if (dx > 0) {
						x = check.getX() - check.getDx() - box.getDx() - 0.001f;
					} else if (dx < 0) {
						x = check.getX() + check.getDx() + box.getDx() + 0.001f;
					}
					updateBox();
					check = world.check(box);
					if (check == null || dx == 0) {
						dx = 0;
						break;
					}
				}
			dy -= 0.001f * delta;
			y += dy * delta;
			updateBox();
			check = world.check(box);
			if (check != null)
				while (true) {
					if (dy > 0) {
						y = check.getY() - check.getDy() - box.getDy() - 0.001f;
					} else if (dy < 0) {
						y = check.getY() + check.getDy() + box.getDy() + 0.001f;
					}
					updateBox();
					check = world.check(box);
					if (check == null || dy == 0) {
						dy = 0;
						break;
					}

				}
			updateBox();
		}
		// Check pickup :D
		if (time >= 1000)
			for (Entity entity : world.entities.values()) {
				if (entity instanceof EntityPlayer) {
					EntityPlayer ep = (EntityPlayer) entity;
					if (box.check(ep.box)) {
						// Added :D
						if (item != null) {
							if (ep.inv.addItem(item, 1)) {
								ep.sendInv();
								// Remove from List
								world.entities.remove(ID);
								// Remove me
								EntityRem er = new EntityRem();
								er.ID = ID;
								world.gs.server.sendToAllTCP(er);
								return;
							}
						} else {
							if (ep.inv.addItem(ItemID, 1)) {
								ep.sendInv();
								// Remove from List
								world.entities.remove(ID);
								// Remove me
								EntityRem er = new EntityRem();
								er.ID = ID;
								world.gs.server.sendToAllTCP(er);
								return;
							}
						}
					}
				}
			}
	}

	public void updateBox() {
		box.setX(x);
		box.setY(y);
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
		world.gs.server.sendToAllUDP(em);
	}
}
