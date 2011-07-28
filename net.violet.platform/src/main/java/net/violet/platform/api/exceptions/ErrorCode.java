package net.violet.platform.api.exceptions;

import org.json.JSON;

/**
 * Define the unique error codes associated with each public API exception Each
 * exception must use the constructor with one of this enum value to define its
 * code and status
 * 
 * @see net.violet.platform.api.exceptions.APIException#getCode()
 * @author christophe - Violet
 */
enum ErrorCode {

	NotModified(304, ErrorStatus.WARNING),

	GenericError(500, ErrorStatus.ERROR), InternalError(500, ErrorStatus.ERROR), HttpRequestFailure(500, ErrorStatus.ERROR),

	BadCredential(401, ErrorStatus.ERROR), InvalidData(400, ErrorStatus.ERROR), Unsupported(400, ErrorStatus.ERROR), InvalidParameter(400, ErrorStatus.ERROR), MissingParameter(400, ErrorStatus.ERROR), InvalidSession(402, ErrorStatus.ERROR), ForbiddenException(403, ErrorStatus.ERROR), InvalidContact(405, ErrorStatus.ERROR), InvalidNotification(406, ErrorStatus.ERROR),

	NoSuchApplication(601, ErrorStatus.ERROR), NoSuchObject(602, ErrorStatus.ERROR), NoSuchPerson(603, ErrorStatus.ERROR), NoSuchContext(604, ErrorStatus.ERROR), NoSuchContact(606, ErrorStatus.ERROR), NoSuchMessage(607, ErrorStatus.ERROR), NoSuchCategory(608, ErrorStatus.ERROR), NoSuchTag(609, ErrorStatus.ERROR), NoSuchFile(611, ErrorStatus.ERROR), NoSuchItem(612, ErrorStatus.ERROR), NoSuchProduct(613, ErrorStatus.ERROR), NoSuchNews(614, ErrorStatus.ERROR), NoSuchPress(615, ErrorStatus.ERROR), NoSuchStore(616, ErrorStatus.ERROR), NoSuchType(617, ErrorStatus.ERROR), NoSuchClass(618, ErrorStatus.ERROR), NoSuchNotification(619, ErrorStatus.ERROR),

	NameAlreadyExists(701, ErrorStatus.ERROR), PersonAlreadyExists(702, ErrorStatus.ERROR), ContactAlreadyExists(703, ErrorStatus.ERROR), ObjectAlreadyExists(704, ErrorStatus.ERROR), NotificationAlreadyExists(705, ErrorStatus.ERROR),

	NoSuchSubscription(802, ErrorStatus.ERROR),

	ParentalFilter(901, ErrorStatus.ERROR), Blacklisted(902, ErrorStatus.ERROR), VocalMessageConversionFailed(903, ErrorStatus.ERROR), TTSGenerationFailed(904, ErrorStatus.ERROR), BadMimeType(905, ErrorStatus.ERROR), BadURL(906, ErrorStatus.ERROR);

	protected final int ERR_CODE;
	protected final ErrorStatus STATUS;

	private ErrorCode(int errCode, ErrorStatus status) {
		this.ERR_CODE = errCode;
		this.STATUS = status;
	}

	public static ErrorCode findByCode(int inErrCode) {
		for (final ErrorCode errCode : ErrorCode.values()) {
			if (errCode.ERR_CODE == inErrCode) {
				return errCode;
			}
		}
		return null;
	}

	enum ErrorStatus implements JSON {
		ERROR("error"), WARNING("warning");

		private final String label;

		private ErrorStatus(String inLabel) {
			this.label = inLabel;
		}

		@Deprecated
		protected String getLabel() {
			return this.label;
		}

		public String toJSONString() {
			return net.violet.common.StringShop.DOUBLE_QUOTE + this.label + net.violet.common.StringShop.DOUBLE_QUOTE;
		}
	}

}
