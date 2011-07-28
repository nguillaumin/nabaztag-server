package net.violet.platform.applets;

import java.util.ArrayList;
import java.util.List;

import net.violet.common.StringShop;
import net.violet.platform.daemons.schedulers.AbstractScheduler.MessageProcessUnit;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.TtsVoiceData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.events.InteractionEvent;
import net.violet.platform.events.TriggerEvent;
import net.violet.platform.message.Message;
import net.violet.platform.message.MessageDraft;
import net.violet.platform.message.MessageServices;
import net.violet.platform.message.MessageServices.Body;
import net.violet.platform.message.application.factories.AbstractMessageFactory;
import net.violet.platform.message.application.factories.AbstractMessageFactory.Message2Send;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.cache.Cache;
import net.violet.platform.util.cache.GenerationException;
import net.violet.platform.util.cache.ValueGenerator;
import net.violet.platform.voice.TTSServices;

import org.apache.log4j.Logger;

public class NativeApplet implements Applet {

	private static final Logger LOGGER = Logger.getLogger(NativeApplet.class);

	private static final String NO_CONTENT = "net.violet.application.messages.no_new_content";

	private static final Cache<TTSCacheKey, Files> NO_CONTENT_CACHE = new Cache<TTSCacheKey, Files>(new ValueGenerator<TTSCacheKey, Files>() {

		public Files generateValue(TTSCacheKey key) {
			return TTSServices.getDefaultInstance().postTTS(key.title, key.needsTreatment, key.needsTreatment, key.ttsVoice);
		}

	});

	private static class TTSCacheKey {

		private final String title;
		private final boolean needsTreatment;
		private final TtsVoice ttsVoice;

		public TTSCacheKey(String title, boolean needsTreatment, TtsVoice inTTSVoice) {
			this.title = title;
			this.needsTreatment = needsTreatment;
			this.ttsVoice = inTTSVoice;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof TTSCacheKey)) {
				return false;
			}

			final TTSCacheKey theObj = (TTSCacheKey) obj;
			return (theObj.needsTreatment == this.needsTreatment) && theObj.title.equals(this.title) && theObj.ttsVoice.equals(this.ttsVoice);
		}

		@Override
		public int hashCode() {
			return this.title.hashCode() + this.ttsVoice.hashCode() + Boolean.valueOf(this.needsTreatment).hashCode();
		}
	}

	public void processTrigger(TriggerEvent inEvent) {
		final ApplicationData theApplication = inEvent.getApplication();
		final AbstractMessageFactory theFactory = AbstractMessageFactory.getFactoryByApplication(theApplication.getReference());

		final List<SubscriptionSchedulingData> theSchedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(inEvent.getSubscription(), inEvent.getSchedulingType());

		if (!theSchedulings.isEmpty()) {
			final SubscriptionSchedulingData theScheduling = theSchedulings.get(0);
			final List<Message2Send> theMessages = theFactory.getMessage(new MessageProcessUnit(theScheduling, null, null) {

				@Override
				public void runWhenSuccessful() {
				}
			});

			final VObjectData theTarget = ((InteractionEvent) inEvent.getEvent()).getTarget();
			final List<Message> result = new ArrayList<Message>(theMessages.size());
			for (final Message2Send aMessage : theMessages) {
				aMessage.setRecipient(theTarget.getReference());
				result.add(aMessage.generateMessageDraft());
				aMessage.runWhenSent();
			}

			if (result.isEmpty()) {
				final VObjectData theEmitter = ((InteractionEvent) inEvent.getEvent()).getEmitter();
				final UserData theUser = theEmitter.getOwner();
				final SiteLangData lang = theUser.getUserLang();

				final String title = DicoTools.dico(lang.getReference(), NativeApplet.NO_CONTENT) + StringShop.EMPTY_STRING + DicoTools.dico(lang.getReference(), theApplication.getProfile().getTitle());

				final TtsVoice inTTSVoice = TtsVoiceData.findTtsVoiceByLang(TtsLangData.getDefaultTtsLanguage(lang)).getReference();
				final boolean needsTreatment = theTarget.getObjectType().instanceOf(ObjectType.NABAZTAG_V1);
				try {
					final Files theFile = NativeApplet.NO_CONTENT_CACHE.get(new TTSCacheKey(title, needsTreatment, inTTSVoice));
					final Body[] bodies = new Body[] { new Body(theFile, null, false) };
					final MessageDraft msgDraft = MessageServices.createMessageDraft(theTarget.getReference(), title, bodies, theApplication.getMessageSignature());
					result.add(msgDraft);
				} catch (final GenerationException e) {
					NativeApplet.LOGGER.fatal(e, e);
				}
			}

			for (final Message aMessage : result) {
				aMessage.setTTLInSecond(inEvent.getTTL());
				MessageServices.send(aMessage);
			}

		}
	}

}
