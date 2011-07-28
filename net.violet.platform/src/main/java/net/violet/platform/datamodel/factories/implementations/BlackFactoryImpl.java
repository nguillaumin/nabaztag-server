package net.violet.platform.datamodel.factories.implementations;

import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Black;
import net.violet.platform.datamodel.BlackImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.BlackFactory;

public class BlackFactoryImpl extends RecordFactoryImpl<Black, BlackImpl> implements BlackFactory {

	public BlackFactoryImpl() {
		super(BlackImpl.SPECIFICATION);
	}

	public void createNewBlack(User inUser, User inBlacked) {
		final Black blacked = new BlackImpl(inUser, inBlacked, net.violet.common.StringShop.EMPTY_STRING);
		inUser.getBlackList().put(inBlacked, blacked);
	}

	public void removeFromBlackList(User inUser, User inBlacked) {
		inUser.getBlackList().remove(inBlacked);
	}

	public List<Black> whoBlackListedMe(User inBlacked) {
		final String condition = "black_blacked = ?";
		return findAll(condition, Arrays.asList(new Object[] { inBlacked.getId() }));
	}

}
