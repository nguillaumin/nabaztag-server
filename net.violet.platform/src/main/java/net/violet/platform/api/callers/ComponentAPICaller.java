package net.violet.platform.api.callers;

public class ComponentAPICaller extends AbstractComponentAPICaller {

	private final String publicKey;

	public ComponentAPICaller(String inPublicKey) {
		this.publicKey = inPublicKey;
	}

	public String getAPIKey() {
		return this.publicKey;
	}

	@Override
	public String toString() {
		return "COMPONENT API CALLER [" + this.publicKey + "]";
	}

}
