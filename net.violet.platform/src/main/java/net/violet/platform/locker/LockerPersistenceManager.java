package net.violet.platform.locker;

/**
 * Definition of the trivial contract to make our locker's content persistent
 * @author christophe - Violet
 */
public interface LockerPersistenceManager {

	LockerEntry get(String inKey);

	void update(String inKey, LockerEntry inValue);

	LockerEntry remove(String inKey);

}
