package net.violet.platform.httpclient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionsManagerTest {

	@Test
	public void getStreamTest() throws ConnectionException, IOException, URISyntaxException {
		final String uri = "http://www.tf1.fr/xml/rss/0,,10,00.xml";

		final ConnectionsManager manager = new ConnectionsManager(1);
		final ConnectionConfiguration configuration = new ConnectionConfiguration(uri);
		final Connection connection = manager.openConnection(configuration);

		final byte[] content = IOUtils.toByteArray(connection.getInputStream());
		Assert.assertTrue(content.length > 0);
		connection.close();
		manager.shutdown();
	}

	@Test(expected = ConnectionException.class)
	public void needsCredentialsTest() throws ConnectionException, URISyntaxException {
		final String uri = "https://mail.google.com/mail/feed/atom";

		final ConnectionsManager manager = new ConnectionsManager(1);
		final ConnectionConfiguration configuration = new ConnectionConfiguration(uri);
		manager.openConnection(configuration);
	}

	@Test(expected = ConnectionException.class)
	public void withInvalidCredentialsTest() throws ConnectionException, URISyntaxException {
		final String uri = "https://mail.google.com/mail/feed/atom";

		final ConnectionsManager manager = new ConnectionsManager(1);
		final ConnectionConfiguration configuration = new ConnectionConfiguration(uri);
		configuration.addCredentials("vnabaztag", "violet");
		manager.openConnection(configuration);
	}

	@Test
	public void withValidCredentialsTest() throws ConnectionException, IOException, URISyntaxException {
		final String uri = "https://mail.google.com/mail/feed/atom";

		final ConnectionsManager manager = new ConnectionsManager(1);
		final ConnectionConfiguration configuration = new ConnectionConfiguration(uri);
		configuration.addCredentials("vnabaztag", "violet123");
		final Connection connection = manager.openConnection(configuration);

		final byte[] content = IOUtils.toByteArray(connection.getInputStream());
		Assert.assertTrue(content.length > 0);
	}

	@Test
	public void withInvalidatingIfModifiedTest() throws ConnectionException, URISyntaxException {
		final String uri = "http://www.tf1.fr/xml/rss/0,,10,00.xml";

		final ConnectionsManager manager = new ConnectionsManager(1);
		final ConnectionConfiguration configuration = new ConnectionConfiguration(uri);
		configuration.addLastModified(new Date(System.currentTimeMillis() + 50000));
		final Connection connection = manager.openConnection(configuration);
		Assert.assertNull(connection);
		manager.shutdown();
	}

	@Test
	public void withOldIfModifiedTest() throws ConnectionException, IOException, URISyntaxException {
		final String uri = "http://www.tf1.fr/xml/rss/0,,10,00.xml";

		final ConnectionsManager manager = new ConnectionsManager(1);
		final ConnectionConfiguration configuration = new ConnectionConfiguration(uri);
		configuration.addLastModified(new Date(System.currentTimeMillis() - (365 * 24 * 3600000)));
		final Connection connection = manager.openConnection(configuration);
		final byte[] content = IOUtils.toByteArray(connection.getInputStream());
		Assert.assertTrue(content.length > 0);
		manager.shutdown();
	}
}
