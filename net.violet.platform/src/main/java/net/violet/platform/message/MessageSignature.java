package net.violet.platform.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.Service;
import net.violet.platform.datamodel.User;
import net.violet.platform.util.ConvertTools;

/**
 * Classe pour la signature d'un message.
 */
public class MessageSignature {

	/**
	 * Couleur aléatoire.
	 */
	public static final int RANDOM_COLOR = -1;

	/**
	 * Stock in a static the constants used by services to send messages.
	 */
	public static Map<String, String[]> SIGNFILE_SERVICES = new HashMap<String, String[]>();

	public static final MessageSignature AIR_SIGNATURE = new MessageSignature(MessageSignature.RANDOM_COLOR, "broadcast/broad/config/anim/sig_nombril_flash_lent.chor", "broadcast/broad/config/air/fr/signature.mp3");

	public static final MessageSignature BOURSE_SIGNATURE = new MessageSignature(MessageSignature.RANDOM_COLOR, "broadcast/broad/config/anim/sig_flash.chor", "broadcast/broad/config/money/fr/signature.mp3");

	public static final MessageSignature TRAFFIC_SIGNATURE = new MessageSignature(MessageSignature.RANDOM_COLOR, "broadcast/broad/config/anim/sig_attention.chor", "broadcast/broad/config/traffic/fr/signature.mp3");

	public static final MessageSignature WEATHER_SIGNATURE = new MessageSignature(MessageSignature.RANDOM_COLOR, "broadcast/broad/config/anim/sig_tete_flash_rapide.chor", "broadcast/broad/config/weather/fr/signature.mp3");

	public static final MessageSignature MAIL_SIGNATURE = new MessageSignature(MessageSignature.parseColor("66FF33"), "broadcast/broad/config/mail/fr/signature.mp3.chor", "broadcast/broad/config/mail/fr/signature.mp3");

	public static final MessageSignature BILAN_SIGNATURE = new MessageSignature(MessageSignature.RANDOM_COLOR, "broadcast/broad/config/anim/sig_flash_rapide.chor", "broadcast/broad/config/signature/SiffletTrain.mp3");

	public static final MessageSignature HORLOGE_SIGNATURE = new MessageSignature(MessageSignature.RANDOM_COLOR, "broadcast/broad/config/anim/sig_circle.chor", "broadcast/broad/config/clock/fr/signature.mp3");

	public static final MessageSignature EMPTY_SIGNATURE = new MessageSignature(MessageSignature.RANDOM_COLOR, (String) null, (String) null);

	/**
	 * SignatureImpl (couleur) par défaut.
	 */
	private static final String DEFAULT_USER_SIGNATURE = "FFFF00";

	/**
	 * Générateur aléatoire pour les couleurs des signatures choisies
	 * aléatoirement.
	 */
	private static final Random RANDOM_GENERATOR = new Random();

	/**
	 * Couleur de la signature.
	 */
	private final int mColor;

	/**
	 * Animation de la signature.
	 */
	private final Anim mAnim;

	/**
	 * Musique de la signature.
	 */
	private final Music mMusic;

	/**
	 * URL du fichier musique.
	 */
	private final String mIntroMusicUrl;

	/**
	 * URL du fichier musique.
	 */
	private final String mOutroMusicUrl;

	/**
	 * URL de la chorégraphie.
	 */
	private final String mIntroChoregraphyUrl;

	/**
	 * URL de la chorégraphie.
	 */
	private final String mOutroChoregraphyUrl;

	public MessageSignature(String inColor, Anim inAnim, Music inMusic) {
		this(MessageSignature.parseColor(inColor), inAnim, inMusic);
	}

	public MessageSignature(int inColor, Anim inAnim, Music inMusic) {
		this.mAnim = inAnim;
		this.mMusic = inMusic;
		this.mColor = inColor;
		if ((inMusic != null) && (inMusic.getFile() != null)) {
			this.mIntroMusicUrl = inMusic.getFile().getPath();
		} else {
			this.mIntroMusicUrl = null;
		}
		this.mOutroMusicUrl = this.mIntroMusicUrl;
		if (inAnim != null) {
			this.mIntroChoregraphyUrl = inAnim.getAnim_url();
		} else {
			this.mIntroChoregraphyUrl = null;
		}
		this.mOutroChoregraphyUrl = this.mIntroChoregraphyUrl;
	}

	@Deprecated
	public MessageSignature(int inColor, String inUrlChoregraphy, String inUrlMusic) {
		this.mColor = inColor;
		this.mAnim = null;
		this.mMusic = null;
		this.mIntroMusicUrl = inUrlMusic;
		this.mIntroChoregraphyUrl = inUrlChoregraphy;
		this.mOutroMusicUrl = this.mIntroMusicUrl;
		this.mOutroChoregraphyUrl = this.mIntroChoregraphyUrl;
	}

	public MessageSignature(Service inService) {
		Files theFiles = inService.getIntro();
		this.mColor = MessageSignature.RANDOM_COLOR;
		this.mAnim = null;
		this.mMusic = null;
		this.mIntroMusicUrl = theFiles.getPath();
		this.mIntroChoregraphyUrl = theFiles.getPath2chor();
		theFiles = inService.getOutro();
		this.mOutroMusicUrl = theFiles.getPath();
		this.mOutroChoregraphyUrl = theFiles.getPath2chor();
	}

