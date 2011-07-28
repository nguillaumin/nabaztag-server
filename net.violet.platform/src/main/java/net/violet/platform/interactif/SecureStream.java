package net.violet.platform.interactif;

import net.violet.common.utils.DigestTools;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.StringShop;

/**
 * Classe pour un flux sécurisé.
 */
public final class SecureStream {

	/**
	 * Préfixe des streams sécurisé.
	 */
	public static final String STREAM_PREFIX = "/str/";

	/**
	 * Secret du stream sécurisé.
	 */
	public static final String SECRET = "6a5f1732a58947d90cd501719c88b08b";

	/**
	 * Constructeur indisponible.
	 */
	private SecureStream() {
		// This space for rent.
	}

	/**
	 * Accesseur à partir d'un nom.
	 * 
	 * @param inName nom du stream
	 * @return <code>null</code> si le stream n'existe pas.
	 */
	public static String getStreamUrl(String inName, long inPosition) {
		final int now = (int) (System.currentTimeMillis() / 1000);
		final String thePathToFile = "/" + inName + "/stream-" + inPosition + StringShop.MP3_EXT;
		final String nowAsHex = Integer.toHexString(now);
		final String theMD5 = DigestTools.digest(SecureStream.SECRET + thePathToFile + nowAsHex, DigestTools.Algorithm.MD5);
		final String theURL = "http://" + Constantes.STREAM_SERVER + SecureStream.STREAM_PREFIX + theMD5 + "/" + nowAsHex + thePathToFile + "?x";
		return theURL;
	}
}
