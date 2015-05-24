package com.game.server;

import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryonet.Connection;
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
	public void login(String name, Connection con) {
		// Launch the Gameworld
		Accept accept = new Accept();
		accept.name = name;
		accept.ID = getID();
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
				con.sendTCP(ap);
			}
		}
		// Add him to the HashMap
		EntityPlayer ep = new EntityPlayer(this, accept.ID, con);
		ep.name = name;
		entities.put(accept.ID, ep);
		// Send him to other Players
		PlayerAdd ap = new PlayerAdd();
		ap.ID = ep.ID;
		ap.name = ep.name;
		ap.x = ep.x;
		ap.y = ep.y;
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
