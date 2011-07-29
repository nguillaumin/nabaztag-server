package net.violet.platform.api.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.violet.platform.api.config.MaskConfig.SchedulingMaskMap;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.util.Constantes;

import org.junit.Assert;
import org.junit.Test;

public class MaskConfigTest extends MockTestBase {

	@Test
	public void forNabaztagtagTest() throws InvalidParameterException, FileNotFoundException {

		final String thePath = "src/test/java/net/violet/platform/api/config/application_mask.xml";
		final List<SchedulingMaskMap> theSchedulings = MaskConfig.loadConfig(new FileReader(thePath), MimeType.MIME_TYPES.XML, ObjectType.NABAZTAG_V2);

		final Set<String> touchedType = new HashSet<String>();
		for (final SchedulingMaskMap aScheduling : theSchedulings) {
			if (aScheduling.get("scheduling_type").equals(SchedulingType.SCHEDULING_TYPE.Daily.name())) {
				Assert.assertEquals(2, aScheduling.get("maximum"));
				Assert.assertEquals(0, aScheduling.get("minimum"));
				touchedType.add(aScheduling.get("scheduling_type").toString());
			} else if (aScheduling.get("scheduling_type").equals(SchedulingType.SCHEDULING_TYPE.Ambiant.name())) {
				Assert.assertEquals(1, aScheduling.get("maximum"));
				Assert.assertEquals(0, aScheduling.get("minimum"));
				touchedType.add(aScheduling.get("scheduling_type").toString());
			} else if (aScheduling.get("scheduling_type").equals(SchedulingType.SCHEDULING_TYPE.DailyWithDuration.name())) {
				Assert.assertEquals(1, aScheduling.get("maximum"));
				Assert.assertEquals(0, aScheduling.get("minimum"));
				touchedType.add(aScheduling.get("scheduling_type").toString());
			} else {
				Assert.fail("Unexpected type : " + aScheduling.get("scheduling_type").toString());
			}
		}

		Assert.assertEquals(3, touchedType.size());

	}

	@Test
	public void forRfidTest() throws InvalidParameterException, FileNotFoundException {

		final String thePath = "src/test/java/net/violet/platform/api/config/application_mask.xml";
		final List<SchedulingMaskMap> theSchedulings = MaskConfig.loadConfig(new FileReader(thePath), MimeType.MIME_TYPES.XML, ObjectType.RFID_BOOK);

		final Set<String> touchedType = new HashSet<String>();
		for (final SchedulingMaskMap aScheduling : theSchedulings) {
			if (aScheduling.get("scheduling_type").equals(SchedulingType.SCHEDULING_TYPE.InteractionTrigger.name())) {
				Assert.assertEquals(1, aScheduling.get("maximum"));
				Assert.assertEquals(1, aScheduling.get("minimum"));
				touchedType.add(aScheduling.get("scheduling_type").toString());
			} else {
				Assert.fail("Unexpected type : " + aScheduling.get("scheduling_type").toString());
			}
		}

		Assert.assertEquals(1, touchedType.size());

	}

}
