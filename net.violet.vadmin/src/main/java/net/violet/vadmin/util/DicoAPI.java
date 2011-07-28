package net.violet.vadmin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.common.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.dico.Create;
import net.violet.platform.api.actions.dico.Export;
import net.violet.platform.api.actions.dico.GetByKey;
import net.violet.platform.api.actions.dico.Update;
import net.violet.platform.api.actions.languages.GetObjectLanguages;
import net.violet.platform.api.actions.languages.GetSiteLanguages;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchMessageException;
import net.violet.platform.api.maps.DicoInformationMap;
import net.violet.platform.api.maps.LangInformationMap;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.MailTools;
import net.violet.vadmin.actions.Admin;
import net.violet.vadmin.objects.data.LanguageData;

import org.apache.log4j.Logger;

public class DicoAPI {

	public static final List<LanguageData> theLanguages = DicoAPI.generateTestLanguagesList();
	public static final String LOC = "LOC_";
	private static final String NO_LOC = "NO_LOC.";
	private static String MAIL_RECIPIENT = "franck@violet.net";

	private static final Logger LOGGER = Logger.getLogger(DicoAPI.class);
	// Path of the created file
	public static final String CVS_FILE_PATH = "broad/config/export.csv"; 
	private static final String EXPORT_OLDFILE_PATH = Constantes.RSC_PATH+"broadcast/broad/config/temp.csv";
	private static final String EXPORT_FINALFILE_PATH = Constantes.RSC_PATH+"broadcast/"+CVS_FILE_PATH;
	private static String KEY_VALUE = "key";
	private static Pattern csv = null;

	static {
		String pattern = "(.*)";
		for (int i = 0; i < theLanguages.size(); i++) {
			pattern += "\\|(.*)";
		}
		LOGGER.info("PATTERN =" + pattern);
		csv = Pattern.compile(pattern);
	}

	/**
	 * Call the Dico API to create a new dico key.
	 * @param inParams a map which contains the necessary values: the <code>key</code>, the <code>language</code>, the <code>text</code> and the <code>page</code> (default true).
	 * @return a <code>DicoInformationMap</code> or <code>null</code> if it failed.
	 * @throws InvalidParameterException
	 * @throws APIException
	 */
	public static Object create(Map<String, Object> inParams) throws InvalidParameterException, APIException {

		final Action theAction = new Create();
		return Admin.processRequest(theAction, inParams);
	}

	/**
	 * Call the Dico API to find all the dico entries from a key.
	 * @param inParams a map which contains the name of the key: <code>id</code>.
	 * @return a <code>DicoTextMap</code> with all the existing keys.
	 * @throws InvalidParameterException
	 * @throws NoSuchMessageException
	 * @throws APIException
	 */
	public static Object getByKey(Map<String, Object> inParams) throws InvalidParameterException, NoSuchMessageException, APIException {

		final Action theAction = new GetByKey();
		return Admin.processRequest(theAction, inParams);
	}

	/**
	 * Call the Dico API to update the values of a key.
	 * @param inParams a map which contains the necessary values and their <code>id</code>.
	 * @return a <code>DicoInformationMap</code>.
	 * @throws InvalidParameterException
	 * @throws APIException
	 */
	public static Object update(Map<String, Object> inParams) throws InvalidParameterException, APIException {

		final Action theAction = new Update();
		return Admin.processRequest(theAction, inParams);
	}

	/**
	 * Retrieves all the language from My and the API. This method will disappear with My.
	 * @return a <code>List</code> of <code>LanguageData</code>.
	 */
	public static List<LanguageData> generateTestLanguagesList() {

		final List<LanguageData> langList = new ArrayList<LanguageData>();
		List<LangInformationMap> theInformationMapList = null;

//		Site Nuxos
		final Action theAction = new GetSiteLanguages();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		try {
			theInformationMapList = (List<LangInformationMap>) Admin.processRequest(theAction, theParams);
		} catch (final APIException e) {}

		if (theInformationMapList != null) {
			for (final LangInformationMap inLangInfo : theInformationMapList) {
				langList.add(buildObjectData(inLangInfo));
			}
		}

//		Site My
		final Action theAction2 = new GetObjectLanguages();
		try {
			theInformationMapList = null;
			theInformationMapList = (List<LangInformationMap>) Admin.processRequest(theAction2, theParams);
		} catch (final APIException e) {}

		if (theInformationMapList != null) {
			for (final LangInformationMap inLangInfo : theInformationMapList) {
				langList.add(buildObjectData(inLangInfo));
			}
		}
		Collections.sort(langList, new Comparator<LanguageData>() {

			public int compare(LanguageData o1, LanguageData o2) {
				return o1.getIso_code().compareTo(o2.getIso_code());
			}
		});
		return langList;
	}

	/**
	 * Create a new <code>LanguageData</code> from a <code>LangInformationMap</code>.
	 * @param inLangInformation the <code>LanguageData</code> Map.
	 * @return a <code>LanguageData</code>.
	 */
	private static LanguageData buildObjectData(LangInformationMap inLangInformation) {

		final LanguageData theFormData = new LanguageData();

		if (inLangInformation != null) {
			theFormData.setName((String) inLangInformation.get("title"));
			theFormData.setIso_code((String) inLangInformation.get("isocode"));
		}
		return theFormData;
	}

