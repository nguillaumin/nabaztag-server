/**
 * 
 */
package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TtsVoice;

/**
 * @author slorg1
 */
public class TtsVoiceMock extends AbstractMockRecord<TtsVoice, TtsVoiceMock> implements TtsVoice {

	private final String command;
	private final String libelle;
	private final String str;
	private final boolean isDefault;
	private final boolean isDisabled;
	private final Lang mlang;

	public TtsVoiceMock(long inId, String libelle, String command, Lang lang, String str, boolean isDefault, boolean isDisabled) {
		super(inId);
		this.command = command;
		this.libelle = libelle;
		this.mlang = lang;
		this.str = str;
		this.isDefault = isDefault;
		this.isDisabled = isDisabled;
	}

	public String getTtsvoice_command() {
		return this.command;
	}

	public long getTtsvoice_id() {
		return getId();
	}

	public long getTtsvoice_lang() {
		return this.mlang.getId();
	}

	public String getTtsvoice_libelle() {
		return this.libelle;
	}

	public String getTtsvoice_str() {
		return this.str;
	}

	public boolean isDefault() {
		return this.isDefault;
	}

	public boolean isDisabled() {
		return this.isDisabled;
	}

	public Lang getLang() {
		return this.mlang;
	}

}
