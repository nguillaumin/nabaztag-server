package net.violet.platform.datamodel.factories.mock;

import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Black;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.BlackFactory;
import net.violet.platform.datamodel.mock.BlackMock;

public class BlackFactoryMock extends RecordFactoryMock<Black, BlackMock> implements BlackFactory {

	public BlackFactoryMock() {
		super(BlackMock.class);
	}

	public void createNewBlack(User inUser, User inBlack) {
		final Black blacked = new BlackMock(inUser, inBlack, net.violet.common.StringShop.EMPTY_STRING);
		inUser.getBlackList().put(inBlack, blacked);
	}

	public void removeFromBlackList(User inUser, User inBlacked) {
		for (final Black black : findAllMapped().values()) {
			if ((black.getBlacked().getId().equals(inBlacked.getId())) && (inUser.getId().equals(black.getUser().getId()))) {
				black.delete();
				inUser.getBlackList().remove(inBlacked);
			}
		}
	}

	public List<Black> whoBlackListedMe(User inBlacked) {
		final LinkedList<Black> black = new LinkedList<Black>();
		black.addAll(inBlacked.getBlackList().values());
		return black;
	}

}
