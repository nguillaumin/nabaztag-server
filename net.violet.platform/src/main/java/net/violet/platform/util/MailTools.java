package net.violet.platform.util;

import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;

public class MailTools {

	private static final Logger LOGGER = Logger.getLogger(MailTools.class);

	private static String sendpartial = "true";

	private static final Executor MAIL_THREAD_POOL = Executors.newFixedThreadPool(5);

	private static String CHARSET = "UTF-8";

	private static boolean MAIL_DEBUG;

	/**
	 * Generic method to send an email from mustn't be null, neither to or bbc
	 * can be null if text_txt or text_html are null the mail will be empty
	 * 
	 * @param from the address of the sender, mustn't be null
	 * @param to collection of internet addresses containing the main dest
	 *            addresses
	 * @param cc collection of secundary internet addresses
	 * @param bcc collection of addresses to send a hidden copy of the mail
	 * @param subject subject of the mail
	 * @param text_txt text of the mail (txt format)
	 * @param text_html text of the mail (html format)
	 * @throws MessagingException if there's a format error and the mail can't
	 *             be built or sent
	 * @throws NullArgumentException if from is null or if bbc and to are both
	 *             null at the same time
	 * @throws IllegalArgumentException if to and bbc are empty or null
	 */
	public static void send(final InternetAddress from, final Collection<InternetAddress> to, final Collection<InternetAddress> cc, final Collection<InternetAddress> bcc, final String subject, final String text_txt, final String text_html) {

		MailTools.MAIL_THREAD_POOL.execute(new Runnable() {

			public void run() {

				try {
					if (((to == null) || (to.size() == 0)) && ((bcc == null) || (bcc.size() == 0))) {
						throw new IllegalArgumentException("No address to send the mail to (fields to and bbc are both empty)");
					}
					if (from == null) {
						throw new IllegalArgumentException("from");
					}

					// Init properties
					final Properties prop = System.getProperties();
					prop.put("mail.smtp.host", Constantes.MAIL_HOST);
					prop.put("mail.smtp.sendpartial", MailTools.sendpartial);

					final Session msession = Session.getInstance(prop);
					msession.setDebug(MailTools.MAIL_DEBUG);

					final MimeMessage message = new MimeMessage(msession);

					// Mail informations
					if (subject == null) {
						message.setSubject(net.violet.common.StringShop.EMPTY_STRING, MailTools.CHARSET);
					} else {
						message.setSubject(subject, MailTools.CHARSET);
					}
					message.setSentDate(new Date());
					message.setFrom(from);

					// Text message
					final MimeBodyPart mimeBodyPartTxt = new MimeBodyPart();
					mimeBodyPartTxt.setHeader("Content-Type", "text/plain;charset=\"" + MailTools.CHARSET + "\"");
					mimeBodyPartTxt.setHeader("Content-Transfer-Encoding", "8bit");
					if (text_txt == null) {
						mimeBodyPartTxt.setText(net.violet.common.StringShop.EMPTY_STRING, MailTools.CHARSET);
					} else {
						mimeBodyPartTxt.setText(text_txt, MailTools.CHARSET);
					}
					final MimeMultipart multipart = new MimeMultipart("alternative");
					multipart.addBodyPart(mimeBodyPartTxt);

					// Html message
					message.setHeader("Content-Type", "text/html;charset=\"" + MailTools.CHARSET + "\"");
					message.setHeader("Content-Encoding", "html");
					message.setHeader("Content-Transfer-Encoding", "8bit");
					final MimeBodyPart mimeBodyPartHtml = new MimeBodyPart();
					if (text_html == null) {
						mimeBodyPartHtml.setText(net.violet.common.StringShop.EMPTY_STRING, MailTools.CHARSET);
					} else {
						mimeBodyPartHtml.setText(text_html, MailTools.CHARSET);
					}
					mimeBodyPartHtml.setHeader("Content-Type", "text/html;charset=\"" + MailTools.CHARSET + "\"");
					multipart.addBodyPart(mimeBodyPartHtml);

					message.setContent(multipart);

					if ((to != null) && !to.isEmpty()) {
						message.setRecipients(Message.RecipientType.TO, to.toArray(new InternetAddress[0]));
					}
					if ((cc != null) && !cc.isEmpty()) {
						message.setRecipients(Message.RecipientType.CC, cc.toArray(new InternetAddress[0]));
					}
					if ((bcc != null) && !bcc.isEmpty()) {
						message.setRecipients(Message.RecipientType.BCC, bcc.toArray(new InternetAddress[0]));
					}

					Transport.send(message);

				} catch (final MessagingException e) {
					MailTools.LOGGER.error("Mailtools.send(subject:" + subject + ") failed :", e);
				}
			}
		});

	}

