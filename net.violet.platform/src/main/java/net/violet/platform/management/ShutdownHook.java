package net.violet.platform.management;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.violet.db.cache.Cache;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.JabberClientManager;
import net.violet.platform.xmpp.JabberComponentManager;
import net.violet.probes.ProbesHandler;

public class ShutdownHook implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent inEvent) {
		ShutdownHook.shutdown();
	}

	public void contextInitialized(ServletContextEvent inEvent) {
		System.setProperty("tomcat", "tomcat_" + inEvent.getServletContext().getServletContextName());
		ProbesHandler.init();
		try {
			JabberComponentManager.getComponent(Constantes.XMPP_PLATFORM_COMPONENT);
		} catch (final IllegalStateException ils) {
			System.err.println(ils.getMessage());
		}
	}

	public static void shutdown() {
		Cache.shutdownCache();
		JabberClientManager.shutdown();
		JabberComponentManager.shutdown();
		ProbesHandler.shutdownAll();
	}
}
