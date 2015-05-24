package com.game.server;

import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryonet.Connection;
import com.game.packet.Accept;

public class World {
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
		Accept accept = new Accept();
		accept.name = name;
		accept.ID = getID();
		con.sendTCP(accept);
	}

	/*
	 * Entities
	 */
	public ConcurrentHashMap<Integer, Entity> entities = new ConcurrentHashMap<Integer, Entity>();

	/*
	 * Update
	 */
	public void update(float delta) {

	}
}
