package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Press;
import net.violet.platform.datamodel.PressImpl;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.factories.PressFactory;

import org.apache.log4j.Logger;

public class PressFactoryImpl extends RecordFactoryImpl<Press, PressImpl> implements PressFactory {

	private static final Logger LOGGER = Logger.getLogger(PressFactoryImpl.class);

	PressFactoryImpl() {
		super(PressImpl.SPECIFICATION);
	}

	public List<Press> getPressByLangAndProduct(Lang inLang, Product inProduct, int inSkip, int inCount) {
		final String[] inJoinTables = { "lang", "product" };
		final String inOrderBy = null;
		final List<Object> values = Arrays.asList(new Object[] { inLang.getId(), inProduct.getId() });
		final String condition = "press.lang = lang.lang_id AND press.product = product.id AND lang.lang_id = ? AND product.id = ?";
		return findAll(inJoinTables, condition, values, inOrderBy, inSkip, inCount);
	}

	public Press create(Lang inLang, String inTitle, String inAbstract, Files inPicture, String inUrl, Product inProduct) {
		try {
			return new PressImpl(inLang, inTitle, inAbstract, inPicture, inUrl, inProduct);
		} catch (final SQLException e) {
			PressFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public boolean usesFiles(Files inFile) {
		//TODO Uncomment when the table will used
		//return count(null, " picture = ? ", Collections.singletonList((Object) inFile.getId()), null) > 0;
		return false;
	}
}
