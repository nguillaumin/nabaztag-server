package net.violet.platform.xmpp.vo;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.daemons.crawlers.AbstractCrawler;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.XMPPException;

public class ServiceWatcher extends AbstractCrawler {


	private static final Logger LOGGER = Logger.getLogger(ServiceWatcher.class);

	private final List<VirtualXMPPObject> mVirtualObjects = new LinkedList<VirtualXMPPObject>();

	public ServiceWatcher(String[] inArgs) {
		super(new String[0]);
		for (final String aSerial : inArgs[0].split(";")) {
			try {
				if ((aSerial != null) && !aSerial.equals(StringShop.EMPTY_STRING)) {
					this.mVirtualObjects.add(new VirtualXMPPObject(aSerial));
				}
			} catch (final XMPPException e) {
				ServiceWatcher.LOGGER.fatal(e, e);
			}
		}
	}

	@Override
	protected void process() {
		synchronized (this.mVirtualObjects) {
			try {
				this.mVirtualObjects.wait();
			} catch (final InterruptedException e) {
				ServiceWatcher.LOGGER.fatal(e, e);
			}
		}

		for (final VirtualXMPPObject aVirtualObject : this.mVirtualObjects) {
			aVirtualObject.disconnect();
		}
	}

	@Override
	protected void quit() {
		synchronized (this.mVirtualObjects) {
			this.mVirtualObjects.notify();
		}
	}
}
