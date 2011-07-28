package net.violet.vadmin.objects.data;

import net.violet.common.StringShop;
import net.violet.vadmin.util.AdminConstantes;

public abstract class AdminData {

	public static final String BROADCAST = "broadcast";

	public String makePictureURL(String path) {
		if (path != null) {
			return path.replaceFirst(AdminData.BROADCAST, AdminConstantes.IMG_PATH);
		}
		return StringShop.EMPTY_STRING;
	}
}
