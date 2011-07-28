package net.violet.platform.datamodel.factories.implementations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.SgbdConnection;
import net.violet.db.records.SgbdResult;
import net.violet.db.records.SgbdConnection.SGBD_ACCESS;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.NabcastCateg;
import net.violet.platform.datamodel.NabcastCategImpl;
import net.violet.platform.datamodel.factories.NabcastCategFactory;

import org.apache.log4j.Logger;

public class NabcastCategFactoryImpl extends RecordFactoryImpl<NabcastCateg, NabcastCategImpl> implements NabcastCategFactory {

	private static final Logger LOGGER = Logger.getLogger(NabcastCategFactoryImpl.class);

	public NabcastCategFactoryImpl() {
		super(NabcastCategImpl.SPECIFICATION);
	}

	public List<NabcastCateg> getNabcastCateg(long lang) {
		final List<NabcastCateg> list = new ArrayList<NabcastCateg>();
		try {
			final String prefix = NabcastCategImpl.SPECIFICATION.getTablePrefix();
			final List<Object> inVals = new ArrayList<Object>();
			String query = "select " + "nabcastcateg_id as " + prefix + "nabcastcateg_id," + "nabcastcateg_val as " + prefix + "nabcastcateg_val," + "nabcastcateg_lang as " + prefix + "nabcastcateg_lang," + "nabcastcateg_desc as " + prefix + "nabcastcateg_desc" + " from nabcastcateg where nabcastcateg_id in ";

			// Ajout de Thierry
			String subquery = "select nabcast_categ from nabcast where nabcast_private < ?";
			inVals.add(1);

			if (lang > 0) {
				subquery = "(" + subquery + " AND nabcast_lang= ?)";
				query += subquery + " and nabcastcateg_lang= ?";
				inVals.add(lang);
				inVals.add(lang);
			} else {
				query += "(" + subquery + ")";
			}

			query += "  order by nabcastcateg_id";
			final SgbdConnection theConnection = new SgbdConnection(SGBD_ACCESS.READONLY);
			try {
				final SgbdResult theSgbdResult = theConnection.doQueryPS(query, inVals, 0, false);
				try {
					final ResultSet theResultSet = theSgbdResult.getResultSet();
					while (theResultSet.next()) {
						list.add(NabcastCategImpl.doCreateObject(theResultSet));
					}
				} finally {
					theSgbdResult.close();
				}
			} finally {
				theConnection.close();
			}
		} catch (final SQLException se) {
			NabcastCategFactoryImpl.LOGGER.fatal(se, se);
		}
		return list;
	}

	public NabcastCateg findByName(String name) {
		return NabcastCategImpl.doFindByName(name);
	}

	/**
	 * Accesseur à partir de la LangImpl
	 * 
	 * @param lang lang dans laquelle on veut la liste
	 * @return la liste des pays
	 * @throws SQLException
	 */
	public List<NabcastCateg> findAllByLang(Lang inLang) {
		final List<NabcastCateg> list = new ArrayList<NabcastCateg>();
		try {
			list.addAll(NabcastCategImpl.doFindAllByLang(inLang));
		} catch (final SQLException e) {
			NabcastCategFactoryImpl.LOGGER.fatal(e, e);
		}
		return list;
	}
}
