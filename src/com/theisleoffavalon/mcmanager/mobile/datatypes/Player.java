/*
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE Version 2, December 2004
 * 
 * Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 * 
 * Everyone is permitted to copy and distribute verbatim or modified copies of
 * this license document, and changing it is allowed as long as the name is
 * changed.
 * 
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING,
 * DISTRIBUTION AND MODIFICATION
 * 
 * 0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package com.theisleoffavalon.mcmanager.mobile.datatypes;

/**
 * Class that represents a Player.
 * 
 * @author eberta
 * @modified 2/14/13
 */
public class Player {

	/**
	 * Name of the Player
	 */
	private String	name;

	/**
	 * IP Address of a player
	 */
	private String	ipAddress;

	/**
	 * Byte Array that contains the picture of the player.
	 */
	private Byte[]	picture;

	public Player(String name, String ipAddress, Byte[] picture) {
		this.name = name;
		this.ipAddress = ipAddress;
		this.picture = picture;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Byte[] getPicture() {
		return this.picture;
	}

	public void setPicture(Byte[] picture) {
		this.picture = picture;
	}

}
