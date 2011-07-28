package net.violet.platform.message;

import net.violet.platform.applications.WeatherHandler;
import net.violet.platform.datamodel.ObjectSleep;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;

/**
 * MessageImpl pour un destinataire.
 */
public abstract class AddressedMessage extends AbstractMessage {

	/**
	 * Référence sur l'objet.
	 */
	private final VObject mReceiver;

	public AddressedMessage(VObject inReceiver) {
		this.mReceiver = inReceiver;
	}

	/**
	 * Récupère les sources dans la base.
	 */
	public final void loadSourcesFromDatabase() {

		for (final SubscriptionData aSubscription : SubscriptionData.findByObjectAndSchedulingType(this.mReceiver, SchedulingType.SCHEDULING_TYPE.Ambiant)) {
			final Object theSourceSetting = aSubscription.getSettings().get(WeatherHandler.SOURCE);
			if (theSourceSetting != null) {
				final Source theSource = Factories.SOURCE.findByPath(theSourceSetting.toString());
				if (theSource != null) {
					final int newsrvval = (int) theSource.getSource_val();
					final Message.SOURCE theMessageSource = Message.SOURCE.findById((int) aSubscription.getApplication().getReference().getTemp().getType());
					setSourceValue(theMessageSource, newsrvval);
				}
			}

		}
	}

	/**
	 * Calcule l'état (veille ou actif).
	 */
	public final void setStatusFromObjectRecord() {
		final int theState = this.mReceiver.getObject_state();
		final int theMode;
		switch (theState) {
		case VObject.STATUS_ACTIF:
		case VObject.STATUS_FORCE_ACTIF:
		case VObject.STATUS_WILLBE_ACTIF:
		case VObject.STATUS_WILLBE_FORCE_ACTIF:
			theMode = Message.MODE.ACTIF.getId();
			break;

		case VObject.STATUS_VEILLE:
		case VObject.STATUS_FORCE_VEILLE:
		case VObject.STATUS_WILLBE_VEILLE:
		case VObject.STATUS_WILLBE_FORCE_VEILLE:
			theMode = Message.MODE.VEILLE.getId();
			break;

		default:
			final boolean isSleeping = ObjectSleep.ObjectSleepCommon.asleep(this.mReceiver);
			theMode = isSleeping ? Message.MODE.VEILLE.getId() : Message.MODE.ACTIF.getId();
		}
		setStatus(theMode);
	}

	/**
	 * Accesseur sur le destinataire.
	 */
	@Override
	public final VObject getReceiver() {
		return this.mReceiver;
	}
}
