package net.violet.platform.datamodel.factories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Lang;

import org.apache.log4j.Logger;

public interface LangFactory extends RecordFactory<Lang> {

	/**
	 * Accesseur à partir du code.
	 * 
	 * @param inAbbrev code de la langue.
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	Lang findByIsoCode(String inIsoCode);

	/**
	 * Accesseur sur toutes les langues maternelles
	 */
	@Deprecated
	List<Lang> findAllMotherTongue();

	Collection<Lang> getCollectionLangFromIdsArray(String[] ids);

	/**
	 * Accesseur sur la liste des langues, triées par id.
	 */
	List<Lang> findAll();

	/**
	 * Accesseur sur toutes les langues possibles pour les nabcasts
	 */
	List<Lang> findAllLanguagesForNabcasts();

	/**
	 * Accesseur sur toutes les langues principales du site (affichage,
	 * application)
	 */
	List<Lang> getAllSiteLanguages();

	/**
	 * Accesseur sur toutes les langues TTS proposés
	 */
	List<Lang> getAllTTSLanguages();

	/**
	 * Accesseur sur toutes les langues ASR proposés
	 */
	List<Lang> getAllASRLanguages();

	/**
	 * Accesseur sur toutes les langues proposés pour la voix de l'objet
	 */
	List<Lang> getAllObjectLanguages();

	/**
	 * Récupère la langue parente selon l'isocode passé ex : fr-FR => fr ex : fr
	 * => fr
	 * 
	 * @param inIsocodeLanguage
	 * @return null si parent non déterminé sinon Lang
	 */
	Lang getParentByIsocode(String inIsocodeLanguage);

	class LangFactoryCommon {

		static final Logger LOGGER = Logger.getLogger(LangFactoryCommon.class);

		/** liste les langues triées par code iso */
		private final Map<String, Lang> mLangByIsoCodeCache;

		/** Liste de toutes les langues visible triées par id croissant */
		private final List<Lang> mAllLangs;

		/** Liste de toutes les langues triées par id croissant */
		private final List<Lang> mAllMotherTongueLangs;

		/** Cache id->langue */
		private final Map<Long, Lang> mLangByIdCache;

		/**
		 * Liste de toutes les langues principales du site (affichage, application)
		 * triées par id croissant
		 */
		private final List<Lang> mAllSiteLanguages;

		/** Liste de toutes les langues TTS proposés triées par id croissant */
		private final List<Lang> mAllTTSLanguages;

		/** Liste de toutes les langues ASR proposés triées par id croissant */
		private final List<Lang> mAllASRLanguages;

		/**
		 * Liste de toutes les langues proposés pour la voix de l'objet triées par
		 * id croissant
		 */
		private final List<Lang> mAllObjectLanguages;

		public LangFactoryCommon(Collection<Lang> inAllLangs) {
			final SortedMap<Long, Lang> langList = new TreeMap<Long, Lang>();
			final Map<String, Lang> theMap = new HashMap<String, Lang>();
			for (final Lang lang : inAllLangs) {
				langList.put(Long.valueOf(lang.getId()), lang);
				theMap.put(lang.getIsoCode(), lang);
			}
			this.mLangByIsoCodeCache = Collections.unmodifiableMap(theMap);
			this.mLangByIdCache = Collections.unmodifiableMap(new HashMap<Long, Lang>(langList));
			this.mAllLangs = LangFactoryCommon.buildAllLangList(langList);
			this.mAllMotherTongueLangs = LangFactoryCommon.buildAllMotherTongueList(langList);
			this.mAllSiteLanguages = buildAllLanguagesWithType(langList, Lang.LANG_TYPE_SITE);
			this.mAllTTSLanguages = buildAllLanguagesWithType(langList, Lang.LANG_TYPE_TTS);
			this.mAllASRLanguages = buildAllLanguagesWithType(langList, Lang.LANG_TYPE_ASR);
			this.mAllObjectLanguages = buildAllLanguagesWithType(langList, Lang.LANG_TYPE_VOICE_OBJECT);
		}

		/**
		 * Accesseur sur toutes les langues classées par lang_id croissant.
		 */
		public final List<Lang> findAll() {
			return this.mAllLangs;
		}

		/**
		 * Accesseur à partir du code.
		 * 
		 * @param inAbbrev code de la langue.
		 * @return l'enregistrement (ou <code>null</code>).
		 */
		public final Lang findByIsoCode(String inIsoCode) {
			return this.mLangByIsoCodeCache.get(inIsoCode);
		}

