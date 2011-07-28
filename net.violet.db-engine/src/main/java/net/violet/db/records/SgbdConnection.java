package net.violet.db.records;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import net.violet.common.StringShop;
import net.violet.db.connector.Connector;
import net.violet.db.connector.Connector.ACCESS_MODE;
import net.violet.db.connector.Connector.ConnectionMode;
import net.violet.probes.ProbesHandler;

import org.apache.log4j.Logger;

public class SgbdConnection {

	public interface ResultSetWorker {

		Object process(ResultSet inResultSet) throws SQLException;
	}

	private static final Logger LOGGER = Logger.getLogger(SgbdConnection.class);

	public static Connector.ConnectionMode CONNECTION_MODE = ConnectionMode.DEFAULT;

	public static enum SGBD_ACCESS {
		READWRITE(ACCESS_MODE.READ, ACCESS_MODE.WRITE), READONLY(ACCESS_MODE.READ, null), WRITEONLY(null, ACCESS_MODE.WRITE);

		private final ACCESS_MODE mReadAccess;
		private final ACCESS_MODE mWriteAccess;

		private SGBD_ACCESS(ACCESS_MODE inReadAccess, ACCESS_MODE inWriteAccess) {
			this.mReadAccess = inReadAccess;
			this.mWriteAccess = inWriteAccess;
		}

		public ACCESS_MODE getReadAccess() {
			return this.mReadAccess;
		}

		public ACCESS_MODE getWriteAccess() {
			return this.mWriteAccess;
		}
	}

	/**
	 * Référence sur la connexion.
	 */
	private Connection readConnection;
	private Connection writeConnection;

	public SgbdConnection() {
		this(SgbdConnection.CONNECTION_MODE, SGBD_ACCESS.READWRITE);
	}

	public SgbdConnection(SGBD_ACCESS inAccess) {
		this(SgbdConnection.CONNECTION_MODE, inAccess);
	}

	public SgbdConnection(Connector.ConnectionMode inConnectionMode, SGBD_ACCESS inAccess) {

		if (inAccess.getWriteAccess() != null) {
			this.writeConnection = SgbdConnection.getConnection(inConnectionMode, inAccess.getWriteAccess());
		}

		if (inAccess.getReadAccess() != null) {
			this.readConnection = SgbdConnection.getConnection(inConnectionMode, inAccess.getReadAccess());
		}
	}

	private static Connection getConnection(Connector.ConnectionMode inConnectionMode, Connector.ACCESS_MODE inAccess) {
		Connection theConnection = null;
		try {
			final long theTime = System.currentTimeMillis();
			final Connector theConnecteur = Connector.getInstance(inConnectionMode, inAccess);
			theConnection = theConnecteur.getConnection();
			final long theNewTime = System.currentTimeMillis();
			if (theNewTime - theTime > 1000) {
				SgbdConnection.LOGGER.info("CONNEXION EN PLUS D'UNE SECONDE: " + (theNewTime - theTime) + "ms");
			}
		} catch (final SQLException e) {
			SgbdConnection.LOGGER.fatal(e, e);
		}

		if (theConnection != null) {
			ProbesHandler.SGBDCONNECTION.add();
		}

		return theConnection;
	}

