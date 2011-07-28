package net.violet.platform.xmpp.packet;

public final class JID {

	private final String mUserName;
	private final String mDomain;
	private final String mResource;

	/**
	 * Constructeur à partir d'une string.
	 */
	public JID(String inAddr) {
		final String[] theAddr1 = inAddr.split("@");
		this.mUserName = theAddr1[0];
		final String[] theAddr2 = theAddr1[1].split("/");
		this.mDomain = theAddr2[0];
		if (theAddr2.length == 2) {
			this.mResource = theAddr2[1];
		} else {
			this.mResource = null;
		}
	}

	public String getUserName() {
		return this.mUserName;
	}

	public String getDomain() {
		return this.mDomain;
	}

	public String getResource() {
		return this.mResource;
	}

	@Override
	public String toString() {
		final String theResult;
		if (this.mResource != null) {
			theResult = this.mUserName + "@" + this.mDomain + "/" + this.mResource;
		} else {
			theResult = this.mUserName + "@" + this.mDomain;
		}
		return theResult;
	}
}
