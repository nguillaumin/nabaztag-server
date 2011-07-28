package net.violet.platform.dataobjects;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.factories.Factories;

public enum ObjectType {

	NABAZTAG_V1("violet.nabaztag.tag", VObject.MODE_PING, VObject.STATUS_ACTIF, HARDWARE.V1),
	NABAZTAG_V2("violet.nabaztag.tagtag", VObject.MODE_XMPP, VObject.STATUS_ACTIF, HARDWARE.V2),
	RFID_BOOK("violet.rfid.book", VObject.MODE_PING_INACTIVE, VObject.STATUS_NORMAL, HARDWARE.BOOK),
	RFID_ZTAMP("violet.rfid.ztamp", VObject.MODE_PING_INACTIVE, VObject.STATUS_NORMAL, HARDWARE.ZTAMP),
	RFID_NANOZTAG("violet.rfid.nanoztag", VObject.MODE_PING_INACTIVE, VObject.STATUS_NORMAL, HARDWARE.NANOZTAG),
	RFID_OTHER("violet.rfid.other", VObject.MODE_PING_INACTIVE, VObject.STATUS_NORMAL, HARDWARE.OTHER_RFID),
	MIRROR("violet.mirror", VObject.MODE_XMPP, VObject.STATUS_ACTIF, HARDWARE.MIRROR),
	DALDAL("violet.daldal", VObject.MODE_PING, VObject.STATUS_ACTIF, HARDWARE.DALDAL),

	NABAZTAG("violet.nabaztag", new ObjectType[] { NABAZTAG_V1, NABAZTAG_V2 }),
	RFID("violet.rfid", new ObjectType[] { RFID_BOOK, RFID_ZTAMP, RFID_NANOZTAG, RFID_OTHER }),
	ALL("violet", new ObjectType[] { NABAZTAG, RFID, MIRROR, DALDAL });

	//These maps only contain the primary hardwares
	private static final Map<Hardware.HARDWARE, ObjectType> HARDWARE_OBJECT_TYPE;
	private static final Map<ObjectType, Hardware.HARDWARE> OBJECT_TYPE_HARDWARE;

	static {
		final Map<Hardware.HARDWARE, ObjectType> theFirstMap = new HashMap<HARDWARE, ObjectType>();
		final Map<ObjectType, Hardware.HARDWARE> theSecondMap = new HashMap<ObjectType, Hardware.HARDWARE>();

		for (final ObjectType aType : ObjectType.values()) {
			if (aType.mAllChildren.isEmpty()) { // is Primary
				final HARDWARE theHardware = aType.mHardwares.iterator().next();
				theFirstMap.put(theHardware, aType);
				theSecondMap.put(aType, theHardware);
			}
		}

		HARDWARE_OBJECT_TYPE = Collections.unmodifiableMap(theFirstMap);
		OBJECT_TYPE_HARDWARE = Collections.unmodifiableMap(theSecondMap);
	}

	private static Set<ObjectType> getFlatChilds(ObjectType inType) {
		final Set<ObjectType> theChilds = new HashSet<ObjectType>();

		if (!inType.mAllChildren.isEmpty()) { // is not primary
			for (final ObjectType aChild : inType.getChilds()) {
				theChilds.addAll(ObjectType.getFlatChilds(aChild));
			}
		} else {
			theChilds.add(inType);
		}

		return theChilds;
	}

	public static ObjectType findByName(String inName) {

		for (final ObjectType aType : ObjectType.values()) {
			if (aType.getTypeName().equals(inName)) {
				return aType;
			}
		}

		return null;
	}

	static ObjectType findByHardware(HARDWARE inHardware) {
		return ObjectType.HARDWARE_OBJECT_TYPE.get(inHardware);
	}

	private final String mTypeName;

	/** Only the direct children */
	private final Set<ObjectType> mDirectChildren = new HashSet<ObjectType>();