	public int doQueryIntV(String queryTmp) {
		int result = 0;
		ResultSet resultSet = null;
		try {
			if (this.readConnection != null) {
				final Statement statement = this.readConnection.createStatement();
				resultSet = statement.executeQuery(queryTmp);
				if (resultSet.next()) {
					result = resultSet.getInt(1);
				}
				statement.close();
			} else {
				throw new SQLException("This operation [select] is not permit, the connection is in WriteOnly mode");
			}
		} catch (final SQLException e) {
			SgbdConnection.LOGGER.fatal(e, e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (final SQLException e) {
				SgbdConnection.LOGGER.fatal(e, e);
			}
		}
		return result;
	}

	/**
	 * Return the second (if available) result of the query, the first one
	 * otherwise
	 * 
	 * @param queryTmp
	 * @return the result of the query
	 */
	public String doQueryString(String queryTmp) {
		String result = StringShop.EMPTY_STRING;
		ResultSet resultSet = null;
		try {
			if (this.readConnection != null) {
				final Statement statement = this.readConnection.createStatement();
				resultSet = statement.executeQuery(queryTmp);
				for (int i = 0; i < 2; i++) {
					if (resultSet.next()) {
						result = resultSet.getString(1);
					}
				}
				statement.close();
			} else {
				throw new SQLException("This operation [select] is not permit, the connection is in WriteOnly mode");
			}
		} catch (final SQLException e) {
			SgbdConnection.LOGGER.fatal(e, e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (final SQLException e) {
				SgbdConnection.LOGGER.fatal(e, e);
			}
		}
		return result;
	}

	public int doQueryUpdate(String queryTmp) {
		int result = -1;

		try {
			if (this.writeConnection != null) {
				final Statement statement = this.writeConnection.createStatement();
				result = statement.executeUpdate(queryTmp);
				statement.close();
			} else {
				throw new SQLException("This operation [update] is not permit, the connection is in ReadOnly mode");
			}
		} catch (final SQLException e) {
			System.out.println("Erreur Suppression : " + e);
			SgbdConnection.LOGGER.fatal(e, e);
		}
		return result;
	}

	/**
	 * Fait une requête update avec un prepared statement.
	 * 
	 * @param inQuery requête pour l'update.
	 * @param inVals valeurs pour le prepared statement.
	 * @return le nombre de rangs concernés.
	 * @throws SQLException si un problème est survenu.
	 */
	public int doQueryUpdatePS(String inQuery, List<Object> inVals) throws SQLException {
		if (this.writeConnection != null) {
			final long before = System.currentTimeMillis();
			final PreparedStatement pstmt = this.writeConnection.prepareStatement(inQuery);
			int index = 1;
			final StringBuilder theValsBuf = new StringBuilder();
			for (final Object theVal : inVals) {
				pstmt.setObject(index, theVal);
				index++;
				theValsBuf.append(theVal + StringShop.COMMA);
			}

			final int theResult = pstmt.executeUpdate();
			pstmt.close();
			final long after = System.currentTimeMillis();
			ProbesHandler.SGBDCONNECTION.registerQuery(after - before, inQuery, inVals);
			return theResult;
		}
		throw new SQLException("This operation [update] is not permit, the connection is in ReadOnly mode");
	}

	/**
	 * Fait une requete select avec un prepared statement.
	 * 
	 * @param inQuery requete pour le select.
	 * @param inVals valeurs pour le prepared statement.
	 * @return ResultSet
	 * @throws SQLException si un probleme est survenu.
	 */
	//TODO do not remove this method!!! will be used on January
	public SgbdResult doQueryPS(String inQuery, List<Object> inVals, int inFetchSize) throws SQLException {
		if (this.readConnection != null) {
			final long before = System.currentTimeMillis();
			final PreparedStatement pstmt = this.readConnection.prepareStatement(inQuery);
			pstmt.setFetchSize(inFetchSize);
			int index = 1;
			if (inVals != null) {
				for (final Object theVal : inVals) {
					pstmt.setObject(index, theVal);
					index++;
				}
			}

			final ResultSet rs = pstmt.executeQuery();
			final long after = System.currentTimeMillis();
			ProbesHandler.SGBDCONNECTION.registerQuery(after - before, inQuery, inVals);
			return new SgbdResult(rs, pstmt);
		}
		throw new SQLException("This operation [select] is not permit, the connection is in WriteOnly mode");
	}

	/**
	 * Fait une requete select avec un prepared statement.
	 * 
	 * @param inQuery requete pour le select.
	 * @param inVals valeurs pour le prepared statement.
	 * @return ResultSet
	 * @throws SQLException si un probleme est survenu.
	 */
	public Object doQueryPS(String inQuery, List<Object> inVals, ResultSetWorker inWorker, int inFetchSize) throws SQLException {
		if (this.readConnection != null) {
			final long before = System.currentTimeMillis();
			final PreparedStatement pstmt = this.readConnection.prepareStatement(inQuery);
			pstmt.setFetchSize(inFetchSize);
			int index = 1;
			if (inVals != null) {
				for (final Object theVal : inVals) {
					pstmt.setObject(index, theVal);
					index++;
				}
			}

			final ResultSet theResultSet = pstmt.executeQuery();
			final long after = System.currentTimeMillis();
			ProbesHandler.SGBDCONNECTION.registerQuery(after - before, inQuery, inVals);
			final Object theResult = inWorker.process(theResultSet);
			theResultSet.close();
			pstmt.close();
			return theResult;
		}
		throw new SQLException("This operation [select] is not permit, the connection is in WriteOnly mode");
	}

	/**
	 * Execute une requete SQL (select). En cas d'erreur, lève une exception
	 * avec la requête dans le message.
	 * 
	 * @param queryTmp requete a executer.
	 * @return le result set (en cas de succès)
	 * @throws SQLException en cas d'erreur.
	 */
	public SgbdResult doQuery(String queryTmp) throws SQLException {
		try {
			if (this.readConnection != null) {
				final Statement statement = this.readConnection.createStatement();
				SgbdConnection.LOGGER.debug("Query : " + queryTmp);
				return new SgbdResult(statement.executeQuery(queryTmp), statement);
			}
			throw new SQLException("This operation [select] is not permit, the connection is in WriteOnly mode");
		} catch (final SQLException anException) {
			final SQLException theNewException = new SQLException("ERROR SQL : query (" + queryTmp + ")  logs:" + anException.getMessage());
			theNewException.initCause(anException);
			throw theNewException;
		}
	}

	public int doQueryInsertPS(String inQuery, List<Object> inVals) {
		final long before = System.currentTimeMillis();
		int result = -1;

		try {
			if (this.writeConnection != null) {
				final PreparedStatement theStatement = this.writeConnection.prepareStatement(inQuery);
				int index = 1;
				for (final Object theVal : inVals) {
					theStatement.setObject(index, theVal);
					index++;
				}
				result = theStatement.executeUpdate();
				theStatement.close();
			} else {
				throw new SQLException("This operation [insert] is not permit, the connection is in ReadOnly mode");
			}
		} catch (final SQLException e) {
			SgbdConnection.LOGGER.fatal(e, e);
		}
		final long after = System.currentTimeMillis();
		ProbesHandler.SGBDCONNECTION.registerQuery(after - before, inQuery, inVals);
		return result;
	}

	/**
	 * Ins�re un enregistrement et retourne les colonnes g�n�r�es
	 * automatiquement.
	 * 
	 * @param inQuery la requ�te d'insertion
	 * @param inVals les valeurs (pour le prepared statement)
	 * @return l'ensemble des colonnes g�n�r�es automatiquement.
	 * @throws SQLException 
	 */
	public SgbdResult doQueryInsertAutoGeneratedKeysPS(String inQuery, List<Object> inVals) throws SQLException {
		final long before = System.currentTimeMillis();
		SgbdResult theResult = null;

		PreparedStatement theStatement = null;
		try {
			if (this.writeConnection != null) {
				theStatement = this.writeConnection.prepareStatement(inQuery, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				for (final Object theVal : inVals) {
					theStatement.setObject(index, theVal);
					index++;
				}
				theStatement.executeUpdate();
				theResult = new SgbdResult(theStatement.getGeneratedKeys(), theStatement);
			} else {
				throw new SQLException("This operation [insert] is not permit, the connection is in ReadOnly mode");
			}
		} finally {
			if ((theResult == null) && (theStatement != null)) {
				try {
					theStatement.close();
				} catch (final SQLException anException) {
					SgbdConnection.LOGGER.fatal(anException, anException);
				}
			}
		}
		final long after = System.currentTimeMillis();
		ProbesHandler.SGBDCONNECTION.registerQuery(after - before, inQuery, inVals);
		return theResult;
	}

	public void close() {
		try {
			if (this.readConnection != null) {
				this.readConnection.close();
				ProbesHandler.SGBDCONNECTION.remove();
			}
			if (this.writeConnection != null) {
				this.writeConnection.close();
				ProbesHandler.SGBDCONNECTION.remove();
			}
		} catch (final SQLException ignore) { // just close your mouth..

		} finally {
			this.readConnection = null;
			this.writeConnection = null;
		}
	}

	//Use for Junit Test or other test
	public static void setConnectionMode(Connector.ConnectionMode inConnectionMode) {
		SgbdConnection.CONNECTION_MODE = inConnectionMode;
	}

	// TODO Special Case, will be changed on January
	@Deprecated
	public SgbdResult doQueryPS(String inQuery, List<Object> inVals, int inFetchSize, boolean readOnMasterDB) throws SQLException {
		final Connection theCurrentConnection;
		if (readOnMasterDB) {
			theCurrentConnection = this.writeConnection; // because delay of replication is too long, so reads on master db
		} else {
			theCurrentConnection = this.readConnection;
		}
		if (theCurrentConnection != null) {
			final long before = System.currentTimeMillis();
			final PreparedStatement pstmt = theCurrentConnection.prepareStatement(inQuery);
			pstmt.setFetchSize(inFetchSize);
			int index = 1;
			if (inVals != null) {
				for (final Object theVal : inVals) {
					pstmt.setObject(index, theVal);
					index++;
				}
			}

			final ResultSet rs = pstmt.executeQuery();
			final long after = System.currentTimeMillis();
			ProbesHandler.SGBDCONNECTION.registerQuery(after - before, inQuery, inVals);
			return new SgbdResult(rs, pstmt);
		}
		throw new SQLException("This operation [select] is not possible, the connection is null");
	}

	/**
	 * Use only for the Stats
	 * 
	 * @param inQuery requete pour le select.
	 * @param inVals valeurs pour le prepared statement.
	 * @return ResultSet
	 * @throws SQLException si un probleme est survenu.
	 */
	public int doQueryPS(String inQuery, List<Object> inVals) {
		int result = 0;
		if (this.readConnection != null) {
			final long before = System.currentTimeMillis();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = this.readConnection.prepareStatement(inQuery);
				int index = 1;
				if (inVals != null) {
					for (final Object theVal : inVals) {
						pstmt.setObject(index, theVal);
						index++;
					}
				}
				rs = pstmt.executeQuery();
				final long after = System.currentTimeMillis();
				ProbesHandler.SGBDCONNECTION.registerQuery(after - before, inQuery, inVals);
				if (rs.next()) {
					result = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (pstmt != null) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
