package net.violet.platform.datamodel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.violet.common.StringShop;
import net.violet.db.cache.Cache;
import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.mock.FactoryMock;
import net.violet.platform.datamodel.mock.ApplicationCategoryMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.ApplicationProfileMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.RecordData;
import net.violet.platform.vasm.Vasm.VASM_FILE;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * Classe de base pour les tests qui utilisent les mocks.
 */
public class MockTestBase {

	private Lang mFrLang;
	private Lang mUsLang;
	private Lang mFrSiteLang;
	private Lang mEnSiteLang;
	private Timezone mParisTimezone = null;
	private User mPrivateUser;
	private User mKowalskyUser;
	private VObject mPrivateObject;
	private VObject mKowalskyObject;
	private VObject mBrewsterObject;
	private final Map<ApplicationClass, Application> mFirstApplications = new HashMap<ApplicationClass, Application>();
	private Application mSecondApplication;

	//Sur les tests lancés par 'silence' le classLoader est capricieux, alors on initialise ces deux variables ici!!
	//On est sûr que les mocks seront initialisés avant.
	private VASM_FILE mVasmFile;
	private Map<String, Object> mPojoParams;

	private static final String strJSONTest = "{\"string_here\":\"i am a string\",\"date\":\"2002-02-02T22:22:22Z\",\"int_value\":1,\"too_big\":4568971236548956,\"bool_et_bil\":true,\"pi\":3.14116,\"mainactor\":{\"id\":\"007\"},\"actors\":[\"laurel\",\"hardy\",\"john\"],\"frac\":0.3333333333333333,\"null_vall\":null,\"sequence\":[{\"annotation\":{\"state\":\"speaking\"}},{\"directive\":{\"settings\":{\"snd.btn.2\":\"blonk\"},\"disable\":[\"earMove\"]}},{\"scene\":{\"genre\":\"net.violet.tts.en\",\"text\":\"\\tHello \\\"Space Boy\\\"\\nCome Here !\",\"options\":{\"voice\":\"\"}}}]}";

	protected static void loadData() {
		for (final Field aField : Factories.class.getFields()) {
			try {
				final Object theObject = aField.get(null);
				final Method theMethod = theObject.getClass().getDeclaredMethod("loadCache");
				if (theMethod != null) {
					theMethod.invoke(theObject);
				}
			} catch (final Exception e) {}
		}
	}

	@Before
	public void setUp() {
		System.setProperty("net.violet.platform.datamodel.factories.impl", FactoryMock.class.getName());
		MockTestBase.loadData();
		this.mVasmFile = VASM_FILE.findByHardware(HARDWARE.V1);
		try {
			this.mPojoParams = ConverterFactory.JSON.convertFrom(MockTestBase.strJSONTest);
			this.mPojoParams.put("long_val", new Long(1234567));

		} catch (final ConversionException e) {
			e.printStackTrace();
			this.mPojoParams = Collections.emptyMap();
		}
	}

	@After
	public void tearDown() {
		AbstractMockRecord.clearForTest();
		RecordData.clearForTest();
		Cache.clearForTest();
		System.gc();
	}

	/**
	 * Récupère la langue objet fr.
	 * 
	 * @return la langue fr.
	 */
	public Lang getFrLang() {
		if (this.mFrLang == null) {
			this.mFrLang = Factories.LANG.findByIsoCode("fr-FR");
		}
		return this.mFrLang;
	}

	/**
	 * Récupère la langue site fr.
	 * 
	 * @return la langue fr.
	 */
	public Lang getSiteFrLang() {
		if (this.mFrSiteLang == null) {
			this.mFrSiteLang = Factories.LANG.findByIsoCode("fr");
		}
		return this.mFrSiteLang;
	}

	/**
	 * Récupère la langue objet us.
	 * 
	 * @return la langue us.
	 */
	public Lang getUsLang() {
		if (this.mUsLang == null) {
			this.mUsLang = Factories.LANG.findByIsoCode("en-US");
		}
		return this.mUsLang;
	}

	/**
	 * Récupère la langue site en.
	 * 
	 * @return la langue en.
	 */
	public Lang getSiteEnLang() {
		if (this.mEnSiteLang == null) {
			this.mEnSiteLang = Factories.LANG.findByIsoCode("en");
		}
		return this.mEnSiteLang;
	}

