package net.violet.platform.applets.interactive;

import java.util.Arrays;
import java.util.List;

import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.interactif.config.PlayerConfig;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.util.ConvertTools;

/**
 * Classe pour l'application lecture d'un long MP3 sans intro , etc.
 */
public class SimplePlayer extends AbstractPlayerApplet {

	/**
	 * utilisé lors d'un passage d'un ztamp
	 */
	public static final String APPLET_NAME = "SimplePlayer";

	// TODO: à dégager dans la table applet
	public static final long APPLET_ID = 10;

	public SimplePlayer() {
		// this space for rent
	}

	/**
	 * Lancement du mode interactif sur le lapin
	 * 
	 * @param inObject : objet concerné.
	 * @param inPassiveObject : objet de type ztamps concerné.
	 * @return prépare un MessageDraft qui sera effectué par l'objet
	 */
	@Override
	protected List<Message> process_start(VObject inObject, Subscription inSubscription, String inCookie) {
		final VObject inPassiveObject = inSubscription.getObject();
		final MessageDraft theMessage = new MessageDraft(inObject);

		theMessage.addSetting("snd.btn.1=clonk");
		theMessage.addSetting("snd.btn.2=clonk");
		theMessage.addSetting("record.enabled=false");
		theMessage.addSetting("streaming.chor.enabled=false");
		theMessage.addSetting("int.mask=8");

		final PlayerConfig theIAPlayerConfig = getConfig(inSubscription, inObject);
		int chapiterIndex = 0;
		final int voice = AbstractPlayerApplet.DEFAULT_VOICE_ID; // une voix par version dans tout les cas sauf la belle lisse poire
		final Cookie theCookie = new Cookie();

		theCookie.setVoiceId(voice);
		theCookie.setState(Cookie.STATE_STREAM);

		// recherche du markup
		final AppletSettings theMarkup = Factories.APPLET_SETTINGS.getAppletSettingsByObject(inPassiveObject, getAppletID(), theIAPlayerConfig.getMarkup());

		if (theMarkup != null) {
			chapiterIndex = ConvertTools.atoi(theMarkup.getValue());
		} else {
			// enregistre le markup du début , il est sur le bouquin . Le
			// secondary object permet de savoir quel est le propriètaire du
			// livre
			Factories.APPLET_SETTINGS.setAppletSettingsByObjects(inPassiveObject, inObject, getAppletID(), theIAPlayerConfig.getMarkup(), Long.toString(0), theIAPlayerConfig.getIsbn());
		}

		theCookie.setChapterIndex(chapiterIndex);
		theMessage.addSetting(theCookie.getFromCookie());
		theMessage.addSetting("snd.btn.1=neutral");
		theMessage.addSetting("snd.btn.2=neutral");
		theMessage.addPalette(Palette.RANDOM_V2);

		theMessage.addAudioStreamSecure(theIAPlayerConfig.getPathStreamByVoice(voice), theIAPlayerConfig.getIndexFilesByVoice(voice)[chapiterIndex]);
		return Arrays.asList(new Message[] { theMessage });
	}

	/**
	 * Le bouton du lapin est appuyé pendant la lecture d'un mp3
	 * 
	 * @param inObject : objet concerné.
	 * @param inPassiveObject : objet de type ztamps concerné.
	 * @param cookie : afin de garder le contexte
	 * @param pos : nombre d'octets lus dans le flux
	 * @param idx : index de la commande musicale qui est interrompue
	 * @param btn : 1 si simple clic, 2 si double clic
	 * @return prépare un MessageDraft qui sera effectué par l'objet
	 */
	@Override
	protected List<Message> process_button(VObject inObject, Subscription inSubscription, String cookie, int pos, int idx, int btn) {
		final MessageDraft theMessage = new MessageDraft(inObject);
		final PlayerConfig theIAPlayerConfig = getConfig(inSubscription, inObject);

		final Cookie theCookie = new Cookie(cookie);
		final int theState = AbstractPlayerApplet.computeState(theCookie, idx);

		if (theState == Cookie.STATE_STREAM) { // lecture d'un chapitre du livre
			final int voiceId = theCookie.getVoiceId();
			int chapiterIndex = 0;
			int finalPos = pos;
			if (theCookie.getChapterIndex() != -1) { // il a repris la lecture à partir d'un chapitre
				chapiterIndex = theCookie.getChapterIndex();
				finalPos += theIAPlayerConfig.getIndexFilesByVoice(voiceId)[chapiterIndex];
				chapiterIndex = getCurrentChapterId(theIAPlayerConfig, finalPos, voiceId);
			} else { // il a lu le livre à partir du début
				chapiterIndex = getCurrentChapterId(theIAPlayerConfig, finalPos, voiceId);
			}

			// enregistre le chapitre courant
			setMarkup(inSubscription.getObject(), theIAPlayerConfig.getMarkup(), Long.toString(chapiterIndex), getAppletID());
		}

		theMessage.addEndInteractive();

		return Arrays.asList(new Message[] { theMessage });
	}

