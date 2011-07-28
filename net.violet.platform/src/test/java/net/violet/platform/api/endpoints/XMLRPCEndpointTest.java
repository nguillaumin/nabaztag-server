package net.violet.platform.api.endpoints;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.api.actions.ApiActionTestBase;
import net.violet.platform.web.ServletTestBase;
import net.violet.platform.web.ServletTestRequest;
import net.violet.platform.web.ServletTestResponse;

import org.apache.xmlrpc.common.XmlRpcNotAuthorizedException;
import org.apache.xmlrpc.webserver.XmlRpcServlet;
import org.junit.Assert;
import org.junit.Test;

public class XMLRPCEndpointTest extends ApiActionTestBase {

	@Test
	public void testBadCredentials() throws IOException {
		final ServletTestRequest theRequest = ServletTestRequest.createFromURL("/vl", "/vl/xmlrpc");
		final OutputStream theStream = theRequest.getDataOutputStream();
		final PrintStream thePrintStream = new PrintStream(theStream);
		thePrintStream.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		final ServletTestResponse theResponse = ServletTestBase.createResponse();
		boolean gotException = false;
		try {
			postRequest(theRequest, theResponse);
		} catch (final ServletException anException) {
			gotException = true;
			Assert.assertEquals(XmlRpcNotAuthorizedException.class, anException.getRootCause().getClass());
		}
		Assert.assertTrue(gotException);
	}

	@Test
	public void testEmptyRequest() throws IOException, ServletException {
		final String theAPIKey = getPublicApplicationAPICaller().getAPIKey();
		final ServletTestRequest theRequest = ServletTestRequest.createFromURL("/vl", "/vl/xmlrpc");
		theRequest.setRemoteUser(theAPIKey);
		final OutputStream theStream = theRequest.getDataOutputStream();
		final PrintStream thePrintStream = new PrintStream(theStream);
		thePrintStream.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		final ServletTestResponse theResponse = ServletTestBase.createResponse();
		postRequest(theRequest, theResponse);
		Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><methodResponse><fault><value><struct><member><name>faultString</name><value>Failed to parse XML-RPC request: Premature end of file.</value></member><member><name>faultCode</name><value><i4>0</i4></value></member></struct></value></fault></methodResponse>", theResponse.getWrittenData());
	}

	@Test
	public void testEcho() throws IOException, ServletException {
		final String theAPIKey = getPublicApplicationAPICaller().getAPIKey();
		final ServletTestRequest theRequest = ServletTestRequest.createFromURL("/vl", "/vl/xmlrpc");
		theRequest.setRemoteUser(theAPIKey);
		final OutputStream theStream = theRequest.getDataOutputStream();
		final PrintStream thePrintStream = new PrintStream(theStream);
		final String theEchoStruct = "<params><param><value><struct><member><name>foo</name><value><i4>42</i4></value></member></struct></value></param></params>";
		thePrintStream.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?><methodCall><methodName>violet.test.echo</methodName>" + theEchoStruct + "</methodCall>");
		final ServletTestResponse theResponse = ServletTestBase.createResponse();
		postRequest(theRequest, theResponse);
		Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><methodResponse>" + theEchoStruct + "</methodResponse>", theResponse.getWrittenData());
	}

	private void postRequest(ServletTestRequest inRequest, HttpServletResponse inResponse) throws IOException, ServletException {
		inRequest.setMethod("POST");
		inRequest.setContentType("text/xml; charset=\"utf-8\"");
		inRequest.setHeader("Accept", "text/xml");
		final XmlRpcServlet theServlet = new XMLRPCEndpoint();
		theServlet.init(new ServletConfig() {

			public String getInitParameter(String name) {
				return null;
			}

			public Enumeration getInitParameterNames() {
				return Collections.enumeration(Collections.<String> emptyList());
			}

			public ServletContext getServletContext() {
				throw new UnsupportedOperationException();
			}

			public String getServletName() {
				throw new UnsupportedOperationException();
			}
		});
		theServlet.doPost(inRequest, inResponse);
	}
}
