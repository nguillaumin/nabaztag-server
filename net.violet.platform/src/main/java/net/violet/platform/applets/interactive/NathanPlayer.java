package net.violet.platform.applets.interactive;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.interactif.config.NathanPlayerConfig;
import net.violet.platform.interactif.config.PlayerConfig;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.util.StringShop;

import org.apache.jcs.access.exception.InvalidArgumentException;

/**
 * Classe pour l'application lecture d'un long MP3 (typiquement pour la lecture
 * des livres Nathan).
 */
public class NathanPlayer extends AbstractIAPlayer {

	private static final Map<Long, NathanPlayerConfig> IAPLAYER_NATHANVERSIONCONFIG_MAP = new HashMap<Long, NathanPlayerConfig>();

	private static final Map<Long, PlayerConfig> IAPLAYER_NATHANISBN_MAP = new HashMap<Long, PlayerConfig>();

	// TODO: à dégager dans la table applet
	public static final long APPLET_ID = 4;

	public NathanPlayer() {
		super(NathanPlayer.APPLET_ID);
	}

	NathanPlayerConfig getConfig(String inConfig) {

		NathanPlayerConfig theResult = null;
		final String[] stringArray = inConfig.split(StringShop.DOUBLE_COLUMNS, 2);

		if (stringArray.length == 2) {
			final Long theISBN = Long.parseLong(stringArray[0]);
			final Long theVersion = Long.parseLong(stringArray[1]);

			if ((theISBN != null) && (theVersion != null)) {
				theResult = NathanPlayer.IAPLAYER_NATHANVERSIONCONFIG_MAP.get(theVersion);
				if (theResult == null) {
					theResult = new NathanPlayerConfig(theISBN, theVersion);
					NathanPlayer.IAPLAYER_NATHANVERSIONCONFIG_MAP.put(theVersion, theResult);
				}
			}
		}

		return theResult;
	}

	@Override
	public PlayerConfig getConfig(Subscription inSubscription, VObject inReader) {
		try {
			final ApplicationSetting isbnSettings = Factories.APPLICATION_SETTING.findByApplicationAndKey(inSubscription.getApplication(), "isbn");
			final String theParam = NathanPlayer.getParamByIBSN(isbnSettings.getValue(), inReader, inSubscription.getObject());
			return getConfig(theParam);
		} catch (final IllegalArgumentException e) {}
		return null;

	}

	/**
	 * Permet d'envoyer soit en stream sécurisé la version officielle sinon en
	 * stream les versions des utilisateurs
	 * 
	 * @param inMessage : MessageDraft
	 * @param inSecure : sécurisé le stream ou pas
	 * @param inIAPlayerConfig : Config du player
	 * @param inChapiterIndex : chapitre courant
	 * @param inVoiceId : voix utilisé
	 * @param inPosition : position dans le bouquin
	 */
	private void addAudioStreamByVersion(MessageDraft inMessage, boolean inSecure, NathanPlayerConfig inIAPlayerNathanConfig, int inChapiterIndex, int inVoiceId) {
		if (inSecure) { // stream sécurisé
			inMessage.addAudioStreamSecure(inIAPlayerNathanConfig.getPathStreamByVoice(inVoiceId), inIAPlayerNathanConfig.getIndexFilesByVoice(inVoiceId)[inChapiterIndex]);
		} else { // version nathan d'un utilisateur
			inMessage.addAudio(inIAPlayerNathanConfig.getFilesList().get(inChapiterIndex).getPath(), true, true);
		}
	}

	@Override
	protected int setupVoiceId(VObject inReader, VObject inRfidSerial, PlayerConfig inIAPlayerConfig, int inVoice) {

		final NathanPlayerConfig theIAPlayerNathanConfig = (NathanPlayerConfig) inIAPlayerConfig;
		// incrémente le nombre de fois que le bouquin est lu sur un lapin avec
		// une voix donnée.
		Factories.APPLET_SETTINGS.addCountAppletSettingsByObjects(inReader, inRfidSerial, NathanPlayer.APPLET_ID, theIAPlayerNathanConfig.getVoice((int) theIAPlayerNathanConfig.getVersion()));

		return inVoice; // il y a qu'une voix par version pour les bouquins nathan
	}

	@Override
	protected void sendAudioStream(MessageDraft inMessage, PlayerConfig inIAPlayerConfig, int inChapiterIndex, int inVoice) {
		final NathanPlayerConfig theIAPlayerNathanConfig = (NathanPlayerConfig) inIAPlayerConfig;
		final NathanVersion theNathanVersion = theIAPlayerNathanConfig.getNathanVersion();
		addAudioStreamByVersion(inMessage, theNathanVersion.getOfficial(), theIAPlayerNathanConfig, inChapiterIndex, inVoice);
	}

