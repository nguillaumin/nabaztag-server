/**
 * 
 */
package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.ScheduledMessage;

/**
 * @author slorg1
 */
public class ScheduledMessageMock extends AbstractMockRecord<ScheduledMessage, ScheduledMessageMock> implements ScheduledMessage {

	protected ScheduledMessageMock(long inId) {
		super(inId);
	}

	public Long getMessage_id() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPacket() {
		// TODO Auto-generated method stub
		return null;
	}
}
