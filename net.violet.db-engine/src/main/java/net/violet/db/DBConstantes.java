package net.violet.db;

public class DBConstantes {

	private static final DBPropertiesTools PROPERTIES = new DBPropertiesTools("db.constante.properties", true);

	// Constantes BDD de test
	public static final String URL_DBTESTCORE = DBConstantes.PROPERTIES.getProperties("URL_DBTESTCORE");

	// Constantes crawler et replicatedSgbdTools et connecteur
	public static final String URL_DBCORE = DBConstantes.PROPERTIES.getProperties("URL_DBCORE");
	public static final String URL_DBCORE_R = DBConstantes.PROPERTIES.getProperties("URL_DBCORE_R");
	public static final String URL_DBCORE_W = DBConstantes.PROPERTIES.getProperties("URL_DBCORE_W");

	// Constantes à la bd de présence des clients ejabberd
	public static final String URL_DBEJABBERD = DBConstantes.PROPERTIES.getProperties("URL_DBEJABBERD");

	// Adresse IP du serveur spread.
	public static final String SPREAD_HOST = DBConstantes.PROPERTIES.getProperties("SPREAD_HOST");

	// Temps d'attente entre les nettoyages du Cache
	public static final long TIME_TO_CLEAN = Long.parseLong(DBConstantes.PROPERTIES.getProperties("TIME_TO_CLEAN"));

	public static final int MAX_CACHE_ABSTRACT_RECORD_ENTRIES = Integer.parseInt(DBConstantes.PROPERTIES.getProperties("MAX_CACHE_ABSTRACT_RECORD_ENTRIES"));
	public static final int MAX_CACHE_DB_COLLECTION_ENTRIES = Integer.parseInt(DBConstantes.PROPERTIES.getProperties("MAX_CACHE_DB_COLLECTION_ENTRIES"));

	public static final String URL_STATS = DBConstantes.PROPERTIES.getProperties("URL_STATS");

	/*CONNECTEURS*/
	public static final String USER_DBEJABBERD = "ejabberdjava";
	public static final String PASSWORD_DBEJABBERD = "1b8d750b411a2a51640aef79ac6beeaa";

	public static final String USER_STATS = "dev";
	public static final String PASSWORD_STATS = "123";

	public static final String USER_DB = "dev";
	public static final String PASSWORD_DB = "123";
	public static final String CLASS_DB = "com.mysql.jdbc.Driver";

	public static final String USER_DB_W = "dev";
	public static final String PASSWORD_DB_W = "123";

	public static final String USER_DB_R = "dev";
	public static final String PASSWORD_DB_R = "123";

	public static final String CONTEXT_DB_R = "jdbc/bdCoreRead";
	public static final String CONTEXT_DB_W = "jdbc/bdCoreWrite";

	// URLs en local pour les test junit.
	public static final String URL_DB_CORE_LOCAL = "jdbc:mysql://127.0.0.1/bdCore?useUnicode=true&characterEncoding=UTF-8";

}
