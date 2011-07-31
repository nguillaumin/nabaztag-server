package net.violet.vadmin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.actions.DispatchAction;

import net.violet.common.StringShop;

public class AdminAuthorization {

	public static final Map<String, List<USER_RIGHTS>> USER_RIGHTS_LIST = new HashMap<String, List<USER_RIGHTS>>();
	
	private static final Log log = LogFactory.getLog(AdminAuthorization.class);
	
	enum USER_RIGHTS {

		SEARCHING(1, "Searching information"),
		ADDING(2, "Adding an information"),
		UPDATING(3, "Updating an information"),
		DELETING(4, "Deleting an information");

		private static Map<Long, USER_RIGHTS> ID_USER_RIGHTS;

		static {
			final Map<Long, USER_RIGHTS> theMap = new HashMap<Long, USER_RIGHTS>();

			for (final USER_RIGHTS aUserRight : USER_RIGHTS.values()) {
				theMap.put(aUserRight.getId(), aUserRight);
			}

			USER_RIGHTS.ID_USER_RIGHTS = Collections.unmodifiableMap(theMap);
		}

		private final long mId;
		private final String mLabel;

		private USER_RIGHTS(long inId, String inLabel) {
			this.mId = inId;
			this.mLabel = inLabel;
		}

		public Long getId() {
			return this.mId;
		}

		public String getLabel() {
			return this.mLabel;
		}

		Map<Long, USER_RIGHTS> getUserRights() {
			return ID_USER_RIGHTS;
		}
		
		public static USER_RIGHTS findById(Long inUserRight) {
			return USER_RIGHTS.ID_USER_RIGHTS.get(inUserRight);
		}
	}
	
	/**
	 * Get all the authorization of an existing user and determine his accessibility to the Admin part.
	 * @param inUserEmail the email address of the identified user.
	 * @return <code>true</code> if the user is authorize to access to the Admin, or <code>false</code> if he isn't.
	 * @throws FileNotFoundException if the authorization's file is not found. 
	 * @throws NumberFormatException if the file contains an inexisting authorization.
	 */
	public static boolean getAuthorization(String inUserEmail) throws FileNotFoundException, NumberFormatException{
	
		File authzFile = null;
		if (AdminConstantes.USERS_PERMISSIONS_PATH.startsWith("classpath:")) {
			URL authzUrl = AdminAuthorization.class.getResource(AdminConstantes.USERS_PERMISSIONS_PATH.replace("classpath:", ""));			
			try {
				authzFile = new File(authzUrl.toURI());
			} catch (URISyntaxException use) {
				log.debug("Unable to use toURI() to get a local file path on '"+authzUrl+"'", use);
				authzFile = new File(authzUrl.toString());
			}
		} else {
			authzFile = new File(AdminConstantes.USERS_PERMISSIONS_PATH);
		}
		
		Scanner scanner = new Scanner(authzFile);
		
		boolean isAuthorized = false;

		while (scanner.hasNextLine()) {
		    String[] line = scanner.nextLine().split(StringShop.SPACE);
		    if(inUserEmail.equals(line[0])){
		    	List<USER_RIGHTS> theUserRights = new LinkedList<USER_RIGHTS>();
		    	for (int i = 1; i < line.length; i++) {
					USER_RIGHTS aUserRight = USER_RIGHTS.findById(new Long(line[i]));
					if(aUserRight!=null){
						theUserRights.add(aUserRight);
					}
				}
		    	USER_RIGHTS_LIST.put(inUserEmail, theUserRights);
		    	isAuthorized = true;
		    }
		}
		scanner.close();
		return isAuthorized;
	}
}
