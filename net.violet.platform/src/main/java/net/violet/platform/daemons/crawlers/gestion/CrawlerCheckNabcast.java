package net.violet.platform.daemons.crawlers.gestion;

import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.NabcastImpl;
import net.violet.platform.datamodel.NabcastVal;
import net.violet.platform.datamodel.NabcastValImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Templates;

import org.apache.log4j.Logger;

//permet de gérer les nabcast
//Celui ci est lancer par un cron tous les jours à 12h30

public class CrawlerCheckNabcast extends AbstractCrawler {


	private static final Logger LOGGER = Logger.getLogger(CrawlerCheckNabcast.class);

	/**
	 * Walker sur les NabcastImpl.
	 */
	private final NabcastImpl.RecordWalker<Nabcast> mWalker;

	public CrawlerCheckNabcast(String[] inArgs) {
		super(inArgs);

		this.mWalker = new NabcastImpl.RecordWalker<Nabcast>() {

			public void process(Nabcast inNabcast) {
				processNabcast(inNabcast);
			}
		};
	}

	private void processNabcast(Nabcast inNabcast) {
		try {

			// check les nabcasts non updatés
			final long nabcast_id = inNabcast.getId();
			final String nabcast_title = inNabcast.getNabcast_title();
			int nabcast_update = inNabcast.getNabcast_update(); // nombre de jours de non update
			final User nabcast_author = inNabcast.getAuthor();

			final NabcastVal theNabcastVal = NabcastValImpl.findNabcastPostIntoOneDay(nabcast_id);

			if (theNabcastVal != null) { // il y a eu un post sous les 24 heures
				if (nabcast_update > 0) {// remet à 0 le compteur
					CrawlerCheckNabcast.LOGGER.info("good update: " + nabcast_id);
					inNabcast.setMailNotified(0);
					inNabcast.setUpdate(0);
					inNabcast.setPrivate(0);
				}
			} else {
				nabcast_update = nabcast_update + 1;

				inNabcast.setUpdate(nabcast_update);

				if (nabcast_update == 15) // send firstmail
				{
					inNabcast.setMailNotified(CCalendar.getCurrentTimeInSecond()); // update time first mail
					Templates.nabcastNotUpdated(nabcast_author, nabcast_title);
					CrawlerCheckNabcast.LOGGER.info("send first mail 15 : " + nabcast_id);
				} else if (nabcast_update == 17)// passe le nabcast en privé 2
				// jours après
				{
					CrawlerCheckNabcast.LOGGER.info("private 17: " + nabcast_id);
					inNabcast.setPrivate(1); // nabcast passe en private
				} else if (nabcast_update == 30)// send lastmail TODO mettre
				// nouveau template
				{
					inNabcast.setMailNotified(CCalendar.getCurrentTimeInSecond()); // update time last mail
					Templates.nabcastNotUpdated(nabcast_author, nabcast_title);
					CrawlerCheckNabcast.LOGGER.info("send last mail 30: " + nabcast_id);
				}
			}
		} catch (final Throwable aThrowable) {
			CrawlerCheckNabcast.LOGGER.fatal(aThrowable, aThrowable);
		}
	}

	@Override
	protected void process() {
		final int nbrNabcast = NabcastImpl.walkNabcastActive(this.mWalker);
		CrawlerCheckNabcast.LOGGER.info(" total : " + nbrNabcast);
	}
}
