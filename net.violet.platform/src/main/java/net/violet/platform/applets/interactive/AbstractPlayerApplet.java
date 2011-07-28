package net.violet.platform.applets.interactive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.events.InteractionEvent;
import net.violet.platform.events.TriggerEvent;
import net.violet.platform.interactif.config.PlayerConfig;
import net.violet.platform.interactif.config.PlayerConfigFactory;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.util.ConvertTools;

public abstract class AbstractPlayerApplet extends AbstractInteractiveApplet {

	protected static final int DEFAULT_VOICE_ID = 0; // voix par défaut permet de revenir au chapitre prècédent si le début de la lecture du chapitre courant n'a pas dépassé 5 secondes
	protected static final int TIMEPREVIOUS = 30000; // quelques secondes

	/**
	 * Génération d'un message correspondant au ZtampImpl lu.
	 * 
	 * @param inAction : Trigger sur l'action liée au ZtampImpl.
	 * @param inObject : objet concerné.
	 * @param inRfidSerial : serial du ZtampImpl lu.
	 * @return prépare un MessageImpl qui sera effectué par l'objet
	 */
	public void processTrigger(TriggerEvent inEvent) {
		// On balance un StartInteractive sur l'objet (inReader).
		final VObjectData target = ((InteractionEvent) inEvent.getEvent()).getTarget();
		final MessageDraft theMessage = new MessageDraft(target.getReference());
		theMessage.setApplication(inEvent.getApplication().getReference());
		theMessage.addStartInteractive(inEvent.getSubscription().getObject().getReference());
		theMessage.setTTLInSecond(inEvent.getTTL());
		MessageServices.send(theMessage);
	}

	/**
	 * Il y a un appuie long sur le bouton du lapin pendant la lecture d'un mp3
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
	protected List<Message> process_reco(VObject inObject, Subscription inSubscription, String cookie, int pos, int idx, int btn, byte[] recoFile) {

		// La reco n'est pas utilisé pour ce type de lecture
		final MessageDraft theMessage = new MessageDraft(inObject);

		theMessage.addEndInteractive();

		return Arrays.asList(new Message[] { theMessage });
	}

	@Override
	protected List<Message> process_rfid(VObject inObject, Subscription inSubscription, String cookie, int pos, int idx) {
		return new ArrayList<Message>();
	}

	/**
	 * retourne l'id du chapitre précédent ou courant si la lecture n'a pas
	 * dépassé 5 secondes
	 * 
	 * @param position
	 * @param voiceId : 0 => voice 1 ; 1 => voice 2
	 * @return retourne 0 si est on est arrivé au début
	 */
	protected int previousOrCurrentChapterId(PlayerConfig inIAPlayerConfig, int position, int voiceId) {
		int result = 0;
		final int maxIdChapter = inIAPlayerConfig.getIndexFilesByVoice(voiceId).length - 1;
		for (int i = maxIdChapter; i > 0; i--) {
			if (inIAPlayerConfig.getIndexFilesByVoice(voiceId)[i] <= position) {
				result = inIAPlayerConfig.getIndexFilesByVoice(voiceId)[i];

				if ((result + AbstractPlayerApplet.TIMEPREVIOUS > position) && (i >= 1)) {
					result = i - 1;
				} else {
					result = i;
				}

				break;
			}
		}
		return result;
	}

	/**
	 * retourne l'id du chapitre courant
	 * 
	 * @param position
	 * @param voiceId : 0 => voice 1 ; 1 => voice 2
	 * @return retourne id
	 */
	protected int getCurrentChapterId(PlayerConfig inIAPlayerConfig, int position, int voiceId) {

		int result = 0;
		final int maxIdChapter = inIAPlayerConfig.getIndexFilesByVoice(voiceId).length - 1;
		for (int i = maxIdChapter; i > 0; i--) {
			if (inIAPlayerConfig.getIndexFilesByVoice(voiceId)[i] <= position) {
				result = i;
				break;
			}
		}
		return result;
	}

	/**
	 * il y a un chapitre suivant?
	 * 
	 * @param index du chapitre courant
	 * @param voiceId : 0 => voice 1 ; 1 => voice 2
	 * @return true or false
	 */
	protected boolean hasNextChapter(PlayerConfig inIAPlayerConfig, int chapiterIndex, int voiceId) {
		return ((chapiterIndex + 1) == inIAPlayerConfig.getIndexFilesByVoice(voiceId).length) ? false : true;
	}

