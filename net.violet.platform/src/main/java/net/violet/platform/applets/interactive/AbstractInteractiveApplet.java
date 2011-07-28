package net.violet.platform.applets.interactive;

import java.util.Collections;
import java.util.List;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.interactif.Status;
import net.violet.platform.message.Message;

import org.apache.log4j.Logger;

public abstract class AbstractInteractiveApplet implements InteractiveApplet {

	private static final Logger LOGGER = Logger.getLogger(AbstractInteractiveApplet.class);

	/**
	 * Une interruption (appuis du bouton, oreilles bougées, reco ou lecture
	 * d'un rfid) est survenue pendant la lecture d'un mp3
	 * 
	 * @param inObject : objet concerné.
	 * @param inPassiveObject : objet de type ztamps concerné.
	 * @param cookie : afin de garder le contexte
	 * @param pos : nombre d'octets lus dans le flux
	 * @param idx : index de la commande musicale qui est interrompue
	 * @param interrupt : infos sur l'interruption survenue (type et valeurs)
	 * @return prépare un MessageDraft qui sera effectué par l'objet
	 */
	public final List<Message> processItMode(VObject inObject, Subscription inSubscription, String cookie, int pos, int idx, Status interrupt) {

		List<Message> messagesList = null;

		final Status.Source evntSrc = interrupt.getSource();

		try {
			if (evntSrc == Status.Source.DONE) { // le plus fréquent
				messagesList = process_finished(inObject, inSubscription, cookie);
			} else if (evntSrc == Status.Source.START) {
				messagesList = process_start(inObject, inSubscription, cookie);
			} else if (evntSrc == Status.Source.RFID) {
				messagesList = process_rfid(inObject, inSubscription, cookie, pos, idx);
			} else if (evntSrc == Status.Source.BUTTON) {
				messagesList = process_button(inObject, inSubscription, cookie, pos, idx, interrupt.getValue());
			} else if (evntSrc == Status.Source.EAR) {
				messagesList = process_ear(inObject, inSubscription, cookie, interrupt.getEarLeft(), interrupt.getEarRight(), pos, idx);
			} else if (evntSrc == Status.Source.RECO) {
				messagesList = process_reco(inObject, inSubscription, cookie, pos, idx, interrupt.getValue(), interrupt.getRecoFile());
			}

		} catch (final Exception e) {
			AbstractInteractiveApplet.LOGGER.error("Unable to process event " + evntSrc + " for object " + inObject, e);
		}

		if (messagesList == null) {
			return Collections.emptyList();
		}

		return messagesList;
	}

	protected abstract List<Message> process_start(VObject inObject, Subscription inSubscription, String inCookie) throws InvalidParameterException, ConversionException;

	protected abstract List<Message> process_button(VObject inObject, Subscription inSubscription, String cookie, int pos, int idx, int btn) throws InvalidParameterException, ConversionException;

	protected abstract List<Message> process_ear(VObject inObject, Subscription inSubscription, String cookie, int earl, int earr, int pos, int idx);

	protected abstract List<Message> process_reco(VObject inObject, Subscription inSubscription, String cookie, int pos, int idx, int btn, byte[] recoFile);

	protected abstract List<Message> process_rfid(VObject inObject, Subscription inSubscription, String cookie, int pos, int idx);

	protected abstract List<Message> process_finished(VObject inObject, Subscription inSubscription, String cookie) throws InvalidParameterException, ConversionException;
}
