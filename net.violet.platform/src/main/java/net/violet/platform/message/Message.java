package net.violet.platform.message;

import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;
import net.violet.platform.message.elements.SequencePart;
import net.violet.platform.util.CCalendar;

/**
 * Interface pour un message.
 */
public interface Message {

	/**
	 * ID pour un message DUMMY.
	 */
	int EV_DUMMY = 0x7fffffff;

	/**
	 * ID pour un message couché.
	 */
	int EV_ASLEEP = 0x7ffffffe;

	/**
	 * Nouveau format pour la source 4 (update).
	 */
	int ENTETE_MAJ_TRAME_4 = 0x7ffffffe;

	enum SOURCE {
		DELETE(0, "Delete Source"),
		WEATHER(1, "Meteo"),
		TRADE(2, "Bourse"),
		TRAFFIC(3, "Trafic"),
		LEFT_EAR(4, "Left Ear"),
		RIGHT_EAR(5, "Right Ear"),
		MAIL(6, "Mail"),
		AIR(7, "Air"),
		MESSAGE(8, "Message"),
		TAICHI(14, "Taichi"),
		ADVANCED_TRADE(20, "Bourse Full");

		private final int id;
		private final String dumpValue;

		SOURCE(int id, String dumpValue) {
			this.id = id;
			this.dumpValue = dumpValue;
		}

		public int getId() {
			return this.id;
		}

		public String getDumpValue() {
			return this.dumpValue;
		}

		public static SOURCE findById(int id) {
			for (final SOURCE aSource : SOURCE.values()) {
				if (aSource.id == id) {
					return aSource;
				}
			}
			return null;
		}
	}

	enum MODE {
		ACTIF(0),
		VEILLE(1),
		FORCE_ACTIF(2),
		FORCE_VEILLE(3);

		private final int id;

		MODE(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}

		public static MODE findById(Integer id) {
			for (final MODE aMode : MODE.values()) {
				if (aMode.getId() == id) {
					return aMode;
				}
			}
			return null;
		}
	}

	// TODO a virer
	int MODE_ACTIF = 0;
	int MODE_VEILLE = 1;
	int MODE_FORCE_ACTIF = 2;
	int MODE_FORCE_VEILLE = 3;

	// TODO remplacer par une enum
	int CLIC_SIMPLE = 1;
	int CLIC_DOUBLE = 2;

	/**
	 * Accesseur sur le destinataire.
	 * 
	 * @return le destinataire du message.
	 */
	VObject getReceiver();

	/**
	 * Accesseur sur l'ID du message.
	 * 
	 * @return l'ID du message ou <code>null</code> si le message n'a pas d'ID.
	 */
	Long getEventID();

	/**
	 * Accesseur sur la trame "mode du lapin": MODE_ACTIF, MODE_VEILLE ou
	 * <code>null</code> si le message ne contient pas cette trame.
	 */
	Integer getStatus();

	/**
	 * Accesseur sur le mode des sources.
	 * 
	 * @return <code>true</code> si les sources sont une mise à jour,
	 *         <code>false</code> sinon.
	 */
	boolean isSourceModeUpdate();

	/**
	 * Accesseur sur les sources.
	 * 
	 * @return les sources ou <code>null</code> si le message ne contient pas
	 *         cette trame.
	 */
	Map<String, Integer> getSources();

	/**
	 * Accesseur sur la position de l'oreille gauche.
	 * 
	 * @return la position de l'oreille gauche ou <code>null</code> si le
	 *         message ne contient pas cette trame.
	 */
	Integer getLeftEarPosition();

	/**
	 * Accesseur sur la position de l'oreille droite.
	 * 
	 * @return la position de l'oreille droite ou <code>null</code> si le
	 *         message ne contient pas cette trame.
	 */
	Integer getRightEarPosition();

	Application getApplication();

	/**
	 * Accesseur sur le nombre de messages.
	 * 
	 * @return le nombre de messages ou <code>null</code> si le message ne
	 *         contient pas cette trame.
	 */
	RABBIT_STATE getNbMessages();

	List<Sequence> getSequenceList();

	List<SequencePart> getSequencePart();

	/**
	 * Ajout un event id
	 */
	void setEventID(Long inEventID);

	/**
	 * 
	 */
	void setMessageID(Long inMessageID);

	/**
	 * Accesseur sur l'id du message (pour notify).
	 */
	Long getMessageID();

	/**
	 * Accesseur sur le titre du message (pour le log)
	 */
	String getTitle();

	CCalendar getDeliveryDate();

	/**
	 * TTL du message dans la file d'attente, en secondes.
	 * 
	 * @return le TTL du message, ou <code>0</code> si le message doit être
	 *         conservé indéfiniement dans la file d'attente.
	 */
	int getTTLInSecond();

	/**
	 * TTL du message dans la file d'attente, en secondes.
	 */
	void setTTLInSecond(int inTTL);
}
