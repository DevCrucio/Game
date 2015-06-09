package com.game.util;

import java.sql.Timestamp;
import java.util.Date;

import com.esotericsoftware.kryo.Kryo;
import com.game.packet.Accept;
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
import com.game.packet.Login;
import com.game.packet.PlayerAdd;
import com.game.packet.PlayerClick;

public class Misc {
	/*
	 * For Logging
	 */

	public static void log(String msg) {
		Date date = new Date();
		System.out.println("[" + new Timestamp(date.getTime()) + "] " + msg
				+ " ");
	}

	/*
	 * Method that registers on Client and Server the same way
	 */
	public static void reg(Kryo kryo) {
		// Array
		kryo.register(int[].class);
		kryo.register(String[].class);
		// Own Classes
		kryo.register(Accept.class);
		kryo.register(ChunkAdd.class);
		kryo.register(ChunkChange.class);
		kryo.register(ChunkRem.class);
		kryo.register(Color.class);
		kryo.register(EntityItemAdd.class);
		kryo.register(EntityMove.class);
		kryo.register(EntityRem.class);
		kryo.register(ItemDrop.class);
		kryo.register(ItemEquip.class);
		kryo.register(ItemSend.class);
		kryo.register(ItemSwitch.class);
		kryo.register(Login.class);
		kryo.register(PlayerAdd.class);
		kryo.register(PlayerClick.class);
	}

	public static float mod(float value, float div) {
		float out = value % div;
		if (out < 0)
			out += div;
		return out;
	}
}
