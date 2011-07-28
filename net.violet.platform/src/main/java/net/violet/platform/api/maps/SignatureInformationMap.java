package net.violet.platform.api.maps;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.dataobjects.AnimData;
import net.violet.platform.dataobjects.UserData;

public class SignatureInformationMap extends AbstractAPIMap {

	public static final String ANIM = "anim";
	public static final String ITEM = "item";
	public static final String COLOR = "color";

	public SignatureInformationMap(APICaller caller, UserData inUser) {
		super(3);
		final AnimData theAnim = inUser.getAnimation();
		put(SignatureInformationMap.ANIM, theAnim.getName());
		put(SignatureInformationMap.ITEM, inUser.getMusicData().getApiId(caller));
		put(SignatureInformationMap.COLOR, inUser.getColorType().getLabel());
	}

}