	@Override
	protected void setupFinishedBook(VObject inReader, VObject inRfidSerial, PlayerConfig inIAPlayerConfig, boolean inFinish) {
		// on reset le marque page car il a fini de lire le livre
		setMarkup(inRfidSerial, inIAPlayerConfig.getMarkup(), Long.toString(0), NathanPlayer.APPLET_ID);
		// on reset aussi la version courante afin que la prochaine fois on
		// puisse tirer aléatoiremant dans les versions
		Factories.APPLET_SETTINGS.setAppletSettingsByObjects(inRfidSerial, inReader, NathanPlayer.APPLET_ID, inIAPlayerConfig.getVoice(), Integer.toString(0), inIAPlayerConfig.getIsbn());

		if (inFinish) {
			final NathanPlayerConfig theIAPlayerNathanConfig = (NathanPlayerConfig) inIAPlayerConfig;
			// incrémente la lecture du bouquin ( a faire que dans le cas ou il
			// a écouté jusqu'au bout)
			Factories.APPLET_SETTINGS.addCountAppletSettingsByObjects(inReader, inRfidSerial, NathanPlayer.APPLET_ID, theIAPlayerNathanConfig.getCountFinish(theIAPlayerNathanConfig.getVersion()));

		}

	}

	/**
	 * Permet de construire le nouveau param pour récupérer la config du player
	 * selon la version
	 * 
	 * @return un nouveau param construit (ISBN::version)
	 * @throws InvalidArgumentException
	 */
	public static String getParamByIBSN(String inISBN, VObject inReader, VObject inPassiveObject) throws IllegalArgumentException {

		final Long theISBN = Long.parseLong(inISBN);
		PlayerConfig theIAPlayerConfig = NathanPlayer.IAPLAYER_NATHANISBN_MAP.get(theISBN);
		if (theIAPlayerConfig == null) {
			theIAPlayerConfig = new NathanPlayerConfig(theISBN);
			NathanPlayer.IAPLAYER_NATHANISBN_MAP.put(theISBN, theIAPlayerConfig);
		}

		// La clé voice correspond à la version de nathan
		final AppletSettings theVoice = Factories.APPLET_SETTINGS.getAppletSettingsByObject(inPassiveObject, NathanPlayer.APPLET_ID, theIAPlayerConfig.getVoice());
		String versionValue = null;
		if (theVoice != null) {
			final long theVersion = Integer.parseInt(theVoice.getValue());
			if (theVersion > 0) { // on reste sur la version courante et on associe la lecture du bouquin à un autre utilisateur s'il y a eu prêt
				versionValue = theVoice.getValue();
				theVoice.setSecondaryObject(inReader);
			} else if (theVersion == 0) { // on va chercher dans les versions aléatoires
				final AppletSettings theVersionSettings = Factories.APPLET_SETTINGS.getAppletSettingsByObject(inPassiveObject, NathanPlayer.APPLET_ID, String.valueOf(((NathanPlayerConfig) theIAPlayerConfig).getAllVersions()));
				if (theVersionSettings != null) {
					final StringBuilder valueSettings = new StringBuilder();
					final List<String> idVersionsList = Arrays.asList(theVersionSettings.getValue().split(net.violet.common.StringShop.SEMI_COLUMN));
					Collections.shuffle(idVersionsList);
					boolean first = true;
					for (final String versionID : idVersionsList) { // Check si la selection a bien des versions existantes
						if (!net.violet.common.StringShop.EMPTY_STRING.equals(versionID)) {
							final NathanVersion version = Factories.NATHAN_VERSION.find(Integer.parseInt(versionID));
							if (version != null) {
								if (first) { // on a trouvé une version
									versionValue = String.valueOf(version.getId());
									Factories.APPLET_SETTINGS.setAppletSettingsByObjects(inPassiveObject, inReader, NathanPlayer.APPLET_ID, theIAPlayerConfig.getVoice(), versionValue, theIAPlayerConfig.getIsbn());
									first = false;
								}
								valueSettings.append(version.getId());
								valueSettings.append(net.violet.common.StringShop.SEMI_COLUMN);
							}
						}
					}
					theVersionSettings.setValue(valueSettings.toString());
				}
			}
		}

		if (versionValue == null) { // on a trouvé aucune version alors on prend la version officielle
			final List<NathanVersion> listOfficielVersion = Factories.NATHAN_VERSION.findOfficialVersions(inISBN);

			// On récupère la version officielle (pour l'instant un seul par
			// bouquin)
			final NathanVersion theNathanVersion = listOfficielVersion.get(0);
			if (theNathanVersion != null) {
				versionValue = String.valueOf(theNathanVersion.getId());
				Factories.APPLET_SETTINGS.setAppletSettingsByObjects(inPassiveObject, inReader, NathanPlayer.APPLET_ID, theIAPlayerConfig.getVoice(), versionValue, theIAPlayerConfig.getIsbn());
				Factories.APPLET_SETTINGS.setAppletSettingsByObjects(inPassiveObject, null, NathanPlayer.APPLET_ID, ((NathanPlayerConfig) theIAPlayerConfig).getAllVersions(), versionValue, theIAPlayerConfig.getIsbn());
			}
		}

		if (versionValue == null) {
			throw new IllegalArgumentException("invalid param");
		}

		return inISBN + StringShop.DOUBLE_COLUMNS + versionValue; // création des nouveaux paramètres ;
	}
}
