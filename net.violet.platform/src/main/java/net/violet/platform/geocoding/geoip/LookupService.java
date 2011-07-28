/**
 * LookupService.java
 *
 * Copyright (C) 2003 MaxMind LLC.  All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.violet.platform.geocoding.geoip;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

/**
 * Provides a lookup service for information based on an IP address. The
 * location of a database file is supplied when creating a lookup service
 * instance. The edition of the database determines what information is
 * available about an IP address. See the DatabaseInfo class for further
 * details.
 * <p>
 * The following code snippet demonstrates looking up the country that an IP
 * address is from:
 * 
 * <pre>
 * // First, create a LookupService instance with the location of the database.
 * LookupService lookupService = new LookupService(&quot;c:\\geoip.dat&quot;);
 * // Assume we have a String ipAddress (in dot-decimal form).
 * Country country = lookupService.getCountry(ipAddress);
 * System.out.println(&quot;The country is: &quot; + country.getName());
 * System.out.println(&quot;The country code is: &quot; + country.getCode());
 * </pre>
 * 
 * In general, a single LookupService instance should be created and then reused
 * repeatedly.
 * <p>
 * <i>Tip:</i> Those deploying the GeoIP API as part of a web application may
 * find it difficult to pass in a File to create the lookup service, as the
 * location of the database may vary per deployment or may even be part of the
 * web-application. In this case, the database should be added to the classpath
 * of the web-app. For example, by putting it into the WEB-INF/classes directory
 * of the web application. The following code snippet demonstrates how to create
 * a LookupService using a database that can be found on the classpath:
 * 
 * <pre>
 * 
 * 
 * 
 * String fileName = getClass().getResource(&quot;/GeoIP.dat&quot;).toExternalForm().substring(6);
 * LookupService lookupService = new LookupService(fileName);
 * </pre>
 * 
 * @author Matt Tucker (matt@jivesoftware.com)
 */
public class LookupService {


	private static final Logger LOGGER = Logger.getLogger(LookupService.class);

	/**
	 * Database file.
	 */
	private final File databaseFile;

	private static final int COUNTRY_EDITION = 1;
	private static final int REGION_EDITION_REV0 = 7;
	private static final int REGION_EDITION_REV1 = 3;
	private static final int CITY_EDITION_REV0 = 6;
	private static final int CITY_EDITION_REV1 = 2;
	private static final int ORG_EDITION = 5;
	private static final int ISP_EDITION = 4;
	private static final int PROXY_EDITION = 8;
	private static final int ASNUM_EDITION = 9;
	private static final int NETSPEED_EDITION = 10;

	/**
	 * The database type. Default is the country edition.
	 */
	private byte databaseType = LookupService.COUNTRY_EDITION;

	private int[] databaseSegments;
	private int recordLength;

	private byte[] dbbuffer;
	private long mtime;
	private static final int COUNTRY_BEGIN = 16776960;
	private static final int STATE_BEGIN_REV0 = 16700000;
	private static final int STATE_BEGIN_REV1 = 16000000;
	private static final int STRUCTURE_INFO_MAX_SIZE = 20;

	private static final int SEGMENT_RECORD_LENGTH = 3;
	private static final int STANDARD_RECORD_LENGTH = 3;
	private static final int ORG_RECORD_LENGTH = 4;
	private static final int MAX_RECORD_LENGTH = 4;

	private static final int FULL_RECORD_LENGTH = 60;

	private static final String[] countryCode = { "--", "AP", "EU", "AD", "AE", "AF", "AG", "AI", "AL", "AM", "AN", "AO", "AQ", "AR", "AS", "AT", "AU", "AW", "AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BM", "BN", "BO", "BR", "BS", "BT", "BV", "BW", "BY", "BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CU", "CV", "CX", "CY", "CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "FI", "FJ", "FK", "FM", "FO", "FR", "FX", "GA", "GB", "GD", "GE", "GF", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GS", "GT", "GU", "GW", "GY", "HK", "HM", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IN", "IO", "IQ", "IR", "IS", "IT", "JM", "JO", "JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB",
			"LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC", "MD", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU", "NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PN", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RO", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "ST", "SV", "SY", "SZ", "TC", "TD", "TF", "TG", "TH", "TJ", "TK", "TM", "TN", "TO", "TL", "TR", "TT", "TV", "TW", "TZ", "UA", "UG", "UM", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "YE", "YT", "RS", "ZA", "ZM", "ME", "ZW", "A1", "A2", "O1", "AX", "GG", "IM", "JE", };

