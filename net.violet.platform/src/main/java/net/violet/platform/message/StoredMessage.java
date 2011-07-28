package net.violet.platform.message;

import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.elements.SequencePart;

/**
 * Classe pour un message stocké dans la base de donnée. TODO: dégager cette
 * classe.
 */
public class StoredMessage extends AddressedMessage {

	/**
	 * Liste des séquences.
	 */
	private final List<Sequence> mSequenceList;

	/**
	 * Constructeur à partir de l'id du message, et l'objet.
	 */
	public StoredMessage(VObject inDest, long inEventID) {
		super(inDest);

		setEventID(new Long(inEventID));

		if ((inEventID == Message.EV_DUMMY) || (inEventID == Message.EV_ASLEEP) || (inEventID == 0)) {
			this.mSequenceList = Collections.emptyList();
		} else {
			this.mSequenceList = Factories.EVSEQ.findByEvent(inEventID);
		}
	}

	public List<Sequence> getSequenceList() {
		return this.mSequenceList;
	}

	public List<SequencePart> getSequencePart() {
		throw new UnsupportedOperationException();
	}
}
