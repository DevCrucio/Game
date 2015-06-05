package com.game.server;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.game.file.Tag;
import com.game.file.TagBoolean;
import com.game.file.TagFloat;
import com.game.file.TagSubtag;
import com.game.packet.Accept;
import com.game.packet.Color;
import com.game.packet.PlayerAdd;

public class World {
	/*
	 * Constructor
	 */
	public GameServer gs;
	public static MapGen generator;

	public World(GameServer gs) {
		this.gs = gs;
		generator = new MapGen(1);
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
		// load Coordinates from File
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
			y.setValue(200.0f);
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
		// Load Color from file
		Color cHair = new Color(), cBody = new Color(), cArm = new Color(), cShoe = new Color();
		if (user.hasTag("Color")) {
			TagSubtag color = (TagSubtag) user.getTag("Color");
			// Hair
			TagSubtag hair = (TagSubtag) color.getTag("Hair");
			cHair.set(((TagFloat) hair.getTag("Red")).getValue(),
					((TagFloat) hair.getTag("Green")).getValue(),
					((TagFloat) hair.getTag("Blue")).getValue());
			// Body
			TagSubtag body = (TagSubtag) color.getTag("Body");
			cBody.set(((TagFloat) body.getTag("Red")).getValue(),
					((TagFloat) body.getTag("Green")).getValue(),
					((TagFloat) body.getTag("Blue")).getValue());
			// Arm
			TagSubtag arm = (TagSubtag) color.getTag("Arm");
			cArm.set(((TagFloat) arm.getTag("Red")).getValue(),
					((TagFloat) arm.getTag("Green")).getValue(),
					((TagFloat) arm.getTag("Blue")).getValue());
			// Shoe
			TagSubtag shoe = (TagSubtag) color.getTag("Shoe");
			cShoe.set(((TagFloat) shoe.getTag("Red")).getValue(),
					((TagFloat) shoe.getTag("Green")).getValue(),
					((TagFloat) shoe.getTag("Blue")).getValue());
		} else {
			TagSubtag color = new TagSubtag("Color");
			// Hair
			TagSubtag hair = new TagSubtag("Hair");
			TagFloat red = new TagFloat("Red");
			red.setValue((float) Math.random());
			TagFloat green = new TagFloat("Green");
			red.setValue((float) Math.random());
			TagFloat blue = new TagFloat("Blue");
			red.setValue((float) Math.random());
			hair.addTag(red);
			hair.addTag(green);
			hair.addTag(blue);
			cHair.set(red.getValue(), green.getValue(), blue.getValue());
			// Body
			TagSubtag body = new TagSubtag("Body");
			red = new TagFloat("Red");
			red.setValue((float) Math.random());
			green = new TagFloat("Green");
			green.setValue((float) Math.random());
			blue = new TagFloat("Blue");
			blue.setValue((float) Math.random());
			body.addTag(red);
			body.addTag(green);
			body.addTag(blue);
			cBody.set(red.getValue(), green.getValue(), blue.getValue());
			// Arm
			TagSubtag arm = new TagSubtag("Arm");
			red = new TagFloat("Red");
			red.setValue((float) Math.random());
			green = new TagFloat("Green");
			green.setValue((float) Math.random());
			blue = new TagFloat("Blue");
			blue.setValue((float) Math.random());
			arm.addTag(red);
			arm.addTag(green);
			arm.addTag(blue);
			cArm.set(red.getValue(), green.getValue(), blue.getValue());
			// Shoe
			TagSubtag shoe = new TagSubtag("Shoe");
			red = new TagFloat("Red");
			red.setValue((float) Math.random());
			green = new TagFloat("Green");
			green.setValue((float) Math.random());
			blue = new TagFloat("Blue");
			blue.setValue((float) Math.random());
			shoe.addTag(red);
			shoe.addTag(green);
			shoe.addTag(blue);
			cShoe.set(red.getValue(), green.getValue(), blue.getValue());
			// Append
			color.addTag(hair);
			color.addTag(body);
			color.addTag(arm);
			color.addTag(shoe);
			user.addTag(color);
		}
		// Launch the Gameworld
		Accept accept = new Accept();
		accept.name = name;
		accept.ID = getID();
		accept.x = vx;
		accept.y = vy;
		accept.lookLeft = vlookleft;
		accept.arm = cArm;
		accept.body = cBody;
		accept.hair = cHair;
		accept.shoe = cShoe;
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
				ap.arm = ep.arm;
				ap.body = ep.body;
				ap.hair = ep.hair;
				ap.shoe = ep.shoe;
				con.sendTCP(ap);
			}
		}
		// Add him to the HashMap
		EntityPlayer ep = new EntityPlayer(this, accept.ID, con, tag);
		ep.name = name;
		ep.x = vx;
		ep.y = vy;
		ep.lookLeft = vlookleft;
		ep.arm = cArm;
		ep.hair = cHair;
		ep.body = cBody;
		ep.shoe = cShoe;
		entities.put(accept.ID, ep);
		// Send him to other Players
		PlayerAdd ap = new PlayerAdd();
		ap.ID = ep.ID;
		ap.name = ep.name;
		ap.x = ep.x;
		ap.y = ep.y;
		ap.lookLeft = ep.lookLeft;
		ap.arm = ep.arm;
		ap.body = ep.body;
		ap.hair = ep.hair;
		ap.shoe = ep.shoe;
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
		if (send >= 67) {
			send -= 67;
			// Send
			for (Entity entity : entities.values()) {
				entity.send();
			}
		}
		// Chunk unload
		for (Chunk chunk : chunks) {
			boolean active = false;
			for (Entity entity : entities.values()) {
				if (entity instanceof EntityPlayer) {
					EntityPlayer ep = (EntityPlayer) entity;
					if (ep.hasChunk(chunk.x, chunk.y)) {
						active = true;
					}
				}
			}
			if (!active) {
				int rx = (int) Math.floor(chunk.x / 32f);
				int ry = (int) Math.floor(chunk.y / 32f);
				for (Region reg : regions) {
					if (reg.x == rx && reg.y == ry) {
						reg.setChunk(chunk);
						chunks.remove(chunk);
					}
				}
			}
		}
		// Region unload
		for (Region reg : regions) {
			reg.isActive(this);
		}
	}

	/*
	 * Region
	 * Chunk
	 */
	List<Region> regions = new CopyOnWriteArrayList<Region>();
	List<Chunk> chunks = new CopyOnWriteArrayList<Chunk>();

	// Chunk System
	public Chunk getChunk(int x, int y) {
		int rx = (int) Math.floor(x / 32f);
		int ry = (int) Math.floor(y / 32f);
		Region region = null;
		Chunk chunk = null;
		// Region active
		boolean active = false;
		for (Region reg : regions) {
			if (reg.x == rx && reg.y == ry) {
				active = true;
				region = reg;
				break;
			}
		}
		if (!active) {
			// Load Region
			regions.add(region = Region.load(rx, ry));
		}
		// Chunk active
		active = false;
		for (Chunk chu : chunks) {
			if (chu.x == x && chu.y == y) {
				active = true;
				chunk = chu;
				break;
			}
		}
		if (!active) {
			// Load Chunk
			chunks.add(chunk = region.getChunk(x, y));
		}
		// Load Chunk
		return chunk;
	}
}
