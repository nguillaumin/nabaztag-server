package net.violet.platform.datamodel;

import net.violet.db.records.associations.AssoRecord;

public interface Subscriber extends AssoRecord<Nabcast, Subscriber> {

	long getSubscriber_user();

	long getSubscriber_nabcast();

	int getSubscriber_heure();

	VObject getVObjectSubscriber();

	void setTime(int heure, int min);

	int getSubscriber_min();

}
