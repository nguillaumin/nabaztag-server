package net.violet.platform.feeds.senders;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.Files;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.feeds.processors.ProcessedEntry;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.MessageSignature;
import net.violet.platform.xmpp.JabberMessageFactory;

/**
 * The InstantSender is a real sender. The entries will be sent to the recipients.
 */
public class InstantSender implements EntrySender {

	private final List<VObjectData> recipients;
	private final MessageSignature signature;
	private final String messageTitle;

	/**
	 * Creates an InstantSender which will send the entries to the provided object recipients.
	 * @param recipients
	 */
	public InstantSender(List<VObjectData> recipients, MessageSignature signature, String messageTitle) {
		this.recipients = recipients;
		this.signature = signature;
		this.messageTitle = messageTitle;
	}

	/**
	 * The provided contents are immediatly sent to the given list of recipients (see constructor above).
	 * The contents will not be sent to RFID objects and disconnected objects.
	 */
	public void performTreatment(List<ProcessedEntry> entries) {
		final List<Files> theContents = new ArrayList<Files>();
		for (final ProcessedEntry anEntry : entries) {
			theContents.addAll(anEntry.getContents());
		}

		for (final VObjectData aMember : this.recipients) {
			if (aMember.isCurrentlyRecipient()) {
				sendServiceMessage(theContents, aMember);
			}
		}
	}

	/**
	 * This method is only here for tests ! Calling static method of MessageServices sucks, it has to be changed (use Strategy pattern)
	 * @param theContents
	 * @param aMember
	 */
	protected void sendServiceMessage(List<Files> theContents, VObjectData aMember) {
		MessageServices.sendServiceMessage(theContents.toArray(new Files[theContents.size()]), aMember.getReference(), this.messageTitle, null, 600, Palette.RANDOM, this.signature, false, JabberMessageFactory.IDLE_MODE);
	}

}
