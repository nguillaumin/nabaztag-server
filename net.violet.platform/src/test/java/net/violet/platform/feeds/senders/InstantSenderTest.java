package net.violet.platform.feeds.senders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.feeds.processors.ProcessedEntry;

import org.junit.Assert;
import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;

public class InstantSenderTest extends MockTestBase {

	@Test
	public void sendEntries() {

		final VObjectData o1 = VObjectData.getData(getBrewsterObject());
		final VObjectData o2 = VObjectData.getData(getKowalskyObject());
		final VObjectData o3 = VObjectData.getData(getPrivateObject());
		final VObjectData o4 = VObjectData.createObject(ObjectType.RFID_NANOZTAG, "serial", "label", o1.getOwner(), "location");

		final List<Files> allTheContents = new ArrayList<Files>();
		final List<ProcessedEntry> theEntries = new ArrayList<ProcessedEntry>();
		for (int i = 0; i < 2; i++) {
			final ProcessedEntry entry = getProcessedEntry("myTitle" + i, "file" + i + "-", 3);
			theEntries.add(entry);
			allTheContents.addAll(entry.getContents());
		}

		final AtomicBoolean contentsAreOk = new AtomicBoolean(true);
		final List<VObjectData> theyReceivedMessage = new ArrayList<VObjectData>();
		final InstantSender sender = new InstantSender(Arrays.asList(o1, o2, o3, o4), null, "funny title") {

			@Override
			protected void sendServiceMessage(List<Files> theContents, VObjectData member) {
				if (!theContents.equals(allTheContents)) {
					contentsAreOk.set(false);
				}

				theyReceivedMessage.add(member);
			}

		};

		sender.performTreatment(theEntries);

		Assert.assertTrue(contentsAreOk.get());
		Assert.assertEquals(Arrays.asList(o1, o2, o3), theyReceivedMessage);
	}

	private ProcessedEntry getProcessedEntry(String title, String fileRootPath, int nbFile) {
		final SyndEntry entry = new SyndEntryImpl();
		entry.setTitle(title);

		final List<Files> theFiles = new ArrayList<Files>();
		for (int i = 0; i < nbFile; i++) {
			theFiles.add(Factories.FILES.createFile(fileRootPath + i, MIME_TYPES.A_MPEG));
		}

		return new ProcessedEntry(entry, theFiles);
	}

}