	/**
	 * Create a new lookup service using the specified database file.
	 * 
	 * @param databaseFile String representation of the database file.
	 * @param options database flags to use when opening the database
	 *            GEOIP_STANDARD read database from disk GEOIP_MEMORY_CACHE
	 *            cache the database in RAM and read it from RAM
	 * @throws java.io.IOException if an error occured creating the lookup
	 *             service from the database file.
	 */
	public LookupService(String databaseFile) {
		this(new File(databaseFile));
	}

	/**
	 * Create a new lookup service using the specified database file.
	 * 
	 * @param databaseFile the database file.
	 * @param options database flags to use when opening the database
	 *            GEOIP_STANDARD read database from disk GEOIP_MEMORY_CACHE
	 *            cache the database in RAM and read it from RAM
	 * @throws java.io.IOException if an error occured creating the lookup
	 *             service from the database file.
	 */
	private LookupService(File databaseFile) {
		this.databaseFile = databaseFile;
	}

	/**
	 * Reads meta-data from the database file.
	 * 
	 * @throws java.io.IOException if an error occurs reading from the database
	 *             file.
	 */
	private void init() throws IOException {
		final RandomAccessFile file = new RandomAccessFile(this.databaseFile, "r");
		int i;
		int j;
		final byte[] delim = new byte[3];
		final byte[] buf = new byte[LookupService.SEGMENT_RECORD_LENGTH];

		this.mtime = this.databaseFile.lastModified();
		file.seek(file.length() - 3);
		for (i = 0; i < LookupService.STRUCTURE_INFO_MAX_SIZE; i++) {
			file.read(delim);
			if ((delim[0] == -1) && (delim[1] == -1) && (delim[2] == -1)) {
				this.databaseType = file.readByte();
				if (this.databaseType >= 106) {
					// Backward compatibility with databases from April 2003 and
					// earlier
					this.databaseType -= 105;
				}
				// Determine the database type.
				if (this.databaseType == LookupService.REGION_EDITION_REV0) {
					this.databaseSegments = new int[1];
					this.databaseSegments[0] = LookupService.STATE_BEGIN_REV0;
					this.recordLength = LookupService.STANDARD_RECORD_LENGTH;
				} else if (this.databaseType == LookupService.REGION_EDITION_REV1) {
					this.databaseSegments = new int[1];
					this.databaseSegments[0] = LookupService.STATE_BEGIN_REV1;
					this.recordLength = LookupService.STANDARD_RECORD_LENGTH;
				} else if ((this.databaseType == LookupService.CITY_EDITION_REV0) || (this.databaseType == LookupService.CITY_EDITION_REV1) || (this.databaseType == LookupService.ORG_EDITION) || (this.databaseType == LookupService.ISP_EDITION) || (this.databaseType == LookupService.ASNUM_EDITION)) {
					this.databaseSegments = new int[1];
					this.databaseSegments[0] = 0;
					if ((this.databaseType == LookupService.CITY_EDITION_REV0) || (this.databaseType == LookupService.CITY_EDITION_REV1)) {
						this.recordLength = LookupService.STANDARD_RECORD_LENGTH;
					} else {
						this.recordLength = LookupService.ORG_RECORD_LENGTH;
					}
					file.read(buf);
					for (j = 0; j < LookupService.SEGMENT_RECORD_LENGTH; j++) {
						this.databaseSegments[0] += LookupService.unsignedByteToInt(buf[j]) << (j * 8);
					}
				}
				break;
			}
			file.seek(file.getFilePointer() - 4);
		}
		if ((this.databaseType == LookupService.COUNTRY_EDITION) | (this.databaseType == LookupService.PROXY_EDITION) | (this.databaseType == LookupService.NETSPEED_EDITION)) {
			this.databaseSegments = new int[1];
			this.databaseSegments[0] = LookupService.COUNTRY_BEGIN;
			this.recordLength = LookupService.STANDARD_RECORD_LENGTH;
		}
		final int l = (int) file.length();
		this.dbbuffer = new byte[l];
		file.seek(0);
		file.read(this.dbbuffer, 0, l);
		file.close();
	}

	private synchronized void _check_mtime() {
		try {
			final long t = this.databaseFile.lastModified();
			if (t != this.mtime) {
				/* GeoIP Database file updated */
				/* refresh filehandle */
				init();
			}
		} catch (final IOException e) {
			LookupService.LOGGER.fatal(e, e);
		}
	}

	// for GeoIP City only
	public Location getLocation(InetAddress addr) {
		return getLocation(LookupService.bytesToLong(addr.getAddress()));
	}

