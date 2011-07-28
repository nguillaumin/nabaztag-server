/**
 *
 */
package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Dico;
import net.violet.platform.datamodel.Lang;

/**
 * @author slorg1
 */

public class DicoMock extends AbstractMockRecord<Dico, DicoMock> implements Dico {

	private final long creation;
	private String key;
	private String page;
	private String text;
	private Lang mLang;

	public DicoMock(long inId, String inDicoKey, Lang inLang, String inDicoText, String inDicoPage) {
		super(inId);
		this.key = inDicoKey;
		this.page = inDicoPage;
		this.text = inDicoText;
		this.mLang = inLang;
		this.creation = System.currentTimeMillis() / 1000;
	}

	public long getDico_creation() {
		return this.creation;
	}

	public String getDico_key() {
		return this.key;
	}

	public String getDico_page() {
		return this.page;
	}

	public String getDico_text() {
		return this.text;
	}

	public Lang getDicoLang() {
		return this.mLang;
	}

	public void setText(String translation) {
		this.text = translation;
	}

	public void update(String inKey, Lang inLang, String inText, String inPage) {
		this.text = inText;
		this.key = inKey;
		this.page = inPage;
		this.mLang = inLang;
	}

}
