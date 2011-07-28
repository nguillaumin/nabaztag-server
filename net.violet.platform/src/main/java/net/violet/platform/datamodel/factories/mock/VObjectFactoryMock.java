package net.violet.platform.datamodel.factories.mock;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.ObjectPreferences;
import net.violet.platform.datamodel.ObjectProfile;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.VObjectFactory;
import net.violet.platform.datamodel.mock.ObjectPreferencesMock;
import net.violet.platform.datamodel.mock.ObjectProfileMock;
import net.violet.platform.datamodel.mock.VObjectMock;

public class VObjectFactoryMock extends RecordFactoryMock<VObject, VObjectMock> implements VObjectFactory {

	VObjectFactoryMock() {
		super(VObjectMock.class);
	}

	public VObject findByName(String inName) {
		VObject theResult = null;
		for (final VObject theObject : findAllMapped().values()) {
			if (inName.equals(theObject.getObject_login())) {
				theResult = theObject;
				break;
			}
		}
		return theResult;
	}

	public List<VObject> findByOwner(User inUser) {
		final List<VObject> theResult = new LinkedList<VObject>();

		for (final VObject theObject : findAllMapped().values()) {
			if (inUser.equals(theObject.getOwner())) {
				theResult.add(theObject);
			}
		}
		return theResult;
	}

	public VObject findByOwnerAndHardware(User inUser, HARDWARE inHardware) {
		VObject theResult = null;
		for (final VObject theObject : findAllMapped().values()) {
			if (inUser.equals(theObject.getOwner()) && inHardware.is(theObject)) {
				theResult = theObject;
				break;
			}
		}
		return theResult;
	}

	public VObject findRandomObject() {
		throw new UnsupportedOperationException();
	}

	public VObject findByJID(String inJID) {
		throw new UnsupportedOperationException();
	}

	public VObject findBySerial(String inSerial) {
		VObject theResult = null;
		for (final VObject theObject : findAllMapped().values()) {
			if (inSerial.equals(theObject.getObject_serial())) {
				theResult = theObject;
				break;
			}
		}
		return theResult;
	}

	public List<VObject> findLastCreatedByHardwares(int count, Set<HARDWARE> hardwares) {
		final List<VObject> theResult = new LinkedList<VObject>();

		final List<VObject> tmp = new LinkedList<VObject>(findAllMapped().values());
		Collections.sort(tmp, new Comparator<VObject>() {

			public int compare(VObject o1, VObject o2) {
				return (int) (o2.getObject_creation() - o1.getObject_creation());
			}
		});

		for (final VObject object : tmp) {
			if (hardwares.contains(object.getHardware())) {
				theResult.add(object);
			}
		}

		if (theResult.size() <= count) {
			return theResult;
		}

		return theResult.subList(0, count);
	}

	public List<VObject> searchObjects(String inName, Set<HARDWARE> inHardwares, String inCity, String inCountry, int skip, int count) {
		final SortedSet<VObject> theResult = new TreeSet<VObject>(new Comparator<VObject>() {

			public int compare(VObject o1, VObject o2) {
				return o1.getProfile().getLabel().compareTo(o2.getProfile().getLabel());
			}
		});

		for (final VObject anObject : findAll()) {
			if (!anObject.getPreferences().isVisible()) {
				continue;
			}

			if ((inName != null) && !anObject.getProfile().getLabel().startsWith(inName)) {
				continue;
			}

			if ((inHardwares != null) && !inHardwares.contains(anObject.getHardware())) {
				continue;
			}

			if ((inCity != null) && !anObject.getOwner().getAnnu().getAnnu_city().equals(inCity)) {
				continue;
			}

			if ((inCountry != null) && !anObject.getOwner().getAnnu().getAnnu_country().equals(inCountry)) {
				continue;
			}

			theResult.add(anObject);
		}

		return getSkipList(new LinkedList<VObject>(theResult), skip, count);
	}

	public ObjectProfile createObjectProfile(VObject inObject) {
		return new ObjectProfileMock(inObject, net.violet.common.StringShop.EMPTY_STRING, null);
	}

	public ObjectPreferences createObjectPreferences(VObject inObject, Lang inLang) {
		return new ObjectPreferencesMock(inObject, true, false, inLang);
	}

	public ObjectPreferences createObjectPreferences(VObject inObject, Lang inLang, boolean inVisibility) {
		return new ObjectPreferencesMock(inObject, inVisibility, false, inLang);
	}

	public VObject findByNameAndMD5Password(String inName, String inPassword) {
		return null;
	}

	public VObject createObject(HARDWARE inHardware, String inSerial, String inName, String inLabel, User inUser, String inLocation, int inMode, int inState, Files inPicture) {
		final VObject theObject = new VObjectMock(0L, inSerial, inName, inUser, inHardware, inUser.getTimezone(), inUser.getAnnu().getLangPreferences(), System.currentTimeMillis(), inPicture);
		theObject.getProfile().setLabel(inLabel);
		return theObject;
	}

	public int walkObjectsStatus(RecordWalker<VObject> inWalker, Timezone inTimezone, boolean postStateActif) {
		throw new UnsupportedOperationException();
	}

}
