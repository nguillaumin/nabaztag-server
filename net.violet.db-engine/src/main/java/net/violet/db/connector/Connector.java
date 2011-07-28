package net.violet.db.connector;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import javax.sql.DataSource;

import net.violet.db.DBConstantes;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.mysql.jdbc.CommunicationsException;

public final class Connector {


	private static final Logger LOGGER = Logger.getLogger(Connector.class);

	private static final Map<Connector.ConnectionMode, Map<ACCESS_MODE, Connector>> MAP_INSTANCE = new HashMap<Connector.ConnectionMode, Map<ACCESS_MODE, Connector>>();

	public enum ConnectionMode {
		DEFAULT,
		TOMCAT,
		POOL,
		POOL_INITIALIZED,
		DIRECT,
		LOCAL,
		TEST_DEBUG,
		TEST_NODB,
		EJABBERD,
		STATS
	}

	public static enum ACCESS_MODE {
		READ(DBConstantes.URL_DBCORE_R, DBConstantes.USER_DB_R, DBConstantes.PASSWORD_DB_R, DBConstantes.CONTEXT_DB_R),
		WRITE(DBConstantes.URL_DBCORE_W, DBConstantes.USER_DB_W, DBConstantes.PASSWORD_DB_W, DBConstantes.CONTEXT_DB_W);

		private final String mUrl;
		private final String mUser;
		private final String mPassword;
		private final String mContext;

		private ACCESS_MODE(String inUrl, String inUser, String inPassWord, String inContext) {
			this.mUrl = inUrl;
			this.mUser = inUser;
			this.mPassword = inPassWord;
			this.mContext = inContext;
		}

		public String getUrl() {
			return this.mUrl;
		}

		public String getUser() {
			return this.mUser;
		}

		public String getPassword() {
			return this.mPassword;
		}

		public String getContext() {
			return this.mContext;
		}
	}

	private ConnectionMode mConnectionMode;

	private DataSource mCoreDataSource;

	private final ACCESS_MODE mAccessMode;

	public static Connector getInstance(Connector.ConnectionMode inConnectionMode, ACCESS_MODE inAccess) {

		Connector theCurrentConnecteur = null;
		Map<ACCESS_MODE, Connector> theConnecteurMap = Connector.MAP_INSTANCE.get(inConnectionMode);
		if (theConnecteurMap == null) { //check once, during the instantiation of the connector
			synchronized (Connector.MAP_INSTANCE) {
				theConnecteurMap = Connector.MAP_INSTANCE.get(inConnectionMode);
				if (theConnecteurMap == null) {
					theConnecteurMap = new HashMap<ACCESS_MODE, Connector>();
					theConnecteurMap.put(inAccess, new Connector(inConnectionMode, inAccess));
					Connector.MAP_INSTANCE.put(inConnectionMode, theConnecteurMap);
				}
			}
		}
		theCurrentConnecteur = theConnecteurMap.get(inAccess);
		if (theCurrentConnecteur == null) { //check once, during the instantiation of the connector in read or write
			synchronized (theConnecteurMap) {
				theCurrentConnecteur = theConnecteurMap.get(inAccess);
				if (theCurrentConnecteur == null) {
					theCurrentConnecteur = new Connector(inConnectionMode, inAccess);
					theConnecteurMap.put(inAccess, theCurrentConnecteur);
				}
			}
		}
		return theCurrentConnecteur;
	}

	private Connector() {
		this(ConnectionMode.DEFAULT, ACCESS_MODE.READ);
	}

	private Connector(ConnectionMode inConnectionMode, ACCESS_MODE inAccess) {
		this.mConnectionMode = inConnectionMode;
		this.mAccessMode = inAccess;
	}

	private void setDriverConnection() {
		try {
			Class.forName(DBConstantes.CLASS_DB);
		} catch (final ClassNotFoundException cnfException) {
			Connector.LOGGER.fatal(cnfException, cnfException);
		}
	}

	public Connection getConnection() throws SQLException {
		Connection cnx = null;
		switch (this.mConnectionMode) {
		case DEFAULT:
			cnx = getConnectionTomcat();
			if (cnx != null) {
				this.mConnectionMode = ConnectionMode.TOMCAT;
			} else {
				// C'est juste qu'on n'est pas démarré via Tomcat.
				setDriverConnection();
				try {
					cnx = getConnectionPool();
					this.mConnectionMode = ConnectionMode.POOL_INITIALIZED;
				} catch (final CommunicationsException aCException) {
					cnx = getConnectionLocal();
					this.mConnectionMode = ConnectionMode.LOCAL;
				}
			}
			break;

		case TOMCAT:
			cnx = getConnectionTomcat();
			break;

		case POOL:
			cnx = getConnectionPool();
			this.mConnectionMode = ConnectionMode.POOL_INITIALIZED;
			break;

		case POOL_INITIALIZED:
			cnx = getConnectionPoolInitialized();
			break;

		case DIRECT:
			cnx = getConnectionDirect();
			break;

		case LOCAL:
			cnx = getConnectionLocal();
			break;

		case TEST_DEBUG:
			cnx = getConnectionDBTest();
			break;

		case EJABBERD:
			setDriverConnection();
			cnx = getConnectionPool();
			this.mConnectionMode = ConnectionMode.POOL_INITIALIZED;
			break;
		case STATS:
			setDriverConnection();
			cnx = getConnectionPool();
			this.mConnectionMode = ConnectionMode.POOL_INITIALIZED;
			break;

		case TEST_NODB:
			throw new UnsupportedOperationException();
		}
		return cnx;
	}

