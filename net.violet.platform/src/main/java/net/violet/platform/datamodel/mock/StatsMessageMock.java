/**
 * 
 */
package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.StatsMessage;

/**
 * @author slorg1
 */
public class StatsMessageMock extends AbstractMockRecord<StatsMessage, StatsMessageMock> implements StatsMessage {

	protected StatsMessageMock(long inId) {
		super(inId);
	}

}