	/**
	 * Récupère la TZ Paris.
	 * 
	 * @return la TZ Paris.
	 */
	public Timezone getParisTimezone() {
		if (this.mParisTimezone == null) {
			this.mParisTimezone = Factories.TIMEZONE.findByJavaId("Europe/Paris");
		}
		return this.mParisTimezone;
	}

	/**
	 * Récupère l'utilisateur private.
	 * 
	 * @return l'utilisateur private.
	 */
	public User getPrivateUser() {
		if (this.mPrivateUser == null) {
			this.mPrivateUser = new UserMock(97238, "private", "12345", "gerard@violet.net", getUsLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);
		}
		return this.mPrivateUser;
	}

	/**
	 * Récupère l'utilisateur kowalsky.
	 * 
	 * @return l'utilisateur kowalsky.
	 */
	public User getKowalskyUser() {
		if (this.mKowalskyUser == null) {
			this.mKowalskyUser = new UserMock(90481, "kowalsky", "12345", "gerard@violet.net", getFrLang(), "France", "firstName", "lastName", getParisTimezone(), Annu.MALE, "75011", "Paris", 0);
		}
		return this.mKowalskyUser;
	}

	public VObject getPrivateObject() {
		if (this.mPrivateObject == null) {
			this.mPrivateObject = new VObjectMock(63643, "0013d3849ab6", "private", getPrivateUser(), HARDWARE.V2, getParisTimezone(), getUsLang());
		}
		return this.mPrivateObject;
	}

	public VObject getKowalskyObject() {
		if (this.mKowalskyObject == null) {
			this.mKowalskyObject = new VObjectMock(60463, "0019db001073", "kowalsky", getKowalskyUser(), HARDWARE.V2, getParisTimezone(), getFrLang());
		}
		return this.mKowalskyObject;
	}

	public VObject getBrewsterObject() {
		if (this.mBrewsterObject == null) {
			this.mBrewsterObject = new VObjectMock(31273, "00904B8C55C5", "brewster", getKowalskyUser(), HARDWARE.V1, getParisTimezone(), getFrLang());
		}
		return this.mBrewsterObject;
	}

	public VASM_FILE getVASM() {
		return this.mVasmFile;
	}

	public Map<String, Object> getPojoParams() {
		return this.mPojoParams;
	}

	public Application getMyFirstApplication() {
		return getMyFirstApplication(ApplicationClass.WEBUI);
	}

	public Application getMyFirstApplication(ApplicationClass inClass) {
		if (this.mFirstApplications.get(inClass) == null) {
			ApplicationCategory theCateg = Factories.APPLICATION_CATEGORY.findByName("test");
			if (theCateg == null) {
				theCateg = new ApplicationCategoryMock(2042, "test");
			}
			final Date now = new Date();
			this.mFirstApplications.put(inClass, new ApplicationMock(2042, "My first application", getKowalskyUser(), now, theCateg, inClass));
			new ApplicationProfileMock(2042, "First Application", "Description", true, new Date(), 1L, 1L, null, null, null, StringShop.EMPTY_STRING, null);
		}
		return this.mFirstApplications.get(inClass);
	}

	public Application getMySecondApplication() {
		if (this.mSecondApplication == null) {
			ApplicationCategory theCateg = Factories.APPLICATION_CATEGORY.findByName("test");
			if (theCateg == null) {
				theCateg = new ApplicationCategoryMock(2042, "test");
			}
			final Date now = new Date();
			this.mSecondApplication = new ApplicationMock(2043, "My second application", getKowalskyUser(), now, theCateg);
		}
		return this.mSecondApplication;
	}

	protected static void assertBinaryEquals(int[] inTargetBinary, byte[] inGeneratedBinary) {
		MockTestBase.assertBinaryEquals(0, 0, inTargetBinary, inGeneratedBinary);
	}

	protected static void assertBinaryEquals(int inOffset, int inPadding, int[] inTargetBinary, byte[] inGeneratedBinary) {
		final int nbElems = inTargetBinary.length;
		Assert.assertEquals(nbElems, inGeneratedBinary.length - inOffset, inPadding);
		for (int indexByte = 0; indexByte < nbElems; indexByte++) {
			int theTargetByte = inTargetBinary[indexByte];
			if (theTargetByte >= 128) {
				theTargetByte = theTargetByte - 256;
			}
			Assert.assertEquals("index = " + indexByte, theTargetByte, inGeneratedBinary[indexByte + inOffset]);
		}
	}
}
