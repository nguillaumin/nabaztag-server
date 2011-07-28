package net.violet.platform.applets.tools;

import java.util.Date;

/**
 * @author christophe - Violet
 */
public interface Expirable {

	/**
	 * @return TRUE if the object needs refresh by comparing it to the repository timestamp 
	 * If repository date is unknown, the method may decide to expire the object using another strategy
	 * ie : fixed delay expiration.  
	 */
	boolean needsRefresh(Date inRepositoryDate);

}
