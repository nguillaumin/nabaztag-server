package net.violet.mynabaztag.action;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.web.ServletTestBase;
import net.violet.platform.web.ServletTestRequest;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.config.ModuleConfigFactory;
import org.junit.Assert;
import org.junit.Before;

/**
 * Classe de base pour les tests des actions (struts).
 * 
 * truncat object_has_read_content; truncate object_has_scenario; truncate
 * scenario; truncate action; truncate object; truncate user; truncate annu;
 * truncate object_has_read_content; truncate object_has_scenario;
 * truncatescenario; truncate action; truncate object;
 */
public abstract class ActionTestBase extends ServletTestBase {

	private Files initFile;
	private final Set<Files> files = new HashSet<Files>();
	protected String fileName = "initFile.mp3";

	private User mActionTestUser;
	private User mActionTestUser2;
	private User mActionTestUser3;
	private User mActionTestUser4;

	@Before
	@Override
	public void setUp() {
		super.setUp();

		final Lang frLang = Factories.LANG.findByIsoCode("fr");
		this.mActionTestUser = new UserMock(1, "ActionTestUser", "123", "test_email@test.com", frLang, "test_country", "test_firstname", "test_lastname", getParisTimezone());
		new VObjectMock(1, "F00000300001", "ActionTestObject", this.mActionTestUser, HARDWARE.V2, getParisTimezone(), frLang);
		new VObjectMock(2, "F00000300002", "ActionTestObjectV1", this.mActionTestUser, HARDWARE.V1, getParisTimezone(), frLang);

		this.mActionTestUser2 = new UserMock(2, "ActionTestUser2", "123", "test_email@test.com", frLang, "test_country", "test_firstname", "test_lastname", getParisTimezone());
		new VObjectMock(3, "F00000300003", "ActionTestUser2", this.mActionTestUser2, HARDWARE.V2, getParisTimezone(), frLang);

		this.mActionTestUser3 = new UserMock(3, "ActionTestUser3", "123", "test_email@test.com", frLang, "test_country", "test_firstname", "test_lastname", getParisTimezone());
		new VObjectMock(4, "F00000300004", "ActionTestUser3", this.mActionTestUser3, HARDWARE.V2, getParisTimezone(), frLang);

		this.mActionTestUser4 = new UserMock(4, "ActionTestUser4", "123", "test_email@test.com", frLang, "test_country", "test_firstname", "test_lastname", getParisTimezone());
		this.initFile = createFile(this.fileName);
	}

	/**
	 * Redirection "login"
	 */
	protected static final ActionForward FORWARD_LOGIN = new ActionForward("login", "/login/", false);

	/**
	 * Redirection "input"
	 */
	protected static final ActionForward FORWARD_INPUT = new ActionForward("input", "/input/", false);

	/**
	 * Signature des méthodes appelées par struts.
	 */
	protected static final Class[] STRUTS_METHODS_SIGNATURE = new Class[] { ActionMapping.class, ActionForm.class, HttpServletRequest.class, HttpServletResponse.class, };

	/**
	 * Crée un ActionMapping.
	 */
	protected static ActionMapping createMapping() {
		final ActionMapping theMapping = new ActionMapping();
		// Parse the configuration for this module
		final ModuleConfigFactory factoryObject = ModuleConfigFactory.createFactory();
		final ModuleConfig config = factoryObject.createModuleConfig("WEB-INF/struts-config.xml");
		config.getControllerConfig().setInputForward(true);
		theMapping.setModuleConfig(config);
		theMapping.addForwardConfig(ActionTestBase.FORWARD_LOGIN);
		theMapping.addForwardConfig(ActionTestBase.FORWARD_INPUT);
		theMapping.setInput("input");
		theMapping.setParameter("dispatch");
		return theMapping;
	}

	/**
	 * Crée une session avec un utilisateur connecté.
	 */
	protected static HttpSession createUserSession(User inUser) {
		final HttpSession theSession = ServletTestBase.createEmptySession();
		theSession.setAttribute("id", inUser.getId());
		return theSession;
	}

	/**
	 * Crée une session avec un utilisateur connecté et un lapin.
	 */
	protected static HttpSession createUserRabbitSession(User inUser, VObject inRabbit) {
		final HttpSession theSession = ActionTestBase.createUserSession(inUser);
		theSession.setAttribute("idLapin", inRabbit.getId());
		return theSession;
	}

