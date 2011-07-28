/**
 * 
 */
package net.violet.platform.datamodel.mock;

import java.util.Map;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.UserPrefs;

/**
 */
public class UserPrefsMock extends AbstractMockRecord<UserPrefs, UserPrefsMock> implements UserPrefs {

	public UserPrefsMock(long inId) {
		super(inId);
	}

	public Map<String, String> getUserPrefs() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getUserprefs_id() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getUserprefs_layout() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLayout(String layout) {
		// TODO Auto-generated method stub

	}
}