		/**
		 * Accesseur à partir d'un id.
		 * 
		 * @param inId id de l'objet.
		 * @return l'enregistrement sinon langue par défaut US.
		 */
		public final Lang find(long inId) {
			final Lang theLangById = this.mLangByIdCache.get(inId);
			return (theLangById == null) ? this.mLangByIdCache.get(Lang.LANG_US_ID) : theLangById;
		}

		public List<Lang> findAllMotherTongue() {
			return this.mAllMotherTongueLangs;
		}

		/**
		 * Accesseur sur toutes les langues maternelles (non TTS)
		 */
		private static List<Lang> buildAllMotherTongueList(Map<Long, Lang> inCache) {
			final List<Lang> listLang = new ArrayList<Lang>();

			for (final Lang lang : inCache.values()) {
				if (lang.getType() == Lang.LANG_MOTHER_TONGUE) {
					listLang.add(lang);
				}
			}
			return Collections.unmodifiableList(listLang);
		}

		private static List<Lang> buildAllLangList(Map<Long, Lang> inCache) {
			final List<Lang> listLang = new ArrayList<Lang>();

			for (final Lang lang : inCache.values()) {
				if (lang.getType() != Lang.LANG_TYPE_DISABLED) {
					listLang.add(lang);
				}
			}
			return Collections.unmodifiableList(listLang);
		}

		public final Collection<Lang> getCollectionLangFromIdsArray(String[] ids) {
			final List<Lang> newLangCollection = new LinkedList<Lang>();
			for (final String id : ids) {
				newLangCollection.add(find(Long.parseLong(id)));
			}
			return newLangCollection;
		}

		/**
		 * Nouvelles fonctions de l'api qui prend en compte la nouvelle * structure
		 * de la table lang afin de determiné facilement les * différents types de
		 * langues proposés (site, ASR, TTS, VOICE) *
		 */

		private List<Lang> buildAllLanguagesWithType(Map<Long, Lang> inCache, long typeValue) {
			final List<Lang> listLang = new ArrayList<Lang>();
			for (final Lang lang : inCache.values()) {

				final char[] binary = Integer.toBinaryString(lang.getNewType() + 16).toCharArray();

				if (((binary[1] == '1') && (typeValue == Lang.LANG_TYPE_VOICE_OBJECT)) || ((binary[2] == '1') && (typeValue == Lang.LANG_TYPE_ASR)) || ((binary[3] == '1') && (typeValue == Lang.LANG_TYPE_TTS)) || ((binary[4] == '1') && (typeValue == Lang.LANG_TYPE_SITE))) {
					listLang.add(lang);
				}
			}
			return Collections.unmodifiableList(listLang);
		}

		/**
		 * Accesseur sur toutes les langues principales du site (affichage,
		 * application)
		 */
		public List<Lang> getAllSiteLanguages() {
			return this.mAllSiteLanguages;
		}

		/**
		 * Accesseur sur toutes les langues TTS proposés
		 */
		public List<Lang> getAllTTSLanguages() {
			return this.mAllTTSLanguages;
		}

		/**
		 * Accesseur sur toutes les langues ASR proposés
		 */
		public List<Lang> getAllASRLanguages() {
			return this.mAllASRLanguages;
		}

		/**
		 * Accesseur sur toutes les langues proposés pour la voix de l'objet
		 */
		public List<Lang> getAllObjectLanguages() {
			return this.mAllObjectLanguages;
		}

		/**
		 * Récupère la langue parente selon l'isocode passé ex : fr-FR => fr ex : fr
		 * => fr
		 * 
		 * @param inIsocodeLanguage
		 * @return null si parent non déterminé sinon Lang
		 */
		private static final Pattern PARENT_LANGUAGE_REGEX = Pattern.compile("(^[a-z]{2})(-.*)?");

		public Lang getParentByIsocode(String inIsocodeLanguage) {
			Lang theResultLang = null;

			final Matcher theMatcher = LangFactoryCommon.PARENT_LANGUAGE_REGEX.matcher(inIsocodeLanguage);
			if (theMatcher.matches()) {
				theResultLang = this.mLangByIsoCodeCache.get(theMatcher.group(1));
			}

			return theResultLang;
		}

	}

}
