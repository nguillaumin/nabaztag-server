package net.violet.platform.api.exceptions;

import java.util.Locale;

import net.violet.platform.conf.ConfigurationFileManager;
import net.violet.platform.util.Constantes;

import org.apache.commons.configuration.Configuration;

public enum APIErrorMessage {

	INVALID_SESSION("invalid_session"),
	INVALID_PASSWORD("invalid_password"),
	INVALID_CONTACT("invalid_contact"),
	INVALID_NOTIFICATION("invalid_notification"),
	INVALID_STATUS_MUSIC("Unkwown library item type : {0}. Authorized types are : {1}"),
	INVALID_LANGUAGE_CODE("Invalid or unexpected language code : {0}"),
	INVALID_NAME("Invalid email address : {0}" + Constantes.POPMAIL + ". Email names can only contains ASCII alphanumeric chars and '_', '-', '.'."),
	INVALID_FORMAT("{0} is not a supported format."),
	INVALID_MESSAGE("invalid message"),
	UNAUTHORIZED("unauthorized"),
	INTERNAL_ERROR("The server encountered an internal error ({0})"),
	CONNECTION_REFUSED("connection_refused"),
	PERMISSION_DENIED("permission_denied"),
	UNKNOWN_FORMAT("Unsupported request format : {0}"),
	BAD_FORMAT("Bad Format : the request parameters could't be parsed : {0}"),
	UNPARSABLE_DATE("unparsable_date"),
	NOT_MODIFIED("The requested resource has not been modified since last call."),
	NO_SUCH_METHOD("no_such_method"),
	NO_SUCH_GROUP("no_such_group"),

	UNREMOVABLE_APPLICATION("This application cannot be removed."),

