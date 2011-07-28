package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Categ;
import net.violet.platform.datamodel.CategImpl;
import net.violet.platform.datamodel.factories.CategFactory;

import org.apache.log4j.Logger;

public class CategFactoryImpl extends RecordFactoryImpl<Categ, CategImpl> implements CategFactory {


	private static final Logger LOGGER = Logger.getLogger(CategFactoryImpl.class);

	CategFactoryImpl() {
		super(CategImpl.SPECIFICATION);
	}

	public Categ createNewCateg(long id) {
		try {
			new CategImpl(id);
		} catch (final SQLException e) {
			CategFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public List<Categ> findAll() {
		return findAll(null, Collections.emptyList(), "categ_type");
	}

	public Categ findByName(String inType) {

		return find(" categ_type = ? ", Arrays.asList(new Object[] { inType }));
	}
}
