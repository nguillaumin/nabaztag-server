package net.violet.platform.voice.server;

import java.util.List;

public class TTSEngineConfig {

	private String mName;
	private int mMaxChannels;
	private String mCommand;
	private String mHost;
	private String[] mEnvp;

	public void setMaxChannels(int inMaxChannels) {
		this.mMaxChannels = inMaxChannels;
	}

	public int getMaxChannels() {
		return this.mMaxChannels;
	}

	public String getName() {
		return this.mName;
	}

	public void setName(String inName) {
		this.mName = inName;
	}

	public String getCommand() {
		return this.mCommand;
	}

	public void setCommand(String inCommand) {
		this.mCommand = inCommand;
	}

	public String getHost() {
		return this.mHost;
	}

	public void setHost(String inHost) {
		this.mHost = inHost;
	}

	public String[] getEnvp() {
		return this.mEnvp;
	}

	public void setEnvironment(List<String> inEnvp) {
		this.mEnvp = inEnvp.toArray(new String[0]);
	}
}
