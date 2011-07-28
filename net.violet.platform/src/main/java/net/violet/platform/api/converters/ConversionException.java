package net.violet.platform.api.converters;

/**
 * Exception throwed when a conversion error occurs in one of the exchange
 * format conversion
 * 
 * @see
 */
public class ConversionException extends Exception {

	/**
	 * Constructeur à partir d'un message.
	 */
	public ConversionException(String msg) {
		super(msg);
	}

	public ConversionException(Throwable e) {
		super(e);
	}

	public ConversionException(Throwable e, String inMessage) {
		super(inMessage, e);
	}
}
