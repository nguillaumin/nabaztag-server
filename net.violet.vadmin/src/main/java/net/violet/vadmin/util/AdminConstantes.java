package net.violet.vadmin.util;

import net.violet.common.utils.PropertiesTools;



public class AdminConstantes {

	public static String USER_EMAIL = "dev-js@violet.net";
	public static String USER_PASSWORD = "ouessant1900";
	
	private static final String DEFAULT_PATH = "admin.constante.properties";

	private static final PropertiesTools PROPERTIES = new PropertiesTools();

	static  {
		PROPERTIES.load(DEFAULT_PATH);
	}
	// 192.168.1.11, my.nabaztag.com
	public static final String DOMAIN = AdminConstantes.PROPERTIES.getProperties("DOMAIN");
	public static final String IMG_PATH = AdminConstantes.PROPERTIES.getProperties("IMG_PATH");
	public static final String OS_SERVER = AdminConstantes.PROPERTIES.getProperties("OS");
	public static final String USERS_PERMISSIONS_PATH = AdminConstantes.PROPERTIES.getProperties("USERS_PERMISSIONS_PATH");
	
	public static final String PATH = "/VAdmin";
	

}