	/**
	 * Crée une requête avec une session avec un utilisateur connecté.
	 */
	protected static HttpServletRequest createUserSessionRequest(User inUser) {
		return ServletTestBase.createRequest(ActionTestBase.createUserSession(inUser));
	}

	/**
	 * Crée une requête avec une session avec un utilisateur connecté et un
	 * lapin.
	 */
	protected static HttpServletRequest createUserRabbitSessionRequest(User inUser, VObject inRabbit) {
		return ServletTestBase.createRequest(ActionTestBase.createUserRabbitSession(inUser, inRabbit));
	}

	/**
	 * Teste la redirection login en cas d'absence d'utilisateur.
	 */
	protected static <A extends Action, F extends ActionForm> void testSessionRedirect(Class<A> inActionClass, Class<F> inFormClass, String inMethod, HttpSession inSession, ActionForward inRedirect) {
		try {
			// Création de la form.
			final F theForm = inFormClass.newInstance();

			ActionTestBase.testSessionRedirect(inActionClass, theForm, inMethod, inSession, inRedirect);
		} catch (final Exception anException) {
			throw new IllegalArgumentException(anException);
		}
	}

	/**
	 * Teste la redirection login en cas d'absence d'utilisateur.
	 */
	protected static <A extends Action, F extends ActionForm> void testSessionRedirect(Class<A> inActionClass, F inForm, String inMethod, HttpSession inSession, ActionForward inRedirect) {
		try {
			// Création de l'action
			final A theAction = inActionClass.newInstance();
			// Création de la form.

			final ActionMapping theActionMapping = ActionTestBase.createMapping();
			// Identify the request parameter containing the method name
			final String parameter = theActionMapping.getParameter();
			final ServletTestRequest theRequest = ServletTestBase.createRequest(inSession);
			theRequest.setParameter(parameter, inMethod);

			final Object theResult = theAction.execute(theActionMapping, inForm, theRequest, ServletTestBase.createResponse());
			Assert.assertEquals(inRedirect, theResult);
		} catch (final Exception anException) {
			throw new IllegalArgumentException(anException);
		}
	}

	/**
	 * Teste la non redirection login en cas d'absence d'utilisateur.
	 */
	protected static <A extends Action, F extends ActionForm> void testRedirectEmptySession(Class<A> inActionClass, Class<F> inFormClass, String inMethod, ActionForward inRedirect) {
		ActionTestBase.testSessionRedirect(inActionClass, inFormClass, inMethod, ServletTestBase.createEmptySession(), inRedirect);
	}

	/**
	 * Teste la non redirection login en cas d'absence d'utilisateur.
	 */
	protected static <A extends Action, F extends ActionForm> void testRedirectEmptySession(Class<A> inActionClass, F inForm, String inMethod, ActionForward inRedirect) {
		ActionTestBase.testSessionRedirect(inActionClass, inForm, inMethod, ServletTestBase.createEmptySession(), inRedirect);
	}

	/**
	 * Teste la redirection login en cas d'absence d'utilisateur.
	 */
	protected <A extends Action, F extends ActionForm> void testRedirectNoRabbit(Class<A> inActionClass, Class<F> inFormClass, String inMethod, ActionForward inRedirect) {
		ActionTestBase.testSessionRedirect(inActionClass, inFormClass, inMethod, ActionTestBase.createUserSession(this.mActionTestUser), inRedirect);
	}

	/**
	 * Creates a new file using the given file name
	 * 
	 * @param inFileName
	 * 
	 * @return the new file
	 * 
	 * @throws SQLException
	 */
	protected Files createFile(String inFileName) {
		final Files myFile = new FilesMock(1, null, MimeType.MIME_TYPES.A_MPEG, inFileName);
		this.files.add(myFile);

		return myFile;
	}

	/**
	 * Creates a new music using the given sound path
	 * 
	 * @param owner
	 * @return the music
	 * @throws SQLException
	 */
	protected Music createMusic(User inOwner) {
		final Music myMusic = new MusicMock(1, this.initFile, "myMusic", inOwner, 89);

		return myMusic;
	}

	protected final void createFileById(long id, String filePath) {
		new FilesMock(id, null, MimeType.MIME_TYPES.A_MPEG, filePath);
	}

	protected final User getActionTestUser() {
		return this.mActionTestUser;
	}

	protected final User getActionTestUser2() {
		return this.mActionTestUser2;
	}

	protected final User getActionTestUser3() {
		return this.mActionTestUser3;
	}

	protected final User getActionTestUser4() {
		return this.mActionTestUser4;
	}
}
