package net.violet.common.utils.net;

import javax.servlet.http.HttpServletRequest;

public class NetTools {

	public static String getIP(HttpServletRequest request) {
		return (request.getHeader("HTTP_X_FORWARDED_FOR") == null) ? request.getRemoteAddr() : request.getHeader("HTTP_X_FORWARDED_FOR");
	}
}
