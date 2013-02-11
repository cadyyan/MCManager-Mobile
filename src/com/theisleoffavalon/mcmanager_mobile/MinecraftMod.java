/**
 * 
 */
package com.theisleoffavalon.mcmanager_mobile;

/**
 * This class represents data about a mod on a server
 * 
 * @author henkelj
 */
public class MinecraftMod {

	/**
	 * Name of the mod
	 */
	private String	name;

	/**
	 * Version of the mod
	 */
	private String	version;

	public MinecraftMod(String name, String version) {
		this.setName(name);
		this.setVersion(version);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}
