package net.violet.platform.applets;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.applets.interactive.CommonPlayer;
import net.violet.platform.applets.interactive.InteractiveApplet;
import net.violet.platform.applets.interactive.NathanPlayer;
import net.violet.platform.applets.interactive.SimplePlayer;
import net.violet.platform.applets.interactive.VoiceMailApplet;
import net.violet.platform.datamodel.ApplicationSetting;
import net.violet.platform.datamodel.Application.NativeApplication;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ApplicationSettingData;
import net.violet.platform.events.TriggerEvent;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.IQAppletQuery;
import net.violet.platform.xmpp.JabberComponentManager;
import net.violet.platform.xmpp.packet.VioletAppletPacket;

import org.apache.log4j.Logger;

public class AppletDispatcher {

	private static final Logger LOGGER = Logger.getLogger(AppletDispatcher.class);

	private static final Map<NativeApplication, NativeApplet> NATIVE_APPLETS = new HashMap<NativeApplication, NativeApplet>();
	private static final Map<Long, InteractiveApplet> PLAYER_APPLETS = new HashMap<Long, InteractiveApplet>();

	public static final Pattern AUDIOBOOK_NAME = Pattern.compile("^net\\.violet\\.audiobooks\\.\\w+$");

	static {
		AppletDispatcher.PLAYER_APPLETS.put(CommonPlayer.APPLET_ID, new CommonPlayer());
		AppletDispatcher.PLAYER_APPLETS.put(VoiceMailApplet.APPLET_ID, new VoiceMailApplet());
		AppletDispatcher.PLAYER_APPLETS.put(NathanPlayer.APPLET_ID, new NathanPlayer());
		AppletDispatcher.PLAYER_APPLETS.put(SimplePlayer.APPLET_ID, new SimplePlayer());

		final NativeApplet nativeApplet = new NativeApplet();
		AppletDispatcher.NATIVE_APPLETS.put(NativeApplication.WEATHER, nativeApplet);
		AppletDispatcher.NATIVE_APPLETS.put(NativeApplication.TRAFIC, nativeApplet);
		AppletDispatcher.NATIVE_APPLETS.put(NativeApplication.RSS_FULL, nativeApplet);
		AppletDispatcher.NATIVE_APPLETS.put(NativeApplication.AIR, nativeApplet);
		AppletDispatcher.NATIVE_APPLETS.put(NativeApplication.BOURSE_FREE, nativeApplet);
		AppletDispatcher.NATIVE_APPLETS.put(NativeApplication.BOURSE_FULL, nativeApplet);
		AppletDispatcher.NATIVE_APPLETS.put(NativeApplication.MOOD, nativeApplet);
		AppletDispatcher.NATIVE_APPLETS.put(NativeApplication.RSS_FREE, nativeApplet);
		AppletDispatcher.NATIVE_APPLETS.put(NativeApplication.PODCAST_FREE, nativeApplet);
		AppletDispatcher.NATIVE_APPLETS.put(NativeApplication.PODCAST_FULL, nativeApplet);

		AppletDispatcher.NATIVE_APPLETS.put(NativeApplication.WEBRADIO, new WebradioApplet());
	}

	/**
	 * Send the Trigger Event to the application that will process it
	 * @param inTrigger
	 * @throws ConversionException 
	 * @throws InvalidParameterException 
	 */
	public static void dispatchApplication(TriggerEvent inTrigger) {

		final ApplicationData app = inTrigger.getApplication();

		final NativeApplication theNative = NativeApplication.findByApplication(app.getReference());

		if ((inTrigger.getSubscription() != null) && inTrigger.getSubscription().isValid()) {

			if ((theNative != null) && (AppletDispatcher.NATIVE_APPLETS.containsKey(theNative))) {
				AppletDispatcher.NATIVE_APPLETS.get(theNative).processTrigger(inTrigger);
				return;

			} else if (AppletDispatcher.AUDIOBOOK_NAME.matcher(app.getName()).matches()) {

				final ApplicationSettingData theAppletSetting = ApplicationSettingData.findByApplicationAndKey(app, ApplicationSetting.Player.APPLET_ID);
				if ((theAppletSetting != null) && theAppletSetting.isValid()) {
					AppletDispatcher.PLAYER_APPLETS.get(Long.parseLong(theAppletSetting.getValue())).processTrigger(inTrigger);
					return;
				}

			}
		}

		AppletDispatcher.sendPacketToAppletComponent(inTrigger, app);
	}

	private static void sendPacketToAppletComponent(TriggerEvent inTrigger, ApplicationData inApplication) {

		try {
			final VioletAppletPacket appletPacket = new VioletAppletPacket(inTrigger);
			appletPacket.setTo(ApplicationCredentialsData.findByApplication(inApplication).getPublicKey() + "@" + Constantes.XMPP_APPLET_COMPONENT);
			appletPacket.setFrom(JabberComponentManager.getComponentDefaultFromAddress(Constantes.XMPP_PLATFORM_COMPONENT));
			// send and don't wait
			IQAppletQuery.simpleSendPacket(Constantes.XMPP_PLATFORM_COMPONENT, appletPacket);
		} catch (final ConversionException e) {
			AppletDispatcher.LOGGER.fatal(e, e);
		}

	}

	public static InteractiveApplet getInteractiveAppletByApplication(ApplicationData inApplication) {
		final ApplicationSettingData theAppletSetting = ApplicationSettingData.findByApplicationAndKey(inApplication, ApplicationSetting.Player.APPLET_ID);
		return AppletDispatcher.PLAYER_APPLETS.get(Long.parseLong(theAppletSetting.getValue()));
	}
}
