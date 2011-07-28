package net.violet.platform.api.maps;

import net.violet.platform.dataobjects.AbstractLangData;

public class LangInformationMap extends AbstractAPIMap {

	public LangInformationMap(AbstractLangData inLang) {
		super(2);

		put("title", inLang.getLang_title());
		put("isocode", inLang.getLang_iso_code());
	}
}