	public Connection getConnectionTomcat() throws SQLException {
		Connection cnx = null;
		try {
			DataSource theDataSource;

			theDataSource = this.mCoreDataSource;

			if ((theDataSource == null)) {
				Context ctx = new InitialContext();
				ctx = (Context) ctx.lookup("java:comp/env");

				theDataSource = (DataSource) ctx.lookup(this.mAccessMode.mContext);
				this.mCoreDataSource = theDataSource;
			}
			cnx = theDataSource.getConnection();
		} catch (final NoInitialContextException anException) {
			// Tout va bien, on n'est pas lancé via tomcat.
		} catch (final NamingException ne) {
			Connector.LOGGER.fatal(ne, ne);
		}
		return cnx;
	}

	/**
	 * Méthode pour récupérer la connexion, lorsque le pool est déjà initialisé.
	 * 
	 * @return la connexion
	 * @throws SQLException
	 */
	public Connection getConnectionPoolInitialized() throws SQLException {
		DataSource theDataSource;

		theDataSource = this.mCoreDataSource;

		final Connection cnx = theDataSource.getConnection();
		return cnx;
	}

	/**
	 * Méthode pour récupérer la connexion, lorsque le pool n'est pas
	 * (forcément) initialisé.
	 * 
	 * @return la connexion
	 * @throws SQLException
	 */
	public Connection getConnectionPool() throws SQLException {
		synchronized (Connector.class) {
			if ((this.mCoreDataSource == null)) {

				switch (this.mConnectionMode) {

				case EJABBERD:
					setupDataSources(DBConstantes.USER_DBEJABBERD, DBConstantes.PASSWORD_DBEJABBERD, DBConstantes.URL_DBEJABBERD);
					break;

				case STATS:
					setupDataSources(DBConstantes.USER_STATS, DBConstantes.PASSWORD_STATS, DBConstantes.URL_STATS);
					break;

				default:
					setupDataSources(this.mAccessMode.mUser, this.mAccessMode.mPassword, this.mAccessMode.mUrl);
					break;
				}

			}
		}
		DataSource theDataSource;

		theDataSource = this.mCoreDataSource;

		final Connection cnx = theDataSource.getConnection();
		return cnx;
	}

	private void setupDataSources(String inUserDB, String inPasswordDB, String inUrlDB) {
		this.mCoreDataSource = Connector.createDataSource(inUserDB, inPasswordDB, inUrlDB);
	}

	private static DataSource createDataSource(String inUserDB, String inPasswordDB, String inUrlDB) {
		final BasicDataSource theResult = new BasicDataSource();
		theResult.setDriverClassName(DBConstantes.CLASS_DB);
		theResult.setUsername(inUserDB);
		theResult.setPassword(inPasswordDB);
		theResult.setTimeBetweenEvictionRunsMillis(500);
		theResult.setMinEvictableIdleTimeMillis(3500000); // 58 minutes. ( mise a cette valeur pour le firewall de TI)
		theResult.setUrl(inUrlDB);
		theResult.setMinIdle(15);
		theResult.setMaxIdle(40);
		theResult.setMaxActive(500);
		theResult.setTestWhileIdle(true);
		theResult.setValidationQuery("select 1");
		return theResult;
	}

	public Connection getConnectionDirect() throws SQLException {

		setDriverConnection();

		return DriverManager.getConnection(this.mAccessMode.getUrl(), this.mAccessMode.getUser(), this.mAccessMode.getPassword());

	}

	public Connection getConnectionLocal() throws SQLException {

		setDriverConnection();

		return DriverManager.getConnection(DBConstantes.URL_DB_CORE_LOCAL, DBConstantes.USER_DB, DBConstantes.PASSWORD_DB);
	}

	public Connection getConnectionDBTest() throws SQLException {

		setDriverConnection();

		return DriverManager.getConnection(DBConstantes.URL_DBTESTCORE, DBConstantes.USER_DB, DBConstantes.PASSWORD_DB);
	}

	//Use for Junit test
	public static Map<Connector.ConnectionMode, Map<ACCESS_MODE, Connector>> getInstanceMap() {
		return Connector.MAP_INSTANCE;
	}

	//Use for Junit test
	public ConnectionMode getConnectionMode() {
		return this.mConnectionMode;
	}

	//Use for Junit test
	public ACCESS_MODE getAccessMode() {
		return this.mAccessMode;
	}
}
