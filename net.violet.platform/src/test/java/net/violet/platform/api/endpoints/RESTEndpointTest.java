package net.violet.platform.api.endpoints;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.violet.platform.api.actions.ApiActionTestBase;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.web.ServletTestBase;
import net.violet.platform.web.ServletTestRequest;
import net.violet.platform.web.ServletTestResponse;

import org.junit.Assert;
import org.junit.Test;

public class RESTEndpointTest extends ApiActionTestBase {

	@Test
	public void testBadCredentials() throws ConversionException {
		final RESTEndpoint theEndpoint = new RESTEndpoint();
		final HttpServletRequest theRequest = ServletTestRequest.createFromURL("/vl", "/vl/rest/objects/findByName/Private");
		final ServletTestResponse theResponse = ServletTestBase.createResponse();
		theEndpoint.doGet(theRequest, theResponse);
		final Map<String, Object> result = ConverterFactory.JSON.convertFrom(theResponse.getWrittenData());
		Assert.assertEquals("BadCredential", result.get("title"));
		Assert.assertEquals("error", result.get("type"));
		Assert.assertEquals("unauthorized", result.get("message"));
		Assert.assertEquals(401, result.get("status"));
	}

	@Test
	public void testEchoGet() {
		final String theAPIKey = getPublicApplicationAPICaller().getAPIKey();;
		final RESTEndpoint theEndpoint = new RESTEndpoint();
		final ServletTestRequest theRequest = ServletTestRequest.createFromURL("/vl", "/vl/rest/test/echo/\"Hello!\"");
		theRequest.setRemoteUser(theAPIKey);
		final ServletTestResponse theResponse = ServletTestBase.createResponse();
		theEndpoint.doGet(theRequest, theResponse);
		Assert.assertEquals("{\"id\":\"Hello!\"}", theResponse.getWrittenData());
	}

	@Test
	public void testEchoPostJsonUrl() {
		final String theAPIKey = getPublicApplicationAPICaller().getAPIKey();;
		final RESTEndpoint theEndpoint = new RESTEndpoint();
		final ServletTestRequest theRequest = ServletTestRequest.createFromURL("/vl", "/vl/rest/test/echo/\"Hello!\"");
		theRequest.setRemoteUser(theAPIKey);
		theRequest.setMethod("POST");
		theRequest.setContentType("application/json; charset=\"utf-8\"");
		theRequest.setHeader("Accept", "application/json");
		final OutputStream theStream = theRequest.getDataOutputStream();
		final PrintStream thePrintStream = new PrintStream(theStream);
		thePrintStream.print("{}");
		final ServletTestResponse theResponse = ServletTestBase.createResponse();
		theEndpoint.doPost(theRequest, theResponse);
		Assert.assertEquals("{\"id\":\"Hello!\"}", theResponse.getWrittenData());
	}

	@Test
	public void testEchoPostJsonUrlAndData() throws ConversionException {
		final String theAPIKey = getPublicApplicationAPICaller().getAPIKey();;
		final RESTEndpoint theEndpoint = new RESTEndpoint();
		final ServletTestRequest theRequest = ServletTestRequest.createFromURL("/vl", "/vl/rest/test/echo/\"Hello!\"");
		theRequest.setRemoteUser(theAPIKey);
		theRequest.setMethod("POST");
		theRequest.setContentType("application/json; charset=\"utf-8\"");
		theRequest.setHeader("Accept", "application/json");
		final OutputStream theStream = theRequest.getDataOutputStream();
		final PrintStream thePrintStream = new PrintStream(theStream);
		thePrintStream.print("{\"foo\":42}");
		final ServletTestResponse theResponse = ServletTestBase.createResponse();
		theEndpoint.doPost(theRequest, theResponse);
		final Map<String, Object> result = ConverterFactory.JSON.convertFrom(theResponse.getWrittenData());
		Assert.assertEquals(42, result.get("foo"));
		Assert.assertEquals("Hello!", result.get("id"));
	}

	@Test
	public void testEchoPostJsonData() {
		final String theAPIKey = getPublicApplicationAPICaller().getAPIKey();;
		final RESTEndpoint theEndpoint = new RESTEndpoint();
		final ServletTestRequest theRequest = ServletTestRequest.createFromURL("/vl", "/vl/rest/test/echo");
		theRequest.setRemoteUser(theAPIKey);
		theRequest.setMethod("POST");
		theRequest.setContentType("application/json; charset=\"utf-8\"");
		theRequest.setHeader("Accept", "application/json");
		final OutputStream theStream = theRequest.getDataOutputStream();
		final PrintStream thePrintStream = new PrintStream(theStream);
		thePrintStream.print("{\"foo\":42}");
		final ServletTestResponse theResponse = ServletTestBase.createResponse();
		theEndpoint.doPost(theRequest, theResponse);
		Assert.assertEquals("{\"foo\":42}", theResponse.getWrittenData());
	}

	@Test
	public void testEchoInvalidFormat() {
		final String theAPIKey = getPublicApplicationAPICaller().getAPIKey();;
		final RESTEndpoint theEndpoint = new RESTEndpoint();
		final ServletTestRequest theRequest = ServletTestRequest.createFromURL("/vl", "/vl/rest/test/echo");
		theRequest.setRemoteUser(theAPIKey);
		theRequest.setMethod("POST");
		theRequest.setContentType("text/plain; charset=\"utf-8\"");
		theRequest.setHeader("Accept", "text/plain");
		final OutputStream theStream = theRequest.getDataOutputStream();
		final PrintStream thePrintStream = new PrintStream(theStream);
		thePrintStream.print("I am in the plain");
		final ServletTestResponse theResponse = ServletTestBase.createResponse();
		theEndpoint.doPost(theRequest, theResponse);
		final String responseContent = theResponse.getWrittenData();
		Assert.assertTrue(responseContent.contains("Unsupported"));
	}
}
