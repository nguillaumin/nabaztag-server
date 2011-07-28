package net.violet.platform.applets.interactive;

import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.interactif.config.PlayerConfig;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.util.ConvertTools;

/**
 * Classe pour l'application lecture d'un long MP3 (typiquement pour la lecture
 * des livres Gallimard).
 */
public class CommonPlayer extends AbstractIAPlayer {

	public static final long APPLET_ID = 1;

	public CommonPlayer() {
		super(CommonPlayer.APPLET_ID);
	}

	@Override
	protected void sendAudioStream(MessageDraft inMessage, PlayerConfig inIAPlayerConfig, int inChapiterIndex, int inVoice) {
		inMessage.addAudioStreamSecure(inIAPlayerConfig.getPathStreamByVoice(inVoice), inIAPlayerConfig.getIndexFilesByVoice(inVoice)[inChapiterIndex]);
	}

	@Override
	protected void setupFinishedBook(VObject inReader, VObject inRfidSerial, PlayerConfig inIAPlayerConfig, boolean inFinish) {
		// on reset le marque page car il a fini de lire le livre
		setMarkup(inRfidSerial, inIAPlayerConfig.getMarkup(), Long.toString(0), CommonPlayer.APPLET_ID);

		if (inFinish) {
			// incrémente la lecture du bouquin ( a faire que dans le cas ou il
			// a écouté jusqu'au bout)
			Factories.APPLET_SETTINGS.addCountAppletSettingsByObjects(inReader, inRfidSerial, CommonPlayer.APPLET_ID, inIAPlayerConfig.getCountFinish());
		}

	}

	@Override
	protected int setupVoiceId(VObject inReader, VObject inRfidSerial, PlayerConfig inIAPlayerConfig, int inVoice) {
		int voice = inVoice;
		// recherche de la voix par le livre
		final AppletSettings theVoice = Factories.APPLET_SETTINGS.getAppletSettingsByObject(inRfidSerial, CommonPlayer.APPLET_ID, inIAPlayerConfig.getVoice());

		if (theVoice == null) { // il n'a pas eu de configuration de la voix, je positionne par défaut une voix pour ce livre
			Factories.APPLET_SETTINGS.setAppletSettingsByObjects(inRfidSerial, inReader, CommonPlayer.APPLET_ID, inIAPlayerConfig.getVoice(), Long.toString(voice), inIAPlayerConfig.getIsbn());
		} else { // il y une voix de choisi pour ce bouquin,
			voice = ConvertTools.atoi(theVoice.getValue());
			theVoice.setSecondaryObject(inReader);
		}

		// incrémente le nombre de fois que le bouquin est lu sur un lapin avec
		// une voix donnée.
		Factories.APPLET_SETTINGS.addCountAppletSettingsByObjects(inReader, inRfidSerial, CommonPlayer.APPLET_ID, inIAPlayerConfig.getVoice(voice));

		return 0;
	}

}