	/**
	 * Generic method to send an email
	 * 
	 * @param from the address of the sender, mustn't be null
	 * @param to collection of internet addresses containing the addresses to
	 *            send the mail to
	 * @param collection of addresses to send a hide copy of the mail
	 * @param subject subject of the mail
	 * @param text_txt text of the mail (txt format)
	 * @param text_html text of the mail (html format)
	 * @throws MessagingException if there's a format error and the mail can't
	 *             be built or sent
	 * @throws IllegalArgumentException if from doesn't fit the email address
	 *             format
	 * @throws NullArgumentException if from is null or if bbc and to are both
	 *             null at the same time
	 */
	public static void send(String from, Collection<InternetAddress> to, Collection<InternetAddress> cc, Collection<InternetAddress> bcc, String subject, String text_txt, String text_html) throws MessagingException {

		if (from == null) {
			throw new IllegalArgumentException(from);
		}
		if (!MailTools.checkMail(from)) {
			throw new IllegalArgumentException(from + " is not an email address");
		}

		final InternetAddress address = new InternetAddress(from);
		if ((Constantes.FILTER_EMAIL == 0) || (to.iterator().next().getAddress().indexOf("@violet.net") > 0)) {// not send email in Pré-prod and dev (except at @violet.net)
			MailTools.send(address, to, cc, bcc, subject, text_txt, text_html);
		}
	}

	/**
	 * Converts a collection of string representing addresses into collection of
	 * InternetAddresses If one of the addresses doesn't fit with the format
	 * it'll not be added, so it could happen that returnedCollection.size() <
	 * addresses.size() It's recommended to use this method to assert that
	 * addresses are correct
	 * 
	 * @param addresses the addresses to convert
	 * @return a collection containing InternetAddress matching with the strings
	 *         in parameter
	 * @throws AddressException if the creation of InternetAddress fails
	 */
	public static Collection<InternetAddress> buildAddressCollection(Collection<String> addresses) throws AddressException {

		final Collection<InternetAddress> iAddresses = new ArrayList<InternetAddress>();

		if (addresses != null) {
			for (final String address : addresses) {
				if (MailTools.checkMail(address)) {
					iAddresses.add(new InternetAddress(address));
				}
			}
		}
		return iAddresses;
	}

	/**
	 * Check if the string matches with email address format
	 * 
	 * @param mail the string to test
	 * @return true if the string matches the format, false otherwise
	 */
	public static final boolean checkMail(final String mail) {
		final String regex = "^\\w(\\.?[\\w-])*@\\w(\\.?[-\\w])*\\.(([a-z]*)(\\.([a-z])*)?|([a-z])*(\\.([a-z])*)?)$";
		return Pattern.matches(regex, mail.toLowerCase());
	}

	public static void sendSupport(String content) {
		MailTools.sendSupport(Constantes.SUPPORT_SUBJECT, content);
	}

	public static void sendSupport(String subject, String content) {

		try {
			final List<InternetAddress> mailto = Arrays.asList(new InternetAddress(Constantes.SUPPORT_MAILTO));
			MailTools.send(Constantes.SUPPORT_MAILFROM, mailto, null, null, subject, content, net.violet.common.StringShop.EMPTY_STRING);

		} catch (final Exception me) {
			MailTools.LOGGER.error("MailTools.sendSupport(subject:" + subject + ") failed with error :", me);
		}
	}

	/**
	 * Send a mail to the recipient given when a new entry of weather source was created
	 * @param recipient the recipient is the sender. This function just warns. 
	 * @param subject
	 * @param content
	 */
	public static void sendFromAdmin(String recipient, String subject, String content) {

		try {
			final List<InternetAddress> mailto = Arrays.asList(new InternetAddress(recipient));
			MailTools.send(recipient, mailto, null, null, subject, content, net.violet.common.StringShop.EMPTY_STRING);

		} catch (final Exception me) {
			MailTools.LOGGER.error("MailTools.sendSupport(subject:" + subject + ") failed with error :", me);
		}
	}

