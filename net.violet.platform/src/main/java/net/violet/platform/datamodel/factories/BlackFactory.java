package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Black;
import net.violet.platform.datamodel.User;

public interface BlackFactory extends RecordFactory<Black> {

	/**
	 * Creates a new black for the given users. This method does NOT check if
	 * the main user is authorized to black the other one (i.e. it does NOT
	 * check if the user is trying to black itself)
	 * 
	 * @param inUser the main user (the one who blacks an other one)
	 * @param inBlacked the other user (the one who is blacked by the main user)
	 */
	void createNewBlack(User inUser, User inBlacked);

	void removeFromBlackList(User inUser, User inBlacked);

	List<Black> whoBlackListedMe(User inBlacked);

}
