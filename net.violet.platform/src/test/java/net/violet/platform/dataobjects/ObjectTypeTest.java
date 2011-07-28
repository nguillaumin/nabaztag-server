package net.violet.platform.dataobjects;

import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ConfigFilesMock;

import org.junit.Test;

public class ObjectTypeTest extends MockTestBase {

	@Test
	public void findByNameTest() {
		final String name = "violet.nabaztag.tagtag";
		final ObjectType theType = ObjectType.findByName(name);

		Assert.assertEquals(ObjectType.NABAZTAG_V2, theType);
	}

	@Test
	public void getChildsTest() {
		Assert.assertTrue(ObjectType.NABAZTAG_V1.getChilds().isEmpty());
		Assert.assertTrue(ObjectType.NABAZTAG_V2.getChilds().isEmpty());
		Assert.assertTrue(ObjectType.MIRROR.getChilds().isEmpty());
		Assert.assertTrue(ObjectType.RFID_ZTAMP.getChilds().isEmpty());

		final Set<ObjectType> nabChilds = ObjectType.NABAZTAG.getChilds();
		Assert.assertEquals(2, nabChilds.size());
		Assert.assertTrue(nabChilds.contains(ObjectType.NABAZTAG_V1) && nabChilds.contains(ObjectType.NABAZTAG_V2));

		final Set<ObjectType> rfidChilds = ObjectType.RFID.getChilds();
		Assert.assertEquals(4, rfidChilds.size());
		Assert.assertTrue(rfidChilds.contains(ObjectType.RFID_BOOK) && rfidChilds.contains(ObjectType.RFID_NANOZTAG) && rfidChilds.contains(ObjectType.RFID_OTHER) && rfidChilds.contains(ObjectType.RFID_ZTAMP));

		final Set<ObjectType> allChilds = ObjectType.ALL.getChilds();
		Assert.assertEquals(4, allChilds.size());
		Assert.assertTrue(allChilds.contains(ObjectType.NABAZTAG) && allChilds.contains(ObjectType.RFID) && allChilds.contains(ObjectType.MIRROR) && allChilds.contains(ObjectType.DALDAL));
	}

	@Test
	public void isPrimaryTest() {
		Assert.assertTrue(ObjectType.NABAZTAG_V1.isPrimaryObject());
		Assert.assertFalse(ObjectType.NABAZTAG.isPrimaryObject());
	}

	@Test
	public void getAllChilds() {
		final Set<ObjectType> v1Primary = ObjectType.NABAZTAG_V1.getAllChilds();
		Assert.assertEquals(0, v1Primary.size());

		final Set<ObjectType> nabaztagPrimaries = ObjectType.NABAZTAG.getAllChilds();
		Assert.assertEquals(2, nabaztagPrimaries.size());
		Assert.assertTrue(nabaztagPrimaries.contains(ObjectType.NABAZTAG_V1) && nabaztagPrimaries.contains(ObjectType.NABAZTAG_V2));

		final Set<ObjectType> all = ObjectType.ALL.getAllChilds();
		Assert.assertEquals(10, all.size());
	}

	@Test
	public void getHardwaresTest() {
		final Set<Hardware.HARDWARE> nabHardwares = ObjectType.NABAZTAG.getHardwares();
		Assert.assertEquals(2, nabHardwares.size());
		Assert.assertTrue(nabHardwares.contains(Hardware.HARDWARE.V2) && nabHardwares.contains(Hardware.HARDWARE.V1));

		final Set<Hardware.HARDWARE> allHardwares = ObjectType.ALL.getHardwares();
		Assert.assertEquals(8, allHardwares.size());

		final Set<Hardware.HARDWARE> mirrorHW = ObjectType.MIRROR.getHardwares();
		Assert.assertEquals(1, mirrorHW.size());
		Assert.assertTrue(mirrorHW.contains(Hardware.HARDWARE.MIRROR));

	}

