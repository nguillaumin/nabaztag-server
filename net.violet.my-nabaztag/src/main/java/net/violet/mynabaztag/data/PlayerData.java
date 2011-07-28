package net.violet.mynabaztag.data;

import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Ztamp;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.interactif.config.PlayerConfig;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.StringShop;

/**
 * data qui représente tous les réglages d'un utilisateur pour un bouquin
 * (ztamp) donné.
 */
public class PlayerData {

	/**
	 * l'appletSettings correspond a une row de cle markup. Pour cette row :
	 * primaryObject = le livre, secondaryObject = le lapin
	 */
	private final AppletSettings theAppletRef;
	private final PlayerConfig theConfig;

	public PlayerData(AppletSettings inApplet, PlayerConfig inConfig) {
		this.theAppletRef = (inApplet == null) ? null : inApplet;
		this.theConfig = inConfig;
	}

	public long getObjectRabbitId() {
		if (this.theAppletRef != null && (this.theAppletRef.getSecondaryObject() != null)) {
			return this.theAppletRef.getSecondaryObject().getId();
		}

		return 0;
	}

	public long getZtampId() {
		if (this.theAppletRef != null && (this.theAppletRef.getPrimaryAppletSettingsObject() != null)) {
			return this.theAppletRef.getPrimaryAppletSettingsObject().getId();
		}

		return 0;
	}

	public String getZtampSerial() {
		if (this.theAppletRef != null && (this.theAppletRef.getPrimaryAppletSettingsObject() != null) && (this.theAppletRef.getPrimaryAppletSettingsObject().getObject_serial() != null)) {
			return this.theAppletRef.getPrimaryAppletSettingsObject().getObject_serial();
		}
		return StringShop.EMPTY_STRING;
	}

	public long getMarkUpIndex() {
		if (this.theAppletRef != null) {
			return ConvertTools.atol(this.theAppletRef.getValue());
		}

		return 0;
	}

	public long getVoiceId() {
		int voiceValue = -1;
		if (this.theAppletRef != null) {
			final VObject theBook = this.theAppletRef.getPrimaryAppletSettingsObject(); // Factories . VOBJECT . find ( theAppletRef .
			// getPrimary_object_id
			// (
			// )
			// )
			// ;
			final VObject theRabbit = this.theAppletRef.getSecondaryObject(); // Factories . VOBJECT . find ( theAppletRef .
			// getSecondary_object_id
			// (
			// )
			// )
			// ;
			if ((theBook != null) && (theRabbit != null)) {

				final AppletSettings theVoice = Factories.APPLET_SETTINGS.getAppletSettingsByObjects(theBook, theRabbit, this.theAppletRef.getApplet_id(), this.theConfig.getVoice());
				if (theVoice != null) {
					voiceValue = ConvertTools.atoi(theVoice.getValue());// recupère la voix
				}
			}
		}
		return voiceValue;
	}

	/**
	 * nom du dernier objet qui l'a lu
	 * 
	 * @return nom
	 */
	public String getNameObjectLastRead() {
		String login = StringShop.EMPTY_STRING;
		if (this.theAppletRef != null) {
			final VObject theBook = this.theAppletRef.getPrimaryAppletSettingsObject(); // Factories . VOBJECT . find ( theAppletRef .
			// getSecondary_object_id
			// (
			// )
			// )
			// ;
			if (theBook != null) {
				final AppletSettings theAppletBook = Factories.APPLET_SETTINGS.getAppletSettingsByObject(theBook, this.theAppletRef.getApplet_id(), this.theConfig.getVoice());
				if (theAppletBook != null) {
					// final long idObjet =
					// theAppletBook.getSecondary_object_id(); //recupère le dernier objet qui a lu ce bouquin VObjectImpl theObject = Factories.VOBJECT.find(idObjet);
					final VObject theRabbit = theAppletBook.getSecondaryObject();
					if (theRabbit != null) {
						login = theRabbit.getObject_login();
					}
				}
			}
		}
		return login;
	}

	/**
	 * id du user qui l'a lu le livre avec son objet (permet de voir le profil
	 * de la personne)
	 * 
	 * @return id
	 */
	public long getIdUserLastRead() {
		long id = 0;
		if (this.theAppletRef != null) {
			final VObject theBook = this.theAppletRef.getPrimaryAppletSettingsObject(); // Factories . VOBJECT . find ( theAppletRef .
			// getSecondary_object_id
			// (
			// )
			// )
			// ;
			if (theBook != null) {
				final AppletSettings theAppletBook = Factories.APPLET_SETTINGS.getAppletSettingsByObject(theBook, this.theAppletRef.getApplet_id(), this.theConfig.getVoice());
				if (theAppletBook != null) {
					// final long idObjet =
					// theAppletBook.getSecondary_object_id(); //recupère le dernier objet qui a lu ce bouquin VObjectImpl theObject = Factories.VOBJECT.find(idObjet);
					final VObject theRabbit = theAppletBook.getSecondaryObject();
					if (theRabbit != null) {
						id = theRabbit.getOwner().getId();
					}
				}
			}
		}
		return id;
	}

	/**
	 * envoi l'id du visuel du rfid
	 * 
	 * @return id
	 */
	public long getPictureObject() {
		long id = -1;
		if (this.theAppletRef != null) {
			final VObject theBook = this.theAppletRef.getPrimaryAppletSettingsObject();
			if (theBook != null) {
				final Ztamp theZtamp = Factories.ZTAMP.findBySerial(theBook.getObject_serial());
				if (theZtamp != null) {
					id = theZtamp.getVisual().getId();
				}
			}
		}
		return id;
	}
}
