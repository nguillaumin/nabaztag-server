package net.violet.platform.util.concurrent.units;

import net.violet.platform.datamodel.Lang;

/**
 * The thread safety of the mState member relies on the use of
 * {@link TTSProcessUnit} - it is not Thread safe on its own
 */
public class TTSProcessUnit extends AbstractCrawlerProcessUnit<Lang> {

	public TTSProcessUnit(String inIdXML, String inTitle, String link, Lang inLang, Integer inTTL) {
		super(inTitle, inLang, inIdXML, inTitle, link, inTTL);
	}

}
