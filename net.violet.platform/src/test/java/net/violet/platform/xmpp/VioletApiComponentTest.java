package net.violet.platform.xmpp;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.packet.JabberComponentApiPacketListener;

public class VioletApiComponentTest extends MockTestBase {

	public static void main(String[] args) {
		/* final JabberComponentApiPacketListener theComponent = */
		new JabberComponentApiPacketListener(JabberComponentManager.getComponent(Constantes.XMPP_API_COMPONENT));

		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (final InterruptedException e) {
			
			e.printStackTrace();
		}
	}

}
