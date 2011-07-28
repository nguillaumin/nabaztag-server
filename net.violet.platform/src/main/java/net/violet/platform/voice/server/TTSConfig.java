package net.violet.platform.voice.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public class TTSConfig {


	private static final Logger LOGGER = Logger.getLogger(TTSServerImpl.class);

	/**
	 * Liste des voix.
	 */
	private final Map<String, TTSVoice> mVoices;

	/**
	 * Voix par défaut.
	 */
	private final Map<String, TTSVoice> mDefaultVoices;

	/**
	 * Voix par défaut (langue inconnue).
	 */
	private TTSVoice mDefaultVoice;

	/**
	 * Liste des moteurs.
	 */
	private final List<TTSEngineConfig> mEngines;

	/**
	 * Constructeur par défaut.
	 */
	public TTSConfig() {
		this.mVoices = new HashMap<String, TTSVoice>();
		this.mEngines = new LinkedList<TTSEngineConfig>();
		this.mDefaultVoices = new HashMap<String, TTSVoice>();
	}

	public void addVoice(TTSVoice inVoice) {
		this.mVoices.put(inVoice.getName(), inVoice);
		if (inVoice.getDefault() || !this.mDefaultVoices.containsKey(inVoice.getLanguage())) {
			this.mDefaultVoices.put(inVoice.getLanguage(), inVoice);
			if ((this.mDefaultVoice == null) || !this.mDefaultVoice.getDefault()) {
				this.mDefaultVoice = inVoice;
			}
		}
	}

	public void addEngine(TTSEngineConfig inEngineConfig) {
		this.mEngines.add(inEngineConfig);
	}

	/**
	 * Charge la liste des engines depuis le fichier xml au démarrage
	 * 
	 * @return
	 */
	public static TTSConfig loadConfig() {
		TTSConfig theResult = null;
		try {
			final Digester digester = new Digester();
			digester.setValidating(false);

			digester.addObjectCreate("config", TTSConfig.class);
			digester.addObjectCreate("config/voice", TTSVoice.class);
			digester.addSetNext("config/voice", "addVoice", TTSVoice.class.getCanonicalName());
			digester.addSetProperties("config/voice");
			digester.addObjectCreate("config/engine", TTSEngineConfig.class);
			digester.addSetNext("config/engine", "addEngine", TTSEngineConfig.class.getCanonicalName());
			digester.addSetProperties("config/engine");
			digester.addObjectCreate("config/engine/envp", ArrayList.class);
			digester.addCallMethod("config/engine/envp/env", "add", 0 /*
																									 * single
																									 * parameter
																									 * from the
																									 * body of
																									 * this
																									 * element
																									 */);
			digester.addSetNext("config/engine/envp", "setEnvironment", ArrayList.class.getCanonicalName());

			theResult = (TTSConfig) digester.parse(TTSConfig.class.getClassLoader().getResourceAsStream("ttsserver.xml"));
		} catch (final SAXException sae) {
			TTSConfig.LOGGER.fatal(sae, sae);
		} catch (final IOException ioe) {
			TTSConfig.LOGGER.fatal(ioe, ioe);
		}
		return theResult;
	}

	public TTSVoice getVoice(String inVoiceName) {
		return this.mVoices.get(inVoiceName);
	}

	public Collection<TTSVoice> getVoices() {
		return this.mVoices.values();
	}

	public List<TTSEngineConfig> getEngineConfigs() {
		return this.mEngines;
	}

	public TTSVoice getDefaultVoice(String inLang) {
		TTSVoice theVoice = this.mDefaultVoices.get(inLang);
		if (theVoice == null) {
			TTSConfig.LOGGER.info("No voice for lang " + inLang);
			theVoice = this.mDefaultVoice;
		}
		return theVoice;
	}
}