	/**
	 * Send a mail to the recipient given when a new entry of weather source was created
	 * @param recipient the recipient is the sender. This function just warns. 
	 * @param subject
	 * @param content
	 */
	public static void sendFromStats(String recipient, String subject, String content, String filePath) {

		try {
			final List<InternetAddress> mailto = Arrays.asList(new InternetAddress(recipient));
			// Init properties
			final Properties prop = System.getProperties();
			prop.put("mail.smtp.host", Constantes.MAIL_HOST);
			prop.put("mail.smtp.sendpartial", MailTools.sendpartial);

			final Session msession = Session.getInstance(prop);
			msession.setDebug(MailTools.MAIL_DEBUG);

			final MimeMessage message = new MimeMessage(msession);

			// Mail informations
			if (subject == null) {
				message.setSubject(net.violet.common.StringShop.EMPTY_STRING, MailTools.CHARSET);
			} else {
				message.setSubject(subject, MailTools.CHARSET);
			}
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(recipient));

			// Première partie du message
			BodyPart messageBodyPart = new MimeBodyPart();

			// Contenu du message
			messageBodyPart.setText(content);

			//Ajout de la première partie du message dans un objet Multipart
			final Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Partie de la pièce jointe
			messageBodyPart = new MimeBodyPart();
			final DataSource source = new FileDataSource(filePath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filePath);
			//Ajout de la partie pièce jointe
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);
			message.setRecipients(Message.RecipientType.TO, mailto.toArray(new InternetAddress[0]));
			// Envoie du message
			Transport.send(message);

		} catch (final Exception me) {
			MailTools.LOGGER.error("MailTools.sendSupport(subject:" + subject + ") failed with error :", me);
		}
	}

	/**
	 *Save from OldMailTools
	 *
	 * Returns a Store instance according to the protocol discribed by typeMail,
	 * the store is already connected
	 * 
	 * @param typeMail protocol to use to connect the store
	 * @param host the host
	 * @param user user login to connect
	 * @param passwd user password
	 * @return the store already connected
	 * @throws MessagingException if an error happens
	 */
	public static Store getStore(String typeMail, String host, String user, String passwd) throws MessagingException {
		final int port;
		final Properties props = new Properties();
		final String protocol;
		final boolean isOverSSL;

		if (typeMail.equals("pop")) {
			port = 110;
			protocol = "pop3";
			isOverSSL = false;
		} else if (typeMail.equals("ssl") || typeMail.equals("pop3s") || typeMail.equals("pops")) {
			port = 995;
			protocol = "pop3";
			isOverSSL = true;
		} else if (typeMail.equals("imap")) {
			port = 143;
			protocol = "imap";
			isOverSSL = false;
		} else if (typeMail.equals("imaps")) {
			port = 993;
			protocol = "imap";
			isOverSSL = true;
		} else {
			throw new IllegalArgumentException("Unknown mail type " + typeMail);
		}
		if (isOverSSL) {
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			props.put("mail." + protocol + ".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail." + protocol + ".socketFactory.fallback", "false");
		}
		props.put("mail." + protocol + ".timeout", "20000");
		props.put("mail." + protocol + ".connectiontimeout", "10000");
		props.put("mail." + protocol + ".port", net.violet.common.StringShop.EMPTY_STRING + port);
		props.put("mail." + protocol + ".socketFactory.port", net.violet.common.StringShop.EMPTY_STRING + port);

		final Session msession = Session.getInstance(props, null);

		final URLName urln = new URLName(protocol, host, port, null, user, passwd);
		final Store store = msession.getStore(urln);

		if (store != null) {
			store.connect();
		}
		return store;
	}

	public static int checkNbMail(String inHost, String inUser, String passwd, String type) {

		int totalMessages = -1;
		final String mbox = "INBOX";
		Store store = null;
		try {
			store = MailTools.getStore(type, inHost, inUser, passwd);
		} catch (final MessagingException e1) {
			MailTools.LOGGER.error(e1, e1);
			return -1;
		}

		if (store == null) {
			return -1;
		}

		// Open the Folder
		Folder folder = null;
		try {
			folder = store.getDefaultFolder();
		} catch (final MessagingException e) {
			MailTools.LOGGER.fatal(e, e);
		}
		if (folder == null) {
			return -3;
		}
		try {
			folder = folder.getFolder(mbox);
		} catch (final MessagingException e) {
			MailTools.LOGGER.fatal(e, e);
		}
		if (folder == null) {
			return -4;
		}

		// try to open read/write and if that fails try read-only
		try {
			folder.open(Folder.READ_WRITE);
		} catch (final MessagingException ex) {
			try {
				folder.open(Folder.READ_ONLY);
			} catch (final MessagingException e) {
				MailTools.LOGGER.fatal(e, e);
			}
		}

		try {
			totalMessages = folder.getMessageCount();
		} catch (final MessagingException e) {
			MailTools.LOGGER.fatal(e, e);
			totalMessages = -1;
		}
		try {
			store.close();
		} catch (final MessagingException e) {
			MailTools.LOGGER.fatal(e, e);
		}

		return totalMessages;
	}
}
