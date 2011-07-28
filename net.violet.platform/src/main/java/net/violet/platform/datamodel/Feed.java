package net.violet.platform.datamodel;

import java.sql.Timestamp;

import net.violet.db.records.Record;

public interface Feed extends Record<Feed> {

	enum Type {
		RSS("RSS"), PODCAST("PODCAST"), GROUP("GROUP"), GMAIL_TWITTER("GMAIL:TWITTER"), FACEBOOK("FACEBOOK");

		private final String label;

		private Type(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return this.label;
		}

		static Type findByLabel(String inLabel) {
			for (final Type aType : Type.values()) {
				if (aType.label.equals(inLabel)) {
					return aType;
				}
			}

			return null;
		}
	}

	enum AccessRight {
		FREE("FREE"), FULL("FULL");

		private final String label;

		private AccessRight(String label) {
			this.label = label;
		}

		@Override
		public String toString() {
			return this.label;
		}

		static AccessRight findByLabel(String inLabel) {
			for (final AccessRight aRight : AccessRight.values()) {
				if (aRight.label.equals(inLabel)) {
					return aRight;
				}
			}

			return null;
		}
	}

	String getUrl();

	Lang getLanguage();

	String getETag();

	Timestamp getLastModified();

	Type getType();

	AccessRight getAccessRight();

	void updateInformation(String etag, Timestamp lastModified);

	// only used by VAdmin
	void updateLang(Lang lang);

}
