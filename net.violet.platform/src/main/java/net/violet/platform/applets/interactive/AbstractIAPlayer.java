package net.violet.platform.applets.interactive;

import java.util.Arrays;
import java.util.List;

import net.violet.common.utils.DigestTools;
import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.events.InteractionEvent;
import net.violet.platform.events.TriggerEvent;
import net.violet.platform.interactif.SecureStream;
import net.violet.platform.interactif.config.NathanPlayerConfig;
import net.violet.platform.interactif.config.PlayerConfig;
import net.violet.platform.interactif.config.PlayerConfigFactory;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.StringShop;

/**
 * Classe abstraite pour l'application de lecture d'un long MP3.
 */
public abstract class AbstractIAPlayer extends AbstractPlayerApplet {

	private final long applet_id;

	/**
	 * Constructeur par défaut.
	 */
	public AbstractIAPlayer(long inAppletId) {
		this.applet_id = inAppletId;
	}

	/**
	 * Génération d'un message correspondant au ZtampImpl lu.
	 * 
	 * @param inAction : Trigger sur l'action liée au ZtampImpl.
	 * @param inObject : objet concerné.
	 * @param inRfidSerial : serial du ZtampImpl lu.
	 * @return prépare un MessageImpl qui sera effectué par l'objet
	 */
	@Override
	public void processTrigger(TriggerEvent inEvent) {
		// On balance un StartInteractive sur l'objet (inReader).
		final VObjectData target = ((InteractionEvent) inEvent.getEvent()).getTarget();
		final MessageDraft theMessage = new MessageDraft(target.getReference());

		if (target.getObjectType().instanceOf(ObjectType.NABAZTAG_V2)) {
			theMessage.setApplication(inEvent.getApplication().getReference());
			theMessage.addStartInteractive(inEvent.getSubscription().getObject().getReference());
		} else if (target.getObjectType().instanceOf(ObjectType.MIRROR)) {

			final ApplicationSettingData isbnSetting = ApplicationSettingData.findByApplicationAndKey(inEvent.getApplication(), "isbn");

			PlayerConfig iaPlayerConfig = PlayerConfigFactory.getConfig(Long.parseLong(isbnSetting.getValue()));

			if (iaPlayerConfig == null) {
				iaPlayerConfig = new NathanPlayerConfig(new Long(isbnSetting.getValue()), new Long(NathanPlayerConfig.LIST_PATH_STREAM_OFFICIAL_VERSION.get(new Long(isbnSetting.getValue())).get(NathanPlayerConfig.VERSION_ID)));
			}

			final String thePathToFile = "/" + iaPlayerConfig.getPathStreamByVoice(0) + "/stream-" + iaPlayerConfig.getIndexFilesByVoice(0)[0] + StringShop.MP3_EXT;
			final String nowAsHex = Integer.toHexString((int) (System.currentTimeMillis() / 1000));
			final String theMD5 = DigestTools.digest(SecureStream.SECRET + thePathToFile + nowAsHex, DigestTools.Algorithm.MD5);

			theMessage.addAudio("broadcast" + SecureStream.STREAM_PREFIX + theMD5 + "/" + nowAsHex + thePathToFile + "?x", true, false);
			sendAudioStream(theMessage, getConfig(inEvent.getSubscription().getReference(), target.getReference()), 0, AbstractPlayerApplet.DEFAULT_VOICE_ID);
		}
		theMessage.setTTLInSecond(inEvent.getTTL());
		MessageServices.send(theMessage);
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
		int chapiterIndex = -1;
		int voice = AbstractPlayerApplet.DEFAULT_VOICE_ID; // une voix par version dans tout les cas sauf la belle lisse poire
		final Cookie theCookie = new Cookie();

		voice = setupVoiceId(inObject, inPassiveObject, theIAPlayerConfig, voice);

		theCookie.setVoiceId(voice);

		// recherche du markup
		final AppletSettings theMarkup = Factories.APPLET_SETTINGS.getAppletSettingsByObject(inPassiveObject, this.applet_id, theIAPlayerConfig.getMarkup());

		if (theMarkup != null) {
			chapiterIndex = ConvertTools.atoi(theMarkup.getValue());
		} else {
			// enregistre le markup du début , il est sur le bouquin . Le
			// secondary object permet de savoir quel est le propriètaire du
			// livre
			Factories.APPLET_SETTINGS.setAppletSettingsByObjects(inPassiveObject, inObject, this.applet_id, theIAPlayerConfig.getMarkup(), Long.toString(0), theIAPlayerConfig.getIsbn());
		}

		// ATTENTION ON DOIT PASSER LES COOKIES AVANT TOUTES LES URL
		if (chapiterIndex == -1) { // toute première lecture du livre
			chapiterIndex = 0; // afin que le bouquin soit lu du début
			theCookie.setChapterIndex(chapiterIndex);
			theMessage.addSetting(theCookie.getFromCookie());

			theMessage.addAudio(theIAPlayerConfig.getMusicHelpInit(), true, false); // lecture de l 'aide initiale
			theMessage.addChoreography(theIAPlayerConfig.getMusicHelpInit().replaceAll("\\.mp3", ".chor"), false);
		} else if (chapiterIndex == 0) {
			theCookie.setChapterIndex(chapiterIndex);
			theMessage.addSetting(theCookie.getFromCookie());
			// le livre a déjà été lu et on a marque marque page à 0
			theMessage.addAudio(theIAPlayerConfig.getMusicHelpLong(), true, false); // lecture de l 'aide longue
			theMessage.addChoreography(theIAPlayerConfig.getMusicHelpLong().replaceAll("\\.mp3", ".chor"), false);
		} else { // le livre a déjà été lu et contient un marque page > 0
			theCookie.setChapterIndex(chapiterIndex);
			theMessage.addSetting(theCookie.getFromCookie());
			theMessage.addAudio(theIAPlayerConfig.getMusicHelpShort(), true, false); // lecture de l 'aide courte
			theMessage.addChoreography(theIAPlayerConfig.getMusicHelpShort().replaceAll("\\.mp3", ".chor"), false);
		}

		// TODO vérifier que l'aide est moins longue que le ttl du token
		theMessage.addSetting("snd.btn.1=neutral");
		theMessage.addSetting("snd.btn.2=neutral");
		theMessage.addPalette(Palette.RANDOM_V2);

		sendAudioStream(theMessage, theIAPlayerConfig, chapiterIndex, voice);

		return Arrays.asList((Message) theMessage);
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

			if (btn == Message.CLIC_SIMPLE) {
				// Simple clic.
				theCookie.setOctetRead(finalPos); // on positionne l'endroit où la lecture s'est arreté
				theCookie.setState(Cookie.STATE_BYE); // MessageImpl de fin d'interactivité
				theCookie.setChapterIndex(chapiterIndex);// garde le chapitre courant
				theMessage.addSetting("snd.btn.1=clonk");
				theMessage.addSetting("snd.btn.2=clonk");
				theMessage.addSetting(theCookie.getFromCookie());
				theMessage.addAudio(theIAPlayerConfig.getMusicBye(), true, false);
				theMessage.addChoreography(theIAPlayerConfig.getMusicBye().replaceAll("\\.mp3", ".chor"), false);
			} else if (btn == Message.CLIC_DOUBLE) {
				// Fin complète.
				// A valider...
			}

			// enregistre le chapitre courant
			setMarkup(inSubscription.getObject(), theIAPlayerConfig.getMarkup(), Long.toString(chapiterIndex), this.applet_id);
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

			if (theState == Cookie.STATE_HELP) { // pendant la lecture de l'aide, on passe à la lecture du livre

				theCookie.setState(Cookie.STATE_STREAM);
				theCookie.setChapterIndex(0);
				theMessage.addSetting(theCookie.getFromCookie());
				theMessage.addPalette(Palette.RANDOM_V2);

				sendAudioStream(theMessage, theIAPlayerConfig, chapiterIndex, voiceId);
			} else if (theState == Cookie.STATE_STREAM) { // pendant la lecture du livre, on passe au prochain chapitre
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
					theCookie.setState(Cookie.STATE_END); // MessageImpl de fin d'histoire
					theMessage.addSetting(theCookie.getFromCookie());
					setupFinishedBook(inObject, inSubscription.getObject(), theIAPlayerConfig, false); // reset du markup et de la version
					theMessage.addSetting("snd.btn.1=clonk");
					theMessage.addSetting("snd.btn.2=clonk");
					theMessage.addAudio(theIAPlayerConfig.getMusicEndBook(), true, false);
					theMessage.addChoreography(theIAPlayerConfig.getMusicEndBook().replaceAll("\\.mp3", ".chor"), false);
					theMessage.addEndInteractive();

				} else {
					chapiterIndex++; // on passe au prochain chapitre
					theCookie.setState(Cookie.STATE_STREAM);
					theCookie.setChapterIndex(chapiterIndex);
					theMessage.addSetting(theCookie.getFromCookie());
					// TODO vérifier que l'aide est moins longue que le ttl du
					// token
					theMessage.addPalette(Palette.RANDOM_V2);
					sendAudioStream(theMessage, theIAPlayerConfig, chapiterIndex, voiceId);
				}

			} else if ((theState == Cookie.STATE_BYE) || (theState == Cookie.STATE_END)) { // pendant la lecture du au revoir , on
				// part
				// du
				// mode
				// interactif
				theMessage.addEndInteractive();
			}
		} else if (earr != 0) { // mouvement de l'oreille gauche (vu de l'utilisateur)

			int finalPos = pos; // il a lu le livre à partir du début

			if (theState == Cookie.STATE_HELP) { // pendant la lecture de l'aide, on relit l'aide
				theMessage.addAudio(theIAPlayerConfig.getMusicHelpLong(), true, false);
				theMessage.addChoreography(theIAPlayerConfig.getMusicHelpLong().replaceAll("\\.mp3", ".chor"), false);
				theMessage.addPalette(Palette.RANDOM_V2);
				finalPos = 0;
				// TODO vérifier que l'aide est moins longue que le ttl du token
				sendAudioStream(theMessage, theIAPlayerConfig, finalPos, voiceId);
			} else if (theState == Cookie.STATE_STREAM) { // pendant la lecture du livre, on retourne au précèdant chapitre
				int chapiterIndex = 0;

				if (theCookie.getChapterIndex() != -1) { // il a repris la lecture à partir d'un chapitre
					chapiterIndex = theCookie.getChapterIndex();
					finalPos += theIAPlayerConfig.getIndexFilesByVoice(voiceId)[chapiterIndex];
				}

				chapiterIndex = previousOrCurrentChapterId(theIAPlayerConfig, finalPos, voiceId);

				// ATTENTION ON DOIT PASSER LES COOKIES AVANT TOUTES LES URL
				if ((chapiterIndex == 0) && (getCurrentChapterId(theIAPlayerConfig, finalPos, voiceId) == 0)) { // est dans le premier chapitre
					theCookie.setState(Cookie.STATE_UNINITIALIZED); // reset des cookies
					theCookie.setChapterIndex(-1); // reset des cookies
					chapiterIndex = 0;
					theMessage.addSetting(theCookie.getFromCookie());
					theMessage.addAudio(theIAPlayerConfig.getMusicHelpLong(), true, false);
					theMessage.addChoreography(theIAPlayerConfig.getMusicHelpLong().replaceAll("\\.mp3", ".chor"), false);
				} else {
					theCookie.setState(Cookie.STATE_STREAM);
					theCookie.setChapterIndex(chapiterIndex);
					theMessage.addSetting(theCookie.getFromCookie());
				}
				// TODO vérifier que l'aide est moins longue que le ttl du token
				sendAudioStream(theMessage, theIAPlayerConfig, chapiterIndex, voiceId);
			} else if ((theState == Cookie.STATE_BYE) || (theState == Cookie.STATE_END)) {
				// pendant la lecture du "au revoir" ou du
				// "chapitre suivant inexistant", on revient au dernier
				// chapitre.
				// il a fini de lire le bouquin et pendant le message de fin, il
				// bouge l'oreille donc on revient au dernier chapitre
				final int lastIndexChapter = theIAPlayerConfig.getIndexFilesByVoice(voiceId).length - 1;
				theCookie.setState(Cookie.STATE_STREAM);
				theCookie.setChapterIndex(lastIndexChapter);
				theMessage.addSetting(theCookie.getFromCookie());

				// TODO vérifier que l'aide est moins longue que le ttl du token
				sendAudioStream(theMessage, theIAPlayerConfig, lastIndexChapter, voiceId);
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

		final Cookie theCookie = new Cookie(cookie);
		theCookie.setState(Cookie.STATE_END); // MessageImpl de fin d'histoire
		theMessage.addSetting(theCookie.getFromCookie());

		setupFinishedBook(inObject, inSubscription.getObject(), theIAPlayerConfig, true); // reset du markup et de la version
		theMessage.addSetting("snd.btn.1=clonk");
		theMessage.addSetting("snd.btn.2=clonk");
		theMessage.addAudio(theIAPlayerConfig.getMusicEndBook(), true, false);
		theMessage.addChoreography(theIAPlayerConfig.getMusicEndBook().replaceAll("\\.mp3", ".chor"), false);

		theMessage.addEndInteractive();

		return Arrays.asList(new Message[] { theMessage });

	}

	/**
	 * Permet de configurer la voix/version dès la lecture du bouquin
	 * 
	 * @return la voix/version
	 */
	protected abstract int setupVoiceId(VObject inReader, VObject inRfidSerial, PlayerConfig inIAPlayerConfig, int inVoice);

	/**
	 * Permet de remettre à zéro le markup et d'ajouter dans les stats le nombre
	 * de fois où le livre a fini d'etre lu ( inFinish==true)
	 */
	protected abstract void setupFinishedBook(VObject inReader, VObject inRfidSerial, PlayerConfig inIAPlayerConfig, boolean inFinish);

	/**
	 * Permet d'envoyer le mp3 en stream a partir d'un chapitre et d'une
	 * voix/version
	 */
	protected abstract void sendAudioStream(MessageDraft inMessage, PlayerConfig inIAPlayerConfig, int inChapiterIndex, int inVoice);

}
