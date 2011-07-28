package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.NabcastImpl;
import net.violet.platform.datamodel.Subscriber;
import net.violet.platform.datamodel.SubscriberImpl;
import net.violet.platform.datamodel.VObject;

public final class SubscriberData extends RecordData<Subscriber> {

	private SubscriberData(Subscriber inSubscriber) {
		super(inSubscriber);
	}

	/**
	 * Finds all the subscribers to a nabcast from the given {@link Nabcast} id
	 * 
	 * @param inId
	 * @return a list of {@link SubscriberData}
	 */
	public static List<SubscriberData> findBySubscriberId(long inId) {
		return SubscriberData.findBySubscriberId(NabcastImpl.find(inId));
	}

	/**
	 * Finds all the subscribers to a nabcast from the given {@link Nabcast}
	 * 
	 * @param inId
	 * @return a list of {@link SubscriberData}
	 */
	public static List<SubscriberData> findBySubscriberId(net.violet.platform.datamodel.Nabcast inNabcast) {
		if (inNabcast != null) {
			return SubscriberData.generateList(SubscriberImpl.findByNabcast(inNabcast));
		}
		return Collections.emptyList();
	}

	/**
	 * Generates a list of SubscribeData with the given SubscriberImpl list
	 * 
	 * @param inSubscribers Subscribers list
	 * @return
	 */
	private static List<SubscriberData> generateList(List<Subscriber> inSubscribers) {
		final List<SubscriberData> SubscribeDataList = new ArrayList<SubscriberData>();

		for (final Subscriber tempSubscriber : inSubscribers) {
			SubscribeDataList.add(new SubscriberData(tempSubscriber));
		}

		return SubscribeDataList;
	}

	/**
	 * @return the subscriber_use attribute
	 */
	public long getSubscriber_user() {
		final Subscriber theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getSubscriber_user();
		}
		return 0;
	}

	/**
	 * @return the subscriber_nabcast attribute
	 */
	public long getSubscriber_nabcast() {
		final Subscriber theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getSubscriber_nabcast();
		}
		return 0;
	}

	/**
	 * @return the subscriber_heure attribute
	 */
	public int getSubscriber_heure() {
		final Subscriber theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getSubscriber_heure();
		}
		return 0;
	}

	/**
	 * @return the subscriber_min attribute
	 */
	public int getSubscriber_min() {
		final Subscriber theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getSubscriber_min();
		}
		return 0;
	}

	public VObject getSubscriber_VObject() {
		final Subscriber theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getVObjectSubscriber();
		}
		return null;
	}
}
