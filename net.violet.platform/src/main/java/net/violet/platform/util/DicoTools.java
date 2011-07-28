package net.violet.platform.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import net.violet.platform.datamodel.Dico;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SiteLangData;

import org.apache.log4j.Logger;

/**
 * @author mleblanc
 */
public final class DicoTools {

	private static final Logger LOGGER = Logger.getLogger(DicoTools.class);

	public static final String LOC_PREFIX = "LOC_"; // prefixe des chaines de localidation (entrées dans le dico)
	private static final Map<String, String> DICO_CACHE = new HashMap<String, String>();

	public static String dico_if(final Lang lang, final String key) {
		return 0 == key.indexOf(DicoTools.LOC_PREFIX) ? DicoTools.dico(lang, key.substring(4)) : key;
	}

	public static String dico_if(final long langId, final String key) {
		return DicoTools.dico_if(Factories.LANG.find(langId), key);
	}

	public static String dico(final String langId, final String key) {
		return DicoTools.dico(Factories.LANG.find(Long.parseLong(langId)), key);
	}

	/**
	 * return the String value of the key in Specific lang.
	 * 
	 * @param inSiteLang
	 * @param key
	 * @return
	 */
	public static String dico(final SiteLangData inSiteLang, final String key) {
		return ((inSiteLang != null) && inSiteLang.isValid()) ? DicoTools.dico(inSiteLang.getReference(), key) : DicoTools.dico((Lang) null, key);
	}

	public static String dico(final Lang langue, final String key) {
		if ((null == key) || (null == langue)) {
			throw new NullPointerException("usage: DicoTool.getInstance(String) do not accept argument null for String or 0 for int");
		}

		return DicoTools.dico_jsp(langue, key);
	}

	// permet de remplacer des key [VAR0],[VAR1],etc... par des values
	public static String dico_jsp(Lang langue, String key, Object... value) {
		String mydico = net.violet.common.StringShop.EMPTY_STRING;

		try {
			final String hash_key = langue + net.violet.common.StringShop.SLASH + key;
			mydico = DicoTools.DICO_CACHE.get(hash_key);

			if (mydico == null) {
				synchronized (DicoTools.DICO_CACHE) {
					mydico = DicoTools.DICO_CACHE.get(hash_key);

					if (mydico == null) {
						final Dico theDico = Factories.DICO.findByKeyAndLang(key, langue);

						if (theDico != null) {
							mydico = theDico.getDico_text();
							DicoTools.DICO_CACHE.put(hash_key, mydico);
						} else {
							mydico = "NOLOC [" + key + "]";
							//Trop de clé pourrite sur my.nabaztag.com
							DicoTools.LOGGER.info("ERREUR lang = " + langue + " key = " + key);
						}
					}
				}
			}
		} catch (final Throwable t) {
			mydico = "NOLOCDB !";
			DicoTools.LOGGER.fatal("ERREUR lang = " + langue + " key = " + key);
		};

		if (value != null) {
			for (int i = 0; i < value.length; i++) {
				final Object theValue = value[i];
				mydico = mydico.replaceAll("\\[VAR" + i + "\\]", theValue != null ? String.valueOf(theValue) : net.violet.common.StringShop.EMPTY_STRING);
			}
		}
		return mydico;
	}

	// permet de mettre une succession de page/key dans une string et de
	// l'interpreter via une methode
	public static String insertBR(Lang langue, String texte, String decoupe) {
		String text = net.violet.common.StringShop.EMPTY_STRING;
		final StringTokenizer st = new StringTokenizer(texte, decoupe);
		while (st.hasMoreTokens()) {
			text += DicoTools.dico_jsp(langue, st.nextToken().trim());
			text += "<br />";
		}
		return text;
	}

}
