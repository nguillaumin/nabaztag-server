package net.violet.platform.object;

import java.util.List;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.SubscriptionScheduling;
import net.violet.platform.datamodel.SubscriptionSchedulingSettings;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.DicoMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.ZtampBatchMock;
import net.violet.platform.datamodel.mock.ZtampMock;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.events.ZtampDetectionEvent;
import net.violet.platform.schedulers.InteractionTriggerHandler;

import org.junit.Assert;
import org.junit.Test;

public class ProvisionningTest extends MockTestBase {

	@Test
	public void VioletZtampProvisionning() {
		final String serial = "d00218c1090b58c7";
		final String prefix = "Violet";
		final ZtampBatchMock theZtampBatch = new ZtampBatchMock(0, getMyFirstApplication(), "", prefix);
		final FilesMock thePicture = new FilesMock(0, null, MimeType.MIME_TYPES.JPEG, "nano.jpg");
		new ZtampMock(0, serial, Hardware.HARDWARE.NANOZTAG, theZtampBatch, thePicture);
		final VObjectData theObjectReader = VObjectData.getData(getKowalskyObject());
		final VObjectData theNano = Provisionning.provisionRfid(theObjectReader, serial);

		Assert.assertEquals(theNano.getObject_login(), prefix + serial);
		Assert.assertEquals(theNano.getObjectType(), ObjectType.RFID_NANOZTAG);
		Assert.assertEquals(theNano.getProfile().getPictureFiles().getPath(), thePicture.getPath());
		Assert.assertEquals(theNano.getProfile().getLabel(), Hardware.HARDWARE.NANOZTAG.getLabel());

		////other rfid (violet partner) it's stored in ztamp table
		final String otherSerial = "d00218c1090b58c7";
		new ZtampMock(0, otherSerial, Hardware.HARDWARE.OTHER_RFID, theZtampBatch, thePicture);
		final VObjectData theRfid = Provisionning.provisionRfid(theObjectReader, otherSerial);

		Assert.assertEquals(theRfid.getObject_login(), prefix + serial);
		Assert.assertEquals(theRfid.getObjectType(), ObjectType.RFID_OTHER);
		Assert.assertEquals(theRfid.getProfile().getPictureFiles().getPath(), thePicture.getPath());
		Assert.assertEquals(theRfid.getProfile().getLabel(), prefix);

	}

	@Test
	public void OtherZtampProvisionning() {
		final String serial = "d00218c1090b58c9";
		final String prefix = "RFID";
		final VObjectData theObjectReader = VObjectData.getData(getKowalskyObject());
		final VObjectData rfid = Provisionning.provisionRfid(theObjectReader, serial);

		Assert.assertEquals(rfid.getObject_login(), prefix + serial);
		Assert.assertEquals(rfid.getObjectType(), ObjectType.RFID_OTHER);
		Assert.assertEquals(rfid.getProfile().getLabel(), prefix);
	}

	@Test
	public void BookProvisionning() {
		final String serial = "d00218c1090b5822";
		final String prefix = "LBLP";
		final String title = "La belle lisse poire";
		final Application LBLP = Factories.APPLICATION.findByName("net.violet.audiobooks.labellelissepoireduprincedemottordu");
		final ZtampBatchMock theZtampBatch = new ZtampBatchMock(0, LBLP, LBLP.getId().toString(), prefix);
		new ZtampMock(0, serial, Hardware.HARDWARE.BOOK, theZtampBatch, null);
		new DicoMock(0, LBLP.getProfile().getTitle().replaceAll("LOC_", net.violet.common.StringShop.EMPTY_STRING), getFrLang(), title, net.violet.common.StringShop.EMPTY_STRING);
		final VObjectData theObjectReader = VObjectData.getData(getKowalskyObject());
		final VObjectData theBook = Provisionning.provisionRfid(theObjectReader, serial);

		Assert.assertEquals(theBook.getObject_login(), prefix + serial);
		Assert.assertEquals(theBook.getObjectType(), ObjectType.RFID_BOOK);
		Assert.assertEquals(theBook.getProfile().getPictureFiles().getPath(), LBLP.getProfile().getPictureFile().getPath());
		Assert.assertEquals(theBook.getProfile().getLabel(), title);

		final List<Subscription> subscription = Factories.SUBSCRIPTION.findAllByObject(theBook.getReference());
		Assert.assertTrue(subscription.size() == 1);

		final Subscription theSubscription = subscription.get(0);
		Assert.assertEquals(theSubscription.getApplication(), LBLP);

		final List<SubscriptionScheduling> subscription_scheduling = Factories.SUBSCRIPTION_SCHEDULING.findAllBySubscription(theSubscription);
		Assert.assertTrue(subscription_scheduling.size() == 1);

		final SubscriptionScheduling theSubscriptionScheduling = subscription_scheduling.get(0);
		Assert.assertEquals(theSubscriptionScheduling.getType(), SchedulingType.SCHEDULING_TYPE.InteractionTrigger);

		final List<SubscriptionSchedulingSettings> subscription_scheduling_settings = Factories.SUBSCRIPTION_SCHEDULING_SETTINGS.findAllBySubscriptionScheduling(theSubscriptionScheduling);
		Assert.assertTrue(subscription_scheduling_settings.size() == 1);

		final SubscriptionSchedulingSettings theSubscriptionSchedulingSettings = subscription_scheduling_settings.get(0);
		Assert.assertEquals(theSubscriptionSchedulingSettings.getKey(), InteractionTriggerHandler.EVENT);
		Assert.assertEquals(theSubscriptionSchedulingSettings.getValue(), ZtampDetectionEvent.NAME);
	}
}
