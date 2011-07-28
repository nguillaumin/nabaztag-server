package net.violet.common.utils.server;


public class ProcessName {

	private static String PROCESS_NAME;
	/*
	 * TODO : Do not change this method when my.nabaztag.com is available or 
	 * two context is deployed in same tomcat instance. 
	 * Because the "tomcat" system property is override.
	 */
	public static String getProcessName() {
		if(PROCESS_NAME == null) {
			synchronized (ProcessName.class) {
				if(PROCESS_NAME == null) {
					String theProcessName = System.getProperty("tomcat");
					if (theProcessName == null) {
						theProcessName = System.getProperty("crawler");
						if (theProcessName == null) {
							theProcessName = "tomcat";
						}
					}
					PROCESS_NAME = ((System.getProperty("hostname") == null) ? "localhost" : System.getProperty("hostname")) + "/" + theProcessName;
				}
			}
		}
		return PROCESS_NAME;
	}

}
