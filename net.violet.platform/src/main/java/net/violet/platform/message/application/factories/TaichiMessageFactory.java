package net.violet.platform.message.application.factories;

import java.util.List;

import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.message.Message;

public class TaichiMessageFactory extends AbstractMessageFactory {

	@Override
	public List<Message2Send> getMessage( MessageProcessUnit inUnit) {
		return null;
	}

	@Override
	protected Message.SOURCE getSource() {
		return Message.SOURCE.TAICHI;
	}

}
