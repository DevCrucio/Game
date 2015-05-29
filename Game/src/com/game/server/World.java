package com.game.server;

import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryonet.Connection;
import com.game.file.Tag;
import com.game.file.TagBoolean;
import com.game.file.TagFloat;
import com.game.file.TagSubtag;
import com.game.packet.Accept;
import com.game.packet.PlayerAdd;

public class World {
	/*
	 * Constructor
	 */
	public GameServer gs;

	public World(GameServer gs) {
		this.gs = gs;
	}

	/*
	 * Generate ID
	 */
	private int next = 0;

	public int getID() {
		return next++;
	}

	/*
	 * Login
	 */
	public void login(String name, Connection con, Tag tag) {
		// TODO load Coordinates from File
		TagSubtag user = (TagSubtag) tag;
		float vx, vy;
		boolean vlookleft;
		if (user.hasTag("Location")) {
			TagSubtag loc = (TagSubtag) user.getTag("Location");
			TagFloat x = (TagFloat) loc.getTag("X");
			vx = x.getValue();
			TagFloat y = (TagFloat) loc.getTag("Y");
			vy = y.getValue();
			TagBoolean lookLeft = (TagBoolean) loc.getTag("LookLeft");
			vlookleft = lookLeft.getValue();
		} else {
			TagSubtag loc = new TagSubtag("Location");
			TagFloat x = new TagFloat("X");
			x.setValue(0.0f);
			TagFloat y = new TagFloat("Y");
			y.setValue(0.0f);
			TagBoolean lookLeft = new TagBoolean("LookLeft");
			lookLeft.setValue(true);
			loc.addTag(x);
			loc.addTag(y);
			loc.addTag(lookLeft);
			user.addTag(loc);
			vx = 0;
			vy = 0;
			vlookleft = true;
		}
		// Launch the Gameworld
		Accept accept = new Accept();
		accept.name = name;
		accept.ID = getID();
		accept.x = vx;
		accept.y = vy;
		accept.lookLeft = vlookleft;
		con.sendTCP(accept);
		// Send other Entities to him
		for (Entity entity : entities.values()) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer ep = (EntityPlayer) entity;
				PlayerAdd ap = new PlayerAdd();
				ap.ID = ep.ID;
				ap.name = ep.name;
				ap.x = ep.x;
				ap.y = ep.y;
				ap.lookLeft = ep.lookLeft;
				con.sendTCP(ap);
			}
		}
		// Add him to the HashMap
		EntityPlayer ep = new EntityPlayer(this, accept.ID, con);
		ep.name = name;
		ep.x = vx;
		ep.y = vy;
		ep.lookLeft = vlookleft;
		entities.put(accept.ID, ep);
		// Send him to other Players
		PlayerAdd ap = new PlayerAdd();
		ap.ID = ep.ID;
		ap.name = ep.name;
		ap.x = ep.x;
		ap.y = ep.y;
		ap.lookLeft = ep.lookLeft;
		gs.server.sendToAllExceptTCP(con.getID(), ap);
	}

	/*
	 * Entities
	 */
	public ConcurrentHashMap<Integer, Entity> entities = new ConcurrentHashMap<Integer, Entity>();

	/*
	 * Update
	 */
	private float send;

	public void update(float delta) {
		// Update
		for (Entity entity : entities.values()) {
			entity.update(delta);
		}
		send += delta;
		if (send >= 50) {
			send -= 50;
			// Send
			for (Entity entity : entities.values()) {
				entity.send();
			}
		}
	}
}
