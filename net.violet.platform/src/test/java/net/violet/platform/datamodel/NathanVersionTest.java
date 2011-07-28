package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.NathanVersionData;

import org.junit.Assert;
import org.junit.Test;

public class NathanVersionTest extends DBTest {

	@Test
	public void testCreatingVersion() throws SQLException {

		final String isbn = "111111";

		final VObject theAuthor = Factories.VOBJECT.find(63643);
		final NathanVersion version = new NathanVersionImpl(theAuthor, isbn);

		Assert.assertNotNull(version);

		Assert.assertEquals(theAuthor, version.getAuthor());
		Assert.assertEquals(false, version.getOfficial());
		Assert.assertEquals(false, version.getShared());
		Assert.assertEquals(NathanVersion.Status.FINISHED.toString(), version.getStatus());

		final NathanVersion foundVersion = Factories.NATHAN_VERSION.find(version.getId());
		Assert.assertEquals(version, foundVersion);

		final List<NathanVersion> authorVersions = Factories.NATHAN_VERSION.findAllByAuthorAndIsbn(theAuthor, isbn);

		Assert.assertEquals(1, authorVersions.size());
		Assert.assertEquals(version, authorVersions.get(0));

		final List<NathanTag> versionTags = version.getTags();
		Assert.assertEquals(0, versionTags.size());

		final List<NathanTag> newTags = new ArrayList<NathanTag>();
		newTags.add(Factories.NATHAN_TAG.find(1));
		newTags.add(Factories.NATHAN_TAG.find(5));
		newTags.add(Factories.NATHAN_TAG.find(7));

		version.setVersionInformation("a description", NathanVersion.Status.AUTHORIZED, true, newTags);

		Assert.assertEquals(version, Factories.NATHAN_VERSION.getPopularVersions(isbn).get(0));
		Assert.assertEquals(3, version.getTags().size());

		Assert.assertEquals(1, NathanVersionData.lookForVersions(Arrays.asList(new Long[] { 8L, 7L }), isbn).size());

	}

}
