package net.violet.platform.api.clients;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import net.violet.platform.api.exceptions.InternalErrorException;
import net.violet.platform.applets.api.ApplicationEvent;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.util.Templates;

import org.apache.log4j.Logger;

/**
 * Send an email message on profit of an interactive application
 * 
 * @author christophe - Violet
 */
public class MailProxyClient {

	private static final Logger LOGGER = Logger.getLogger(MailProxyClient.class);

	public static Map<String, Object> send(ApplicationEvent inEventContext, List<String> inTo, List<String> inCc, String inSubject, String inMsgText) throws InternalErrorException {

		final VObjectData sender = inEventContext.getObject();
		final UserData from = sender.getOwner();
		final ApplicationData app = inEventContext.getApplication();
		final VObjectData reader = inEventContext.getReader();

		final Collection<InternetAddress> to = MailProxyClient.convert(inTo);
		final Collection<InternetAddress> cc = MailProxyClient.convert(inCc);

		if (MailProxyClient.LOGGER.isDebugEnabled()) {
			MailProxyClient.LOGGER.debug("Sending " + inSubject + " mail from " + from + ", to " + to);
		}

		if (!Templates.sendAppMailPost(app.getReference(), from.getReference(), sender.getReference(), reader.getReference(), to, cc, inSubject, inMsgText)) {
			throw new InternalErrorException("Message not sent : " + from + net.violet.common.StringShop.SPACE + to + net.violet.common.StringShop.SPACE + cc + net.violet.common.StringShop.SPACE + inSubject + net.violet.common.StringShop.SPACE + inMsgText);
		}

		return Collections.emptyMap();

	}

	private static Collection<InternetAddress> convert(List<String> inAddresses) {

		final Collection<InternetAddress> addresses = new ArrayList<InternetAddress>(inAddresses.size());
		for (final String addr : inAddresses) {
			try {
				addresses.add(new InternetAddress(addr));
			} catch (final AddressException e) {

			}
		}

		return addresses;
	}

}
