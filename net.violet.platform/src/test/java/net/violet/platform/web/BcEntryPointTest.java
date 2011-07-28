package net.violet.platform.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

public class BcEntryPointTest extends ServletTestBase {

	@Test
	public void testEmpty() throws IOException {
		final BcEntryPoint theServlet = new BcEntryPoint();
		final HttpServletRequest theRequest = ServletTestBase.createEmptySessionRequest();
		final HttpServletResponse theResponse = ServletTestBase.createResponse();
		theServlet.doGet(theRequest, theResponse);
		Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN, ((ServletTestResponse) theResponse).getStatus());
	}
}