	@Test
	public void instanceOfTest() {
		Assert.assertTrue(ObjectType.RFID.instanceOf(ObjectType.ALL));
		Assert.assertTrue(ObjectType.NABAZTAG_V1.instanceOf(ObjectType.ALL));
		Assert.assertTrue(ObjectType.NABAZTAG_V2.instanceOf(ObjectType.NABAZTAG));
		Assert.assertTrue(ObjectType.MIRROR.instanceOf(ObjectType.ALL));
		Assert.assertTrue(ObjectType.RFID_BOOK.instanceOf(ObjectType.RFID));
		Assert.assertTrue(ObjectType.RFID_BOOK.instanceOf(ObjectType.RFID_BOOK));
		Assert.assertTrue(ObjectType.RFID.instanceOf(ObjectType.RFID));
		Assert.assertFalse(ObjectType.RFID_BOOK.instanceOf(ObjectType.NABAZTAG));
		Assert.assertFalse(ObjectType.MIRROR.instanceOf(ObjectType.DALDAL));
	}

	@Test
	public void isValidSerialTest() {
		Assert.assertTrue(ObjectType.NABAZTAG_V2.isValidSerial(getKowalskyObject().getObject_serial()));
		Assert.assertTrue(ObjectType.NABAZTAG.isValidSerial(getKowalskyObject().getObject_serial()));
		Assert.assertFalse(ObjectType.MIRROR.isValidSerial(getKowalskyObject().getObject_serial()));
		Assert.assertFalse(ObjectType.ALL.isValidSerial(getKowalskyObject().getObject_serial()));
	}

	@Test
	public void getObjectPictureTest() {
		final Files V1_1 = Factories.FILES.createFile("v1/image/1.jpg", MimeType.MIME_TYPES.JPEG);
		final Files V1_2 = Factories.FILES.createFile("v1/image/2.jpg", MimeType.MIME_TYPES.JPEG);
		final Files V2_1 = Factories.FILES.createFile("v2/image/1.jpg", MimeType.MIME_TYPES.JPEG);
		final Files V2_2 = Factories.FILES.createFile("v2/image/2.jpg", MimeType.MIME_TYPES.JPEG);
		final Files mirror_1 = Factories.FILES.createFile("mirror/image/1.jpg", MimeType.MIME_TYPES.JPEG);
		final Files mirror_2 = Factories.FILES.createFile("mirror/image/2.jpg", MimeType.MIME_TYPES.JPEG);

		new ConfigFilesMock(V1_1, HARDWARE.V1.getId().toString());
		new ConfigFilesMock(V1_2, HARDWARE.V1.getId().toString());
		new ConfigFilesMock(V2_1, HARDWARE.V2.getId().toString());
		new ConfigFilesMock(V2_2, HARDWARE.V2.getId().toString());
		new ConfigFilesMock(mirror_1, HARDWARE.MIRROR.getId().toString());
		new ConfigFilesMock(mirror_2, HARDWARE.MIRROR.getId().toString());

		List<FilesData> theFiles = ObjectType.NABAZTAG_V1.getDefaultPictures();
		Assert.assertEquals(2, theFiles.size());
		Assert.assertTrue(theFiles.contains(FilesData.getData(V1_1)) && theFiles.contains(FilesData.getData(V1_2)));

		theFiles = ObjectType.NABAZTAG_V2.getDefaultPictures();
		Assert.assertEquals(2, theFiles.size());
		Assert.assertTrue(theFiles.contains(FilesData.getData(V2_1)) && theFiles.contains(FilesData.getData(V2_2)));

		theFiles = ObjectType.MIRROR.getDefaultPictures();
		Assert.assertEquals(2, theFiles.size());
		Assert.assertTrue(theFiles.contains(FilesData.getData(mirror_1)) && theFiles.contains(FilesData.getData(mirror_2)));

		theFiles = ObjectType.NABAZTAG.getDefaultPictures();
		Assert.assertEquals(4, theFiles.size());
		Assert.assertTrue(theFiles.contains(FilesData.getData(V1_1)) && theFiles.contains(FilesData.getData(V1_2)) && theFiles.contains(FilesData.getData(V2_1)) && theFiles.contains(FilesData.getData(V2_2)));

	}
}
