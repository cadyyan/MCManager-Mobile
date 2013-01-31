package com.theisleoffavalon.mcmanager_mobile.datatypes;

public class Mod {

	private String	name;

	private String	versionNumber;

	private String	gameVersion;

	private boolean	updateAvailable;

	private Mod(String name) {
		this(name, null, null, false);
	}

	public Mod(String name, String versionNumber, String gameVersion,
			boolean updateAvailable) {
		this.name = name;
		this.versionNumber = versionNumber;
		this.gameVersion = gameVersion;
		this.updateAvailable = updateAvailable;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersionNumber() {
		return this.versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getGameVersion() {
		return this.gameVersion;
	}

	public void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}

	public boolean isUpdateAvailable() {
		return this.updateAvailable;
	}

	public void setUpdateAvailable(boolean updateAvailable) {
		this.updateAvailable = updateAvailable;
	}

}
