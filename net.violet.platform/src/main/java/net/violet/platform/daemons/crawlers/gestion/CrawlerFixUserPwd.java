package net.violet.platform.daemons.crawlers.gestion;

import java.sql.SQLException;

import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserPwd;
import net.violet.platform.datamodel.UserPwdImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.VObjectImpl;

import org.apache.log4j.Logger;

public class CrawlerFixUserPwd extends AbstractCrawler {

	private static final Logger LOGGER = Logger.getLogger(CrawlerFixUserPwd.class);

	private final VObjectImpl.RecordWalker<VObject> mWalker;

	public CrawlerFixUserPwd(String[] inArgs) {
		super(inArgs);
		this.mWalker = new VObjectImpl.RecordWalker<VObject>() {

			public void process(VObject inObject) {
				processObject(inObject);
			}
		};
	}

	private void processObject(VObject inObject) {

		final String theNabname = inObject.getObject_login();
		final User theOwner = inObject.getOwner();
		if (theOwner != null) {
			final UserPwd theUserPwd = UserPwdImpl.findByPseudoAndUser(theNabname, theOwner);
			if (theUserPwd == null) { // we lost parameters for myNabaztag website
				try {
					new UserPwdImpl(theOwner.getId(), theNabname, theOwner.getPassword());
				} catch (final SQLException e) {
					CrawlerFixUserPwd.LOGGER.fatal("Failed : " + theNabname, e);
				}
				CrawlerFixUserPwd.LOGGER.info(" Process : " + theNabname);
			} else {
				CrawlerFixUserPwd.LOGGER.info(" OK : " + theNabname);
			}
		}
	}

	@Override
	protected void process() {
		final int nbProcessed = VObjectImpl.walkOnNabaztagObject(this.mWalker);
		CrawlerFixUserPwd.LOGGER.info(" total : " + nbProcessed);
	}
}
