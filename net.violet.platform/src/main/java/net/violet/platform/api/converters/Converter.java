package net.violet.platform.api.converters;

import java.io.Reader;

import net.violet.platform.api.exceptions.InternalErrorException;

/**
 * The interface for each converter.
 */
public interface Converter {

	/**
	 * @return the name of the supported format
	 */
	String getFormatName();

	/**
	 * Uses the given String object to return an object according to the
	 * implementing format.
	 * 
	 * @param inContent
	 * @return
	 * @throws ConversionException
	 */
	<T> T convertFrom(String inContent) throws ConversionException;

	/**
	 * Uses the given Reader and returns an object according to the implementing
	 * format.
	 * 
	 * @param inReader
	 * @return
	 * @throws ConversionException
	 */
	<T> T convertFrom(Reader inReader) throws ConversionException, InternalErrorException;

	/**
	 * Uses the given POJO and returns its equivalent as a String object
	 * according to the implementing format.
	 * 
	 * @param inPojo
	 * @return
	 * @throws ConversionException
	 */
	String convertTo(Object inPojo) throws ConversionException;

	/**
	 * Uses the given POJO and returns its equivalent as a String object
	 * according to the implementing format and provided option. This method can
	 * be useless for some implementations, it is only here to allow an
	 * implementing class to change its behavior depending on an option (see for
	 * example the XMLConverter class which can return a full document or just a
	 * sample - without any header - according to the provided option). See the
	 * implementation documentation to know how the option parameter is used.
	 * 
	 * @param inPojo
	 * @param inOption
	 * @return
	 * @throws ConversionException
	 */
	String convertTo(Object inPojo, boolean inOption) throws ConversionException;

}
