package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface AnnuFactory extends RecordFactory<Annu>, FilesAccessor {

	Annu create(User user, String country, Lang lang);
}