	/** All the children, i.e. the non-primaries and the primaries children*/
	private final Set<ObjectType> mAllChildren = new HashSet<ObjectType>();
	private final Set<Hardware.HARDWARE> mHardwares = new HashSet<Hardware.HARDWARE>();
	private final int mMode;
	private final int mState;

	private ObjectType(String inTypeName, ObjectType[] childsHardware) {
		this(inTypeName, 0, 0, null);

		for (final ObjectType anObjectType : childsHardware) {
			this.mDirectChildren.add(anObjectType);
			this.mAllChildren.addAll(ObjectType.getFlatChilds(anObjectType));
			this.mAllChildren.add(anObjectType);
		}

		for (final ObjectType aType : getAllChilds()) {
			this.mHardwares.addAll(aType.mHardwares);
		}
	}

	private ObjectType(String inTypeName, int inMode, int inState, HARDWARE inPrimaryHardware) {
		this.mTypeName = inTypeName;
		if (inPrimaryHardware != null) {
			this.mHardwares.add(inPrimaryHardware);
		}
		this.mMode = inMode;
		this.mState = inState;
	}

	public String getTypeName() {
		return this.mTypeName;
	}

	public Set<ObjectType> getChilds() {
		return Collections.unmodifiableSet(this.mDirectChildren);
	}

	Set<ObjectType> getAllChilds() {
		return Collections.unmodifiableSet(this.mAllChildren);
	}

	public boolean isPrimaryObject() {
		return ObjectType.OBJECT_TYPE_HARDWARE.containsKey(this);
	}

	public int getMode() {
		return this.mMode;
	}

	public int getState() {
		return this.mState;
	}

	HARDWARE getPrimaryHardware() {
		return ObjectType.OBJECT_TYPE_HARDWARE.get(this);
	}

	Set<HARDWARE> getHardwares() {
		return this.mHardwares;
	}

	public boolean instanceOf(ObjectType inType) {
		return (inType != null) && ((inType.isPrimaryObject() && ((inType == this))) || inType.mAllChildren.contains(this) || (inType == this));
	}

	public boolean isValidSerial(String serialNumber) {
		if ((getPrimaryHardware() != null)) {
			return this.getPrimaryHardware().checkIdentifier(serialNumber);
		}

		for (final ObjectType aType : this.mDirectChildren) {
			if (!aType.isValidSerial(serialNumber)) {
				return false;
			}
		}
		return true;
	}

	public boolean isMimeTypeSupported(MimeType.MIME_TYPES inType) {
		return (inType != null) && this.getPrimaryHardware().isMimeTypeSupported(inType);
	}

	/**
	 * Returns a list of FilesData, each of them being an available default picture for the objectType.
	 * If the objectType is a composed type (i.e. not a primary type) the returned list contains the sum of available
	 * pictures for all the type's children (e.g. if the method is called on NABAZTAG then the list contains the 
	 * TAG and TAG/TAG pictures).
	 * The list is never null but can be empty. It does not contain any duplicate.
	 * @return a list of FilesData, it can be empty.
	 */
	public List<FilesData> getDefaultPictures() {
		final Set<FilesData> thePictures = new HashSet<FilesData>();
		if (this.isPrimaryObject()) {
			for (final ConfigFiles aConfigFiles : Factories.CONFIG_FILES.findDefaultPicturesByHardware(this.getPrimaryHardware())) {
				thePictures.add(FilesData.getData(aConfigFiles.getFiles()));
			}
		} else {
			for (final ObjectType aSubType : getChilds()) {
				thePictures.addAll(aSubType.getDefaultPictures());
			}
		}

		return new LinkedList<FilesData>(thePictures);
	}

	public List<MIME_TYPES> getMimeTypes() {
		final Hardware.HARDWARE theHardware = getPrimaryHardware();
		if (theHardware != null) {
			return theHardware.getMimeTypes();
		}
		return Collections.emptyList();
	}
}
