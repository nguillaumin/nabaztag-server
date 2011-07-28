package net.violet.platform.voice.server;

public class TTSVoice {

	private String language;
	private String name;
	private String command;
	private String gender;
	private int pitch;
	private int speed;
	private String mEngine;
	private boolean mDefault;

	public int getPitch() {
		return this.pitch;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean getDefault() {
		return this.mDefault;
	}

	public void setDefault(boolean inDefault) {
		this.mDefault = inDefault;
	}

	public String getCommand() {
		return this.command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEngine() {
		return this.mEngine;
	}

	public void setEngine(String inEngine) {
		this.mEngine = inEngine;
	}
}
