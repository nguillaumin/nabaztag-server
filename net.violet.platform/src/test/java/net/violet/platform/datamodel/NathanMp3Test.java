package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.List;

import net.violet.platform.datamodel.factories.Factories;

import org.junit.Assert;
import org.junit.Test;

public class NathanMp3Test extends DBTest {

	@Test
	public void testCreation() throws SQLException {

		final Files theFile = Factories.FILES.find(1);
		final NathanVersion theVersion = Factories.NATHAN_VERSION.find(1);

		Assert.assertNotNull(theVersion);

		final NathanMp3 theMp3 = new NathanMp3Impl((NathanVersionImpl) theVersion, (FilesImpl) theFile, 111);

		Assert.assertNotNull(theMp3);

		final List<NathanMp3> theList = Factories.NATHAN_MP3.findAllMp3ByVersion(theVersion);

		Assert.assertEquals(1, theList.size());
		Assert.assertEquals(theMp3, theList.get(0));

	}

}