	MISSING_PARAMETER("The requested parameter {0} is missing."),
	INVALID_PARAMETER("Value of parameter {0} is invalid."),
	INVALID_PARAMETER_BECAUSE("Value of parameter {0} is invalid : {1}"),
	MISSING_COUNTRY("The country of the city must be given."),
	NOT_AN_INTEGER("Parameter {0} is not of the expected type (integer)"),
	NOT_A_LONG("Parameter {0} is not of the expected type (long)"),
	NOT_A_BYTE_ARRAY("Parameter {0} is not of the expected type (byte array)"),
	NOT_A_BOOLEAN("Parameter {0} is not of the expected type (boolean)"),
	NOT_A_STRING("Parameter {0} is not of the expected type (string)"),
	NOT_A_DATE("Parameter {0} is not of the expected type (date)"),
	NOT_A_PRIMITIVE_TYPE("Parameter {0} is not of the accepted primitives type (String, Number, Date, Boolean)"),
	NOT_A_MAP("Parameter {0} is not of the expected type (map)"),
	NOT_A_LIST("Parameter {0} is not of the expected type (list)"),
	NOT_A_CONFIGURATION_WIDGET_MAP("Received map is not of a correct ConfigurationWidget map : {0}"),
	NOT_AN_EMAIL_ADDRESS("Parameter {0} is not of the expected type (email address)"),
	NOT_A_TIMEZONE("Parameter {0} is not of the expected type (timezone)"),
	NOT_A_COUNTRY_CODE("not_a_country_code"),
	NOT_A_PRIMARY_LANGUAGE_CODE("not_a_primary_language_code"),
	NOT_A_VOICE_COMMAND("not_a_voice_command_or_not_identify_a_voice"),
	NOT_A_VALID_OBJECT("not_a_valid_object"),
	NOT_IN_THE_CONTACT_LIST("not_in_the_contact_list"),
	NOT_AN_EXISTING_QUOTE("not_an_existing_quote"),
	EMAIL_ADDRESS_ALREADY_EXISTS("email_address_already_exists"),
	NAME_ALREADY_EXISTS("name_already_exists"),
	SERIAL_ALREADY_EXISTS("serial_already_exists"),
	MESSAGE_ALREADY_ARCHIVED("message_already_archived"),
	CONTACT_ALREADY_EXISTS("contact_already_exists"),
	NOTIFICATION_ALREADY_EXISTS("notification_already_exists"),
	NO_SUCH_APPLICATION("The application Id submitted to the API call doesn't correspond to any application."),
	NO_SUCH_CONTACT("no_such_contact"),
	NO_VALID_OBJECT_TYPE("no_valid_object_type_or_not_primary"),
	NO_VALID_PALETTE("{0} is not a valid palette name"),
	NO_VALID_OBJECT_SERIAL("There is no object associated with this serial : {0}"),
	CONTENT_TYPE_NOT_SUPPORTED("this content type not supported : {0}"),
	MISSING_MAIN_PARAMETER("missing_main_parameter"),
	NO_SUCH_MESSAGE("no_such_message"),
	NO_SUCH_DICO_KEY("no_such_dico_key"),
	NO_SUCH_CATEGORY("{0} is not a valid category."),
	NO_SUCH_COUNTRY("no_such_country"),
	NO_SUCH_TAG("no_such_tag"),
	INVALID_SENDER("invalid_sender"),
	NO_SUCH_PERSON("Unknown user : {0}"),
	NO_SUCH_CONTEXT("no_such_context"),
	NO_SUCH_CONTINENT("no_such_continent"),
	NO_SUCH_FILE("no_such_file"),
	NO_SUCH_NEWS("no_such_news"),
	NO_SUCH_PRESS("no_such_press"),
	NO_SUCH_STORE("no_such_store"),
	NO_SUCH_TYPE("{0} is not a valid object type."),
	NO_SUCH_OBJECT("no_such_object"),
	NO_SUCH_PRODUCT("no_such_product"),
	INVALID_SEQUENCE_PART("invalid_sequence_part"),
	INVALID_EXPIRATION_DATE("invalid_expiration_date"),
	NOT_A_GENDER("not_a_gender"),
	PASSWORD_CANNOT_BE_EMPTY("password_cannot_be_empty"),
	EMPTY_QUERY("empty_query"),
	INVALID_APPLICATION("The application sources couldn't be loaded !"),
	JAVASCRIPT_EXECUTION_ERROR("JavaScript execution error : {0}."),
	NOT_A_STATUS("not_a_status"),
	NOT_A_HARDWARE_NAME("not_a_hardware_name"),
	UNKNOWN_SCRIPTING_LANGUAGE("unknown_scripting_language"),
	BLACKING_ITSELF("can_not_black_itself"),
	NO_SUCH_APPLANGUAGE("no_such_applanguage"),
	NO_SUCH_APIVERSION("no_such_apiversion"),
	NO_SUCH_SUBSCRIPTION("no_such_subscription"),
	CANNOT_SUBSCRIBE("can_not_subscribe"),
	CANNOT_DELETE_PENDING_NOTIFICATION("can_not_delete_pending_notification"),
	CANNOT_RETRACT_THIS_NOTIFICATION("can_not_retract_this_notification"),
	CANNOT_REJECT_THIS_NOTIFICATION("can_not_reject_this_notification"),
	CANNOT_ACCEPT_THIS_NOTIFICATION("can_not_accept_this_notification"),
	CANNOT_SEND_NOTIFICATION("can_not_send_notification_for_this_application"),
	PARENTAL_FILTER("have_parental_filter"),
	BLACKLISTED("is_in_blacklist"),
	VOCAL_MESSAGE_CONVERSION_FAILED("vocal_message_conversion_failed"),
	TTS_GENERATION_FAILED("tts_generation_failed"),
	DELETE_FAILED("Delete failed"),
	BAD_MIME_TYPE("Bad mime type : ${0}"),
	BAD_ENCODING("Bad encoding : ${0}. Use UTF-8 instead."),
	NO_SUCH_ITEM("no_such_item"),
	HTTP_REQUEST_FAILURE("The HTTP request failed with following message status {0}."),
	INVALID_COLOR("{0} is an invalid color name for the signature."),
	NOT_AN_ANIM_NAME("not_an_anim_name"),
	UNKNOWN_EMAIL_ADDRESS("Unknown email address : {0}"),
	UNKNOWN_URL("Bad url : ${0}"),
	NO_SUCH_MAIL_BOX("No such mail box: {0}."),
	OUT_OF_BOUND("Out of bound"),
	NO_SUCH_NOTIFICATION("no_such_notification"),
	NO_SUCH_CLASS("{0} is not a valid class for an application."),
	INVALID_MIME_TYPE("{0} is not a valid mime type."),
	INVALID_SETTING("{0} is not a valid setting : {1}"),
	MISSING_SETTING("Required setting {0} is missing");

	private final String mMessage;

	private APIErrorMessage(String inMessage) {
		this.mMessage = inMessage;
	}

	public String getMessage() {
		return this.mMessage;
	}

	/**
	 * @param locale
	 * @return the error message translated in the given Locale if it does exist
	 */
	String getMessage(Locale locale) {

		Configuration localeTranslation;
		try {
			localeTranslation = ConfigurationFileManager.getConfiguration(locale.getCountry());

		} catch (final Exception e) {
			e.printStackTrace();
			localeTranslation = null;
		}

		if (localeTranslation != null) {
			// note : the file may not be fully translated, so we need the
			// default message
			return localeTranslation.getString(this.name(), this.mMessage);

		}
		// not translated
		return this.mMessage;
	}

}
