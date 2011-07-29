package net.violet.platform.api.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import net.violet.platform.api.config.WidgetConfig.ConfigurationWidgetMap;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

public class WidgetConfigTest extends MockTestBase {

	@Test
	public void loadConfigTest() throws InvalidParameterException, FileNotFoundException {

		final String thePath = "src/test/java/net/violet/platform/api/config/application_interface.xml";
		final List<ConfigurationWidgetMap> theSettings = WidgetConfig.loadConfig(new FileReader(thePath), MimeType.MIME_TYPES.XML, false);

		Assert.assertEquals(5, theSettings.size());

	}
}