	// for GeoIP City only
	public Location getLocation(String str) {
		InetAddress addr;
		try {
			addr = InetAddress.getByName(str);
		} catch (final UnknownHostException e) {
			return null;
		}

		return getLocation(addr);
	}

	private synchronized Location getLocation(long ipnum) {
		int record_pointer;
		final byte[] record_buf = new byte[LookupService.FULL_RECORD_LENGTH];
		int record_buf_offset = 0;
		final Location record = new Location();
		int str_length = 0;
		int j;
		int seek_country;
		double latitude = 0;
		double longitude = 0;

		try {
			seek_country = seekCountry(ipnum);
			if (seek_country == this.databaseSegments[0]) {
				return null;
			}
			record_pointer = seek_country + (2 * this.recordLength - 1) * this.databaseSegments[0];

			// read from memory
			for (int i = 0; i < LookupService.FULL_RECORD_LENGTH; i++) {
				record_buf[i] = this.dbbuffer[i + record_pointer];
			}

			// get country
			record.countryCode = LookupService.countryCode[LookupService.unsignedByteToInt(record_buf[0])];
			record_buf_offset++;

			// get region
			while (record_buf[record_buf_offset + str_length] != '\0') {
				str_length++;
			}
			record_buf_offset += str_length + 1;
			str_length = 0;

			// get city
			while (record_buf[record_buf_offset + str_length] != '\0') {
				str_length++;
			}
			if (str_length > 0) {
				record.city = new String(record_buf, record_buf_offset, str_length, "ISO-8859-1");
			}
			record_buf_offset += str_length + 1;
			str_length = 0;

			// get postal code
			while (record_buf[record_buf_offset + str_length] != '\0') {
				str_length++;
			}
			record_buf_offset += str_length + 1;

			// get latitude
			for (j = 0; j < 3; j++) {
				latitude += LookupService.unsignedByteToInt(record_buf[record_buf_offset + j]) << (j * 8);
			}
			record_buf_offset += 3;

			// get longitude
			for (j = 0; j < 3; j++) {
				longitude += LookupService.unsignedByteToInt(record_buf[record_buf_offset + j]) << (j * 8);
			}

			if (this.databaseType == LookupService.CITY_EDITION_REV1) {
				// get DMA code
				int dmaarea_combo = 0;
				if ("US".equals(record.countryCode)) {
					record_buf_offset += 3;
					for (j = 0; j < 3; j++) {
						dmaarea_combo += LookupService.unsignedByteToInt(record_buf[record_buf_offset + j]) << (j * 8);
					}
				}
			}
		} catch (final IOException e) {
			System.err.println("IO Exception while seting up segments");
		}
		return record;
	}

	/**
	 * Finds the country index value given an IP address.
	 * 
	 * @param ipAddress the ip address to find in long format.
	 * @return the country index.
	 */
	private synchronized int seekCountry(long ipAddress) {
		final byte[] buf = new byte[2 * LookupService.MAX_RECORD_LENGTH];
		final int[] x = new int[2];
		int offset = 0;
		_check_mtime();
		for (int depth = 31; depth >= 0; depth--) {
			// read from memory
			for (int i = 0; i < 2 * LookupService.MAX_RECORD_LENGTH; i++) {
				buf[i] = this.dbbuffer[(2 * this.recordLength * offset) + i];
			}
			for (int i = 0; i < 2; i++) {
				x[i] = 0;
				for (int j = 0; j < this.recordLength; j++) {
					int y = buf[i * this.recordLength + j];
					if (y < 0) {
						y += 256;
					}
					x[i] += y << (j * 8);
				}
			}

			if ((ipAddress & (1 << depth)) > 0) {
				if (x[1] >= this.databaseSegments[0]) {
					return x[1];
				}
				offset = x[1];
			} else {
				if (x[0] >= this.databaseSegments[0]) {
					return x[0];
				}
				offset = x[0];
			}
		}

		// shouldn't reach here
		LookupService.LOGGER.fatal("Error seeking country while seeking " + ipAddress);
		return 0;
	}

	/**
	 * Returns the long version of an IP address given an InetAddress object.
	 * 
	 * @param address the InetAddress.
	 * @return the long form of the IP address.
	 */
	private static long bytesToLong(byte[] address) {
		long ipnum = 0;
		for (int i = 0; i < 4; ++i) {
			long y = address[i];
			if (y < 0) {
				y += 256;
			}
			ipnum += y << ((3 - i) * 8);
		}
		return ipnum;
	}

	private static int unsignedByteToInt(byte b) {
		return b & 0xFF;
	}
}
