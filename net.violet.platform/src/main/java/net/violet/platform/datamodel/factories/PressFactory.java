package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Press;
import net.violet.platform.datamodel.Product;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface PressFactory extends RecordFactory<Press>, FilesAccessor {

	List<Press> getPressByLangAndProduct(Lang lang, Product product, int skip, int count);

	Press create(Lang record, String inTitle, String inAbstract, Files inPicture, String inUrl, Product inProduct);

}
