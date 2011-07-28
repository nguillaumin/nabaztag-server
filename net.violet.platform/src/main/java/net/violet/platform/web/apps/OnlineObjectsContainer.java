package net.violet.platform.web.apps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

public class OnlineObjectsContainer {

	private static final long EXPIRATION_TIME = 3600000; // 1 hour in millis
	private static final long RECENT_TIMEOUT = 30000;

	private final Comparator<OnlineObject> COMPARATOR = new Comparator<OnlineObject>() {

		//Returns a negative integer if o1 is newer than o2 (i.e. if o1 has been seen after o2)
		public int compare(OnlineObject o1, OnlineObject o2) {
			if (o1.equals(o2)) { // if it's the same object, they are equal
				return 0;
			}
			return o2.getSeenAt().compareTo(o1.getSeenAt());
		}
	};

	private final Timer timeoutTimer;
	private final Timer recentTimer;

	private final SortedSet<OnlineObject> onlineObjects = new TreeSet<OnlineObject>(this.COMPARATOR); // all the online objects
	private final SortedSet<OnlineObject> recentlyAddedObjects = new TreeSet<OnlineObject>(this.COMPARATOR); // recently connected objects 
	private final SortedSet<OnlineObject> recentlyRemovedObjects = new TreeSet<OnlineObject>(this.COMPARATOR); // recently disconnected objects

	public OnlineObjectsContainer() {
		this.timeoutTimer = new Timer(true);
		this.timeoutTimer.schedule(new TimerTask() {

			/**
			 * In this task, we evaluate all the online objects, if the ttl has been crossed : the object is removed.
			 */
			@Override
			public void run() {
				synchronized (OnlineObjectsContainer.this.onlineObjects) {
					final Iterator<OnlineObject> iterator = OnlineObjectsContainer.this.onlineObjects.iterator();
					while (iterator.hasNext()) {
						final OnlineObject anObject = iterator.next();
						if (anObject.getSeenAt().getTime() + OnlineObjectsContainer.EXPIRATION_TIME < System.currentTimeMillis()) {
							OnlineObjectsContainer.this.recentlyRemovedObjects.add(anObject);
							OnlineObjectsContainer.this.recentlyAddedObjects.remove(anObject);
							iterator.remove();
						}
					}
				}
			}

		}, OnlineObjectsContainer.EXPIRATION_TIME, OnlineObjectsContainer.EXPIRATION_TIME);

		this.recentTimer = new Timer(true);
		this.recentTimer.schedule(new TimerTask() {

			/**
			 * The 'recent' lists are refresh after a short timeout.
			 */
			@Override
			public void run() {
				synchronized (OnlineObjectsContainer.this.onlineObjects) {
					Iterator<OnlineObject> iterator = OnlineObjectsContainer.this.recentlyAddedObjects.iterator();
					while (iterator.hasNext()) {
						final OnlineObject anObject = iterator.next();
						if (anObject.getSeenAt().getTime() + OnlineObjectsContainer.RECENT_TIMEOUT < System.currentTimeMillis()) {
							iterator.remove();
						}
					}

					iterator = OnlineObjectsContainer.this.recentlyRemovedObjects.iterator();
					while (iterator.hasNext()) {
						final OnlineObject anObject = iterator.next();
						if (anObject.getSeenAt().getTime() + OnlineObjectsContainer.RECENT_TIMEOUT < System.currentTimeMillis()) {
							iterator.remove();
						}
					}
				}
			}

		}, OnlineObjectsContainer.RECENT_TIMEOUT, OnlineObjectsContainer.RECENT_TIMEOUT);

	}

	/**
	 * Returns the amount of connected objects.
	 * @return
	 */
	public long getAmount() {
		synchronized (this.onlineObjects) {
			return this.onlineObjects.size();
		}
	}

	/**
	 * Adds an object.
	 * @param onlineObject
	 */
	public void addObject(OnlineObject onlineObject) {
		synchronized (this.onlineObjects) {
			this.onlineObjects.remove(onlineObject);
			this.onlineObjects.add(onlineObject);

			this.recentlyAddedObjects.remove(onlineObject);
			this.recentlyAddedObjects.add(onlineObject);

			this.recentlyRemovedObjects.remove(onlineObject); // no longer considered as removed
		}
	}

	/**
	 * Removes an object
	 * @param onlineObject
	 */
	public void removeObject(OnlineObject onlineObject) {
		synchronized (this.onlineObjects) {
			this.onlineObjects.remove(onlineObject); // if the object has been removed (it was in the list)
			this.recentlyRemovedObjects.add(onlineObject);
			this.recentlyAddedObjects.remove(onlineObject); // no longer considered as newly added
		}
	}

	/**
	 * Returns a list of objects which recently left the network.
	 * @return
	 */
	public List<OnlineObject> getOfflineObjects() {
		synchronized (this.onlineObjects) {
			return new ArrayList<OnlineObject>(this.recentlyRemovedObjects);
		}
	}

	/**
	 * Returns a list of objects which recently joined the network.
	 * @return
	 */
	public List<OnlineObject> getNewObjects() {
		synchronized (this.onlineObjects) {
			return new ArrayList<OnlineObject>(this.recentlyAddedObjects);
		}
	}

	/**
	 * Returns a list of all the connected objects.
	 * @return
	 */
	public List<OnlineObject> getOnlineObjects() {
		synchronized (this.onlineObjects) {
			return getOnlineObjects(this.onlineObjects.size());
		}
	}

	/**
	 * Returns a list of the most recent connected objects. The list's size won't be greater than the given max size.
	 * @param maxSize
	 * @return
	 */
	public List<OnlineObject> getOnlineObjects(int maxSize) {
		synchronized (this.onlineObjects) {
			final List<OnlineObject> theList = new ArrayList<OnlineObject>(this.onlineObjects);
			return theList.subList(0, Math.min(maxSize, theList.size()));
		}
	}

}
