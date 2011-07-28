package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Notification;
import net.violet.platform.datamodel.NotificationImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Notification.NOTIFICATION_STATUS;
import net.violet.platform.datamodel.factories.NotificationFactory;

import org.apache.log4j.Logger;

public class NotificationFactoryImpl extends RecordFactoryImpl<Notification, NotificationImpl> implements NotificationFactory {

	private static final Logger LOGGER = Logger.getLogger(NotificationFactoryImpl.class);

	public NotificationFactoryImpl() {
		super(NotificationImpl.SPECIFICATION);
	}

	public long countReceived(VObject inRecipient) {
		return count(null, " recipient_id = ? ", Collections.singletonList((Object) inRecipient.getId()), null);
	}

	public long countSent(VObject inSender) {
		return count(null, " sender_id = ? and status = ? ", Arrays.asList(new Object[] { inSender.getId(), NOTIFICATION_STATUS.PENDING.toString() }), null);
	}

	public Notification create(VObject inSender, VObject inRecipient, Application inApplication, NOTIFICATION_STATUS inStatus) {
		try {
			return new NotificationImpl(inSender, inRecipient, inApplication, inStatus);
		} catch (final SQLException e) {
			NotificationFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public List<Notification> findAllReceived(VObject inRecipient, int inSkip, int inGetCount) {
		return findAll(" recipient_id = ? ", Collections.singletonList((Object) inRecipient.getId()), " creation_time ", inSkip, inGetCount);
	}

	public List<Notification> findAllSent(VObject inSender, int inSkip, int inGetCount) {
		return findAll(" sender_id = ? and status = ? ", Arrays.asList(new Object[] { inSender.getId(), NOTIFICATION_STATUS.PENDING.toString() }), " creation_time ", inSkip, inGetCount);
	}

	public Notification findPending(VObject inSender, Application inApplication) {
		return find(" sender_id = ? and application_id = ? and status = ? ", Arrays.asList(new Object[] { inSender.getId(), inApplication.getId(), NOTIFICATION_STATUS.PENDING.toString() }));
	}

}
