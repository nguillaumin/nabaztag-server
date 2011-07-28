package net.violet.platform.voice;

import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

public class TTSFilterTest extends MockTestBase {

	@Test
	public void testCleanUpTTSString() {
		final String theText = "my super-text qui pourrait \"*\" faire crasher * le *TTS* si je ne faisais'-*rien";
		final String theTextFiltered = TTSFilter.filterNewsTitle(theText);
		Assert.assertFalse(theTextFiltered.contains("*"));
		Assert.assertFalse(theTextFiltered.contains("-"));
		System.out.println(theTextFiltered);
	}
}