	public MessageSignature(User inUser) {
		String theColorSignature = inUser.getColorSign();

		if (theColorSignature == null) {
			theColorSignature = MessageSignature.DEFAULT_USER_SIGNATURE;
		}
		this.mColor = ConvertTools.htoi(theColorSignature);
		this.mAnim = inUser.getColor();
		this.mMusic = inUser.getMusic();
		if ((this.mMusic != null) && (this.mMusic.getFile() != null)) {
			this.mIntroMusicUrl = this.mMusic.getFile().getPath();
		} else {
			this.mIntroMusicUrl = null;
		}
		if (this.mAnim != null) {
			this.mIntroChoregraphyUrl = this.mAnim.getAnim_url();
		} else {
			this.mIntroChoregraphyUrl = null;
		}
		this.mOutroMusicUrl = this.mIntroMusicUrl;
		this.mOutroChoregraphyUrl = this.mIntroChoregraphyUrl;
	}

	/**
	 * Retourne la couleur (y compris une couleur choisie au hasard si mColor ==
	 * -1).
	 * 
	 * @return la couleur.
	 */
	int getColor4Message() {
		int theColor = getColor();
		if (theColor == MessageSignature.RANDOM_COLOR) {
			theColor = MessageSignature.RANDOM_GENERATOR.nextInt(0xEEEEEE) + 0x111111;
		}
		return theColor;
	}

	int getColor() {
		return this.mColor;
	}

	/**
	 * Accesseur sur l'animation, ou <code>null</code> si l'animation n'est pas
	 * fournie.
	 * 
	 * @return l'animation de la signature.
	 */
	Anim getAnim() {
		return this.mAnim;
	}

	/**
	 * Accesseur sur la musique, ou <code>null</code> si la musique n'est pas
	 * fournie.
	 * 
	 * @return la musique de la signature.
	 */
	Music getMusic() {
		return this.mMusic;
	}

	public String getIntroChoregraphyUrl() {
		return this.mIntroChoregraphyUrl;
	}

	public String getIntroMusicUrl() {
		return this.mIntroMusicUrl;
	}

	public String getOutroChoregraphyUrl() {
		return this.mOutroChoregraphyUrl;
	}

	public String getOutroMusicUrl() {
		return this.mOutroMusicUrl;
	}

	private static int parseColor(String inColor) {
		int theColor;
		if ((inColor == null) || (inColor.length() < 3) || inColor.equals("rand")) {
			theColor = MessageSignature.RANDOM_COLOR;
		} else {
			try {
				theColor = Integer.parseInt(inColor, 16);
			} catch (final NumberFormatException anException) {
				theColor = MessageSignature.RANDOM_COLOR;
			}
		}
		return theColor;
	}

	@Override
	public boolean equals(Object obj) {
		final MessageSignature theObject;

		return (obj instanceof MessageSignature) && (getColor() == (theObject = (MessageSignature) obj).getColor()) && (((null == this.getAnim()) && (this.getAnim() == theObject.getAnim())) || ((this.getAnim() != null) && this.getAnim().equals(theObject.getAnim()))) && (((null == this.getMusic()) && (this.getMusic() == theObject.getMusic())) || ((this.getMusic() != null) && this.getMusic().equals(theObject.getMusic()))) && (((null == this.getIntroMusicUrl()) && (this.getIntroMusicUrl() == theObject.getIntroMusicUrl())) || ((this.getIntroMusicUrl() != null) && this.getIntroMusicUrl().equals(theObject.getIntroMusicUrl()))) && (((null == this.getOutroMusicUrl()) && (this.getOutroMusicUrl() == theObject.getOutroMusicUrl())) || ((this.getOutroMusicUrl() != null) && this.getOutroMusicUrl().equals(theObject.getOutroMusicUrl()))) && (((null == this.getIntroChoregraphyUrl()) && (this.getIntroChoregraphyUrl() == theObject.getIntroChoregraphyUrl())) || ((this.getIntroChoregraphyUrl() != null) && this.getIntroChoregraphyUrl().equals(theObject.getIntroChoregraphyUrl()))) && (((null == this.getOutroChoregraphyUrl()) && (this.getOutroChoregraphyUrl() == theObject.getOutroChoregraphyUrl())) || ((this.getOutroChoregraphyUrl() != null) && this.getOutroChoregraphyUrl().equals(theObject.getOutroChoregraphyUrl())));
	}

	@Override
	public int hashCode() {
		return MessageSignature.objectHashCode(getColor()) + MessageSignature.objectHashCode(getAnim()) + MessageSignature.objectHashCode(getMusic()) + MessageSignature.objectHashCode(getIntroMusicUrl()) + MessageSignature.objectHashCode(getOutroMusicUrl()) + MessageSignature.objectHashCode(getIntroChoregraphyUrl()) + MessageSignature.objectHashCode(getOutroChoregraphyUrl());
	}

	private static int objectHashCode(Object inObject) {
		final int theResult;
		if (inObject == null) {
			theResult = 0;
		} else {
			theResult = inObject.hashCode();
		}
		return theResult;
	}
}
