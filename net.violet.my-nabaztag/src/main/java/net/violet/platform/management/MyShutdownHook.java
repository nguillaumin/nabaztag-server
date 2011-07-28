package net.violet.platform.management;

import javax.servlet.ServletContextEvent;

public class MyShutdownHook extends ShutdownHook {

	@Override
	public void contextDestroyed(ServletContextEvent inEvent) {
		super.contextDestroyed(inEvent);
	}

	@Override
	public void contextInitialized(ServletContextEvent inEvent) {
		super.contextInitialized(inEvent);
	}
}
