package net.violet.platform.message.application.factories;

import java.util.Collections;
import java.util.List;

import net.violet.platform.daemons.crawlers.source.CrawlerSourceMail;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageSignature;

public class MailMessageFactory extends AbstractMessageFactory {

	static final String TITLE = "Service Mail";

	@Override
	public List<Message2Send> getMessage(final MessageProcessUnit inUnit) {

		return Collections.singletonList((Message2Send) new Message2Send(inUnit.get(), inUnit.getProcessConditioner()) {

			@Override
			protected Files[] getBody() {
				return null;
			}

			@Override
			protected MessageSignature getSignature() {
				return MessageSignature.MAIL_SIGNATURE;
			}

			@Override
			protected String getTitle() {
				return MailMessageFactory.TITLE;
			}

			@Override
			protected Palette getColorPal() {
				return CrawlerSourceMail.MAIL_COLOR_PAL;
			}

			@Override
			protected boolean isStream() {
				return true;
			}

		});

	}

	@Override
	protected Message.SOURCE getSource() {
		return Message.SOURCE.MAIL;
	}

}