	/**
	 * les oreilles du lapin ont été bougés pendant la lecture d'un mp3
	 * 
	 * @param inObject : objet concerné.
	 * @param inPassiveObject : objet de type ztamps concerné.
	 * @param cookie : afin de garder le contexte
	 * @param earl : oreille gauche
	 * @param earr : oreille droite
	 * @param pos : nombre d'octets lus dans le flux
	 * @param idx : index de la commande musicale qui est interrompue
	 * @return prépare un MessageDraft qui sera effectué par l'objet
	 */
	@Override
	protected List<Message> process_ear(VObject inObject, Subscription inSubscription, String cookie, int earl, int earr, int pos, int idx) {

		final MessageDraft theMessage = new MessageDraft(inObject);

		final PlayerConfig theIAPlayerConfig = getConfig(inSubscription, inObject);

		final Cookie theCookie = new Cookie(cookie);
		final int theState = AbstractPlayerApplet.computeState(theCookie, idx);
		final int voiceId = theCookie.getVoiceId();

		if (earl != 0) { // mouvement de l'oreille droite (vu de l'utilisateur)
			int chapiterIndex = 0;

			if (theState == Cookie.STATE_STREAM) { // pendant la lecture du livre, on passe au prochain chapitre
				int finalPos = pos;
				if (theCookie.getChapterIndex() != -1) { // il a repris la lecture à partir d'un chapitre
					chapiterIndex = theCookie.getChapterIndex();
					finalPos += theIAPlayerConfig.getIndexFilesByVoice(voiceId)[chapiterIndex];
					chapiterIndex = getCurrentChapterId(theIAPlayerConfig, finalPos, voiceId);
				} else { // il a lu le livre à partir du début
					chapiterIndex = getCurrentChapterId(theIAPlayerConfig, pos, voiceId);
				}

				if (!hasNextChapter(theIAPlayerConfig, chapiterIndex, voiceId)) {// il
					// n
					// 'y
					// a
					// plus
					// de
					// chapitre
					// suivant
					// on reset le marque page car il a fini de lire le livre
					setMarkup(inSubscription.getObject(), theIAPlayerConfig.getMarkup(), Long.toString(0), SimplePlayer.APPLET_ID);

					theMessage.addEndInteractive();
				} else {
					chapiterIndex++; // on passe au prochain chapitre
					theCookie.setState(Cookie.STATE_STREAM);
					theCookie.setChapterIndex(chapiterIndex);
					theMessage.addSetting(theCookie.getFromCookie());
					theMessage.addPalette(Palette.RANDOM_V2);
					theMessage.addAudioStreamSecure(theIAPlayerConfig.getPathStreamByVoice(voiceId), theIAPlayerConfig.getIndexFilesByVoice(voiceId)[chapiterIndex]);
				}
			} else { // on part du mode interactif
				theMessage.addEndInteractive();
			}
		} else if (earr != 0) { // mouvement de l'oreille gauche (vu de l'utilisateur)

			int finalPos = pos; // il a lu le livre à partir du début

			if (theState == Cookie.STATE_STREAM) { // pendant la lecture du livre, on retourne au précèdant chapitre
				int chapiterIndex = 0;

				if (theCookie.getChapterIndex() != -1) { // il a repris la lecture à partir d'un chapitre
					chapiterIndex = theCookie.getChapterIndex();
					finalPos += theIAPlayerConfig.getIndexFilesByVoice(voiceId)[chapiterIndex];
				}

				chapiterIndex = previousOrCurrentChapterId(theIAPlayerConfig, finalPos, voiceId);

				theCookie.setState(Cookie.STATE_STREAM);
				theCookie.setChapterIndex(chapiterIndex);
				theMessage.addSetting(theCookie.getFromCookie());
				theMessage.addAudioStreamSecure(theIAPlayerConfig.getPathStreamByVoice(voiceId), theIAPlayerConfig.getIndexFilesByVoice(voiceId)[chapiterIndex]);
			} else { // on part du mode interactif
				theMessage.addEndInteractive();
			}
		}

		return Arrays.asList(new Message[] { theMessage });
	}

	/**
	 * Le lapin a fini de lire les urls
	 * 
	 * @param inObject : objet concerné.
	 * @param inPassiveObject : objet de type ztamps concerné.
	 * @param cookie : afin de garder le contexte
	 * @return prépare un MessageDraft qui sera effectué par l'objet
	 */
	@Override
	protected List<Message> process_finished(VObject inObject, Subscription inSubscription, String cookie) {
		final MessageDraft theMessage = new MessageDraft(inObject);

		final PlayerConfig theIAPlayerConfig = getConfig(inSubscription, inObject);

		// on reset le marque page car il a fini de lire le livre
		setMarkup(inSubscription.getObject(), theIAPlayerConfig.getMarkup(), Long.toString(0), getAppletID());

		theMessage.addEndInteractive();

		return Arrays.asList(new Message[] { theMessage });
	}

	public Long getAppletID() {
		return SimplePlayer.APPLET_ID;
	}

}