	public static void exportDicoValues() throws APIException {
		final Action theAction = new Export();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		int index = 0;
		theParams.put("index", index);

		String actualKey = StringShop.EMPTY_STRING;
		String lastKey = StringShop.EMPTY_STRING;
		SortedMap<String, String> valueByIso = new TreeMap<String, String>();
		int sizeRow = 1;
		int languagesSize = theLanguages.size();

		File finalFile = new File(EXPORT_OLDFILE_PATH);
		if (finalFile.exists()) {
			finalFile.delete();
		}

		FileWriter writer = null;
		String line = KEY_VALUE;
		try {
			writer = new FileWriter(EXPORT_OLDFILE_PATH, true);
			for (LanguageData aLanguageData : theLanguages) {
				line += "|" + aLanguageData.getIso_code();
			}
			writer.write(line + "\n");
			line = StringShop.EMPTY_STRING;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		while (sizeRow > 0) {
			List<DicoInformationMap> theExportList = (List<DicoInformationMap>) Admin.processRequest(theAction, theParams);
			sizeRow = theExportList.size();

			//		Formated the data by key
			for (DicoInformationMap aDicoData : theExportList) {
				actualKey = aDicoData.getString(DicoInformationMap.KEY);
				if (!actualKey.equals(lastKey)) {
					LOGGER.info("lastKey=" + lastKey);
					LOGGER.info("actualKey=" + actualKey);
					if (!StringShop.EMPTY_STRING.equals(lastKey)) {
						LOGGER.info("NEW ENTRY");
						writeIntoFile(writer, lastKey, fillLanguages(valueByIso, lastKey));
						valueByIso = new TreeMap<String, String>();
					}
					lastKey = actualKey;
				}
				LOGGER.info("ADD A NEW VALUE FOR=" + aDicoData.getString(DicoInformationMap.LANGUAGE) + " AND " + aDicoData.getString(DicoInformationMap.TEXT));
				valueByIso.put(aDicoData.getString(DicoInformationMap.LANGUAGE), aDicoData.getString(DicoInformationMap.TEXT));
			}

			LOGGER.info("SIZE=" + theExportList.size());
			theParams.put("index", index += 5000);
		}

		if (valueByIso.size() == languagesSize || sizeRow == 0) {
			LOGGER.info("END OF PROCESS");
			writeIntoFile(writer, lastKey, fillLanguages(valueByIso, lastKey));
			valueByIso = new TreeMap<String, String>();
		}

		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		finalFile.renameTo(new File(EXPORT_FINALFILE_PATH));

	}

	private static SortedMap<String, String> fillLanguages(SortedMap<String, String> inValues, String inKey) {

		for (LanguageData aLanguageData : theLanguages) {
			if (inValues.get(aLanguageData.getIso_code()) == null) {
				LOGGER.info("Clean:" + aLanguageData.getIso_code());
				inValues.put(aLanguageData.getIso_code(), NO_LOC+inKey);
			}
		}
		return inValues;
	}

	private static void writeIntoFile(FileWriter inWriter, String keyName, SortedMap<String, String> inValues) {

		String line =  keyName;
		for (Entry<String, String> value : inValues.entrySet()) {
			line += "|" + value.getValue();
		}
		try {
			inWriter.write(line + "\n");
		} catch (IOException e) {}
	}

	public static void importDicoValues(InputStream inStream) throws APIException {

		String line;
		BufferedReader theBuffReader = new BufferedReader(new InputStreamReader(inStream));
		try {
			boolean error = false;
			while ((line = theBuffReader.readLine()) != null) {
				final Matcher theMatcher = csv.matcher(line);
				if (theMatcher.matches()) {
					String key = theMatcher.group(1);
					if (!KEY_VALUE.equals(key)) {
						Map<String, Object> theParams = new HashMap<String, Object>();
						theParams.put("id", key);
						
						Map<String, DicoInformationMap> dicoAvailable = new HashMap<String, DicoInformationMap>();
						
						for(DicoInformationMap aDicoMap : (List<DicoInformationMap>) getByKey(theParams)){
							dicoAvailable.put(aDicoMap.getString("language"), aDicoMap);
						}
						
						for (int i = 2; i <= theMatcher.groupCount(); i++) {
							
							theParams.put("language", theLanguages.get(i - 2).getIso_code());
							theParams.put("key", key);
							theParams.put("text", StringShop.EMPTY_STRING.equals(theMatcher.group(i))?NO_LOC+key:theMatcher.group(i));
							theParams.put("page", StringShop.EMPTY_STRING);

							DicoInformationMap theDicoInformationMap = dicoAvailable.get(theLanguages.get(i - 2).getIso_code());
							
							if(theDicoInformationMap == null){
								create(theParams);
							} else {
								theParams.put("id", theDicoInformationMap.getString("id"));
								theParams.put("page", theDicoInformationMap.getString("page"));
								update(theParams);
							}
						}
					}
				} else {
					error = true;
					break;
				}
			}
			if(error) {
				MailTools.sendFromAdmin(MAIL_RECIPIENT, "ADMIN: Import failed , bad csv !!", line);
			} else { 
				MailTools.sendFromAdmin(MAIL_RECIPIENT, "ADMIN: Import ok!!", StringShop.EMPTY_STRING);
			}
		} catch (IOException e) {
			MailTools.sendFromAdmin(MAIL_RECIPIENT, "ADMIN: Import failed!!", e.getMessage());
			e.printStackTrace();
		}
	}
}
