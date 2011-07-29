package net.violet.platform.api.actions.methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.ApiActionTestBase;
import net.violet.platform.api.actions.applications.GetPackage;
import net.violet.platform.api.actions.methods.Get.MethodInformationMap;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.api.converters.JSONConverter;
import net.violet.platform.api.endpoints.APIConstants;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class GetTest extends ApiActionTestBase {

	@Test
	public void testGet() throws APIException {
		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "violet.methods.get");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Map<String, Object> theResult = (Map<String, Object>) theAction.processRequest(theActionParam);
		Assert.assertEquals("violet.methods.get", theResult.get("name"));
	}

	@Test(expected = InvalidParameterException.class)
	public void testNoSuchMethod() throws APIException {
		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "org.bar");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

	@Test
	public void testGetPackage() throws APIException {
		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, APIConstants.GET_PACKAGE_ACTION);
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Map<String, Object> theResult = (Map<String, Object>) theAction.processRequest(theActionParam);
		Assert.assertEquals("violet.applications.getPackage", theResult.get(MethodInformationMap.NAME));
		final List<String> theParamsList = (List<String>) theResult.get(MethodInformationMap.PARAMS);
		Assert.assertEquals(1, theParamsList.size());
		Assert.assertEquals(GetPackage.IF_MODIFIED_SINCE + "[OPTIONAL]", theParamsList.get(0));
	}

	/**
	 * Test à partir des fichiers de tests externes
	 * (probes/api/methods.get_*.test) qui sont dérivés de la spécification.
	 * 
	 * @throws APIException
	 * @throws IOException
	 */
	@Test
	public void specificationTests() throws IOException {
		int nbFailures = 0;

		// Itération sur les fichiers
		final File theExternalTestsDir = new File("src/test/resources/probes/api");
		for (final File theTestFile : theExternalTestsDir.listFiles()) {
			if (theTestFile.isFile()) {
				final String theTestFileName = theTestFile.getName();
				if (theTestFileName.startsWith("methods.get_") && theTestFileName.endsWith(".test")) {
					if (!specificationTest(theTestFile)) {
						nbFailures++;
					}
				}
			}
		}
		if (nbFailures > 0) {
			Assert.fail(nbFailures + " specification tests failed");
		}
	}

	private static final Pattern REGEX_PATTERN = Pattern.compile("regex=(.*)");
	private static final Pattern URL_PATTERN = Pattern.compile("url=methods/get/(.*)");

	/**
	 * Test à partir d'un seul fichier de test externe.
	 * 
	 * @param inTestFile
	 *            fichier de test externe.
	 * @throws APIException
	 * @throws FileNotFoundException
	 */
	private boolean specificationTest(File inTestFile) throws IOException {
		final InputStream theInputStream = new FileInputStream(inTestFile);
		final BufferedReader theReader = new BufferedReader(new InputStreamReader(theInputStream));
		String theRegexStr = null;
		String theMethodName = null;
		while (true) {
			final String theLine = theReader.readLine();
			if (theLine == null) {
				break;
			}
			Matcher theMatcher = GetTest.REGEX_PATTERN.matcher(theLine);
			if (theMatcher.matches()) {
				theRegexStr = theMatcher.group(1);
			} else {
				theMatcher = GetTest.URL_PATTERN.matcher(theLine);
				if (theMatcher.matches()) {
					theMethodName = theMatcher.group(1);
				}
			}
		}
		Assert.assertNotNull("Could not find regex in external test file [" + inTestFile.getAbsolutePath() + "]", theRegexStr);
		Assert.assertNotNull("Could not find the method name in the url of external test file [" + inTestFile.getAbsolutePath() + "]", theMethodName);

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, theMethodName);
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		Object theResult = null;
		try {
			theResult = theAction.processRequest(theActionParam);
		} catch (final APIException e) {
			System.out.println("WARNING for " + theMethodName + " not available in api");
			return true;
		}

		final JSONConverter theConverter = ConverterFactory.JSON;
		final String theResultAsJSON = theConverter.convertTo(theResult);
		final boolean matches;
		if (!theResultAsJSON.matches(".*" + theRegexStr + ".*")) {
			System.out.println("FAILURE for " + theMethodName + " json=" + theResultAsJSON + ", regex=" + theRegexStr);
			matches = false;
		} else {
			System.out.println("SUCCESS for " + theMethodName);
			matches = true;
		}
		return matches;
	}

	/**
	 * Test à partir des fichiers de tests externes
	 * (probes/api/methods.get_*.test) qui sont dérivés de la spécification.
	 * Vérifie que toutes les méthodes implémentées ont un test.
	 */
	private static final String TEST_PACKAGE_NAME = "test";

	@Test
	public void specificationTestsList() {
		int nbFailures = 0;
		// Itération sur les fichiers source
		final File theSourceRootDir = new File("src/main/java/net/violet/platform/api/actions/");
		for (final File thePackageDir : theSourceRootDir.listFiles()) {
			if (thePackageDir.isDirectory()) {
				final String thePackageDirName = thePackageDir.getName();

				if (!GetTest.TEST_PACKAGE_NAME.equals(thePackageDirName)) {
					for (final File theSourceFile : thePackageDir.listFiles()) {
						if (theSourceFile.isFile()) {
							final String theSourceFileName = theSourceFile.getName();
							if (theSourceFileName.endsWith(".java")) {
								final int theLen = theSourceFileName.length();
								final String theClassName = "net.violet.platform.api.actions." + thePackageDirName + "." + theSourceFileName.substring(0, theLen - 5);
								final String theMethodName = "violet." + thePackageDirName + "." + StringUtils.uncapitalize(theSourceFileName.substring(0, theLen - 5));
								try {
									final Class theClass = Class.forName(theClassName);
									if ((theClass.getModifiers() & Modifier.ABSTRACT) == 0) {
										final File theExternalFile = new File("src/test/resources/probes/api/methods.get_" + theMethodName + ".test");
										if (theExternalFile.exists()) {
											System.out.println("Test for method " + theMethodName + " exists");
										} else {
											System.out.println("FAILURE: Test for method " + theMethodName + " doesn't exist!");
											nbFailures++;
										}
									}
								} catch (final ClassNotFoundException e) {
									Assert.fail("ClassNotFound: " + theClassName);
								}
							}
						}
					}
				}
			}
		}

		if (nbFailures > 0) {
			Assert.fail(nbFailures + " specification tests are missing");
		}
	}
}