	/**
	 * update le markup du bouquin, (il y en a un par livre)
	 * 
	 * @param inPassiveObject : le bouquin
	 * @param inMarkup : le markup
	 * @param inChapiterIndex : l'index du chapitre courant
	 */
	protected void setMarkup(VObject inPassiveObject, String inMarkup, String inChapiterIndex, long inAppletID) {
		// enregistre le chapitre courant
		final AppletSettings theMarkup = Factories.APPLET_SETTINGS.getAppletSettingsByObject(inPassiveObject, inAppletID, inMarkup);
		if (theMarkup != null) { // un markup par bouquin initialisé
			theMarkup.setValue(inChapiterIndex);
		}
	}

	/**
	 * Calcule l'état à partir du cookie et de l'index.
	 * 
	 * @param inCookie le cookie
	 * @param inIndex index de la commande musicale interrompue.
	 */
	protected static int computeState(Cookie inCookie, int inIndex) {
		int theState = inCookie.getState();
		if ((theState == Cookie.STATE_HELP) || (theState == Cookie.STATE_UNINITIALIZED)) {
			if (inIndex == 0) {
				// Pendant l'aide.
				theState = Cookie.STATE_HELP;
			} else if (inIndex == 1) {
				theState = Cookie.STATE_STREAM;
			}
		}
		return theState;
	}

	public PlayerConfig getConfig(Subscription inSubscription, VObject inReader) {
		final ApplicationSetting isbnSetting = Factories.APPLICATION_SETTING.findByApplicationAndKey(inSubscription.getApplication(), "isbn");
		return PlayerConfigFactory.getConfig(Long.parseLong(isbnSetting.getValue()));
	}

	protected static class Cookie {

		static final int STATE_UNINITIALIZED = -1;
		static final int STATE_HELP = 0;
		static final int STATE_STREAM = 1;
		static final int STATE_BYE = 2;
		static final int STATE_END = 3;
		static final int STATE_OVER = 4; // L'utilisateur essaie d'aller trop loin dans la lecture.

		private int voiceId; // ceci correspond à la version du livre pour nathan et pour gallimard c'est la voix
		private int octetRead = -1;
		private int mState;
		private int chapiterIndex = -1;

		public Cookie(String settings) {
			this.voiceId = AbstractPlayerApplet.DEFAULT_VOICE_ID;
			this.mState = Cookie.STATE_UNINITIALIZED;

			if (settings != null) {
				final StringTokenizer thecookieList = new StringTokenizer(settings, ";");
				while (thecookieList.hasMoreTokens()) {
					final String[] cookieValues = thecookieList.nextToken().split(":");
					if (cookieValues.length == 2) {
						if ("v".equals(cookieValues[0].toLowerCase())) { // l'id de la voix
							this.voiceId = ConvertTools.atoi(cookieValues[1]);
						} else if ("o".equals(cookieValues[0].toLowerCase())) { // octet lu depuis le début du livre
							this.octetRead = ConvertTools.atoi(cookieValues[1]);
						} else if ("i".equals(cookieValues[0].toLowerCase())) { // idx courant
							this.mState = ConvertTools.atoi(cookieValues[1]);
						} else if ("c".equals(cookieValues[0].toLowerCase())) { // id du chapitre courant
							this.chapiterIndex = ConvertTools.atoi(cookieValues[1]);
						}
					}
				}
			}
		}

		public Cookie() {
			this.voiceId = AbstractPlayerApplet.DEFAULT_VOICE_ID;
			this.mState = Cookie.STATE_UNINITIALIZED;
		}

		public String getFromCookie() {
			final StringBuilder theResult = new StringBuilder();
			theResult.append("cookie=");
			theResult.append("v:");
			theResult.append(getVoiceId());
			theResult.append(net.violet.common.StringShop.SEMI_COLUMN);
			theResult.append("o:");
			theResult.append(getOctetRead());
			theResult.append(net.violet.common.StringShop.SEMI_COLUMN);
			theResult.append("i:");
			theResult.append(getState());
			theResult.append(net.violet.common.StringShop.SEMI_COLUMN);
			theResult.append("c:");
			theResult.append(getChapterIndex());
			theResult.append(net.violet.common.StringShop.SEMI_COLUMN);
			return theResult.toString();
		}

		public int getChapterIndex() {
			return this.chapiterIndex;
		}

		public void setChapterIndex(int chapiterIndex) {
			this.chapiterIndex = chapiterIndex;
		}

		public int getState() {
			return this.mState;
		}

		public void setState(int inState) {
			this.mState = inState;
		}

		public int getOctetRead() {
			return this.octetRead;
		}

		public void setOctetRead(int octetRead) {
			this.octetRead = octetRead;
		}

		public int getVoiceId() {
			return this.voiceId;
		}

		public void setVoiceId(int voiceId) {
			this.voiceId = voiceId;
		}
	}

}
