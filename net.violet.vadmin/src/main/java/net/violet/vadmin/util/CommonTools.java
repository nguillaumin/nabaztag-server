package net.violet.vadmin.util;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.files.Synthetize;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.TTSGenerationFailedException;
import net.violet.platform.api.maps.FilesInformationMap;
import net.violet.platform.datamodel.Feed.Type;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.feeds.FeedsTools;
import net.violet.vadmin.actions.Admin;



public class CommonTools {
	
	/**
	 * Generate a Text To Speech from a Text and a Language.
	 * @param inText the text to generate
	 * @param getId <code>boolean</code> if the information generated have to be returned
	 * @param languageCode the language to convert the text
	 * @return the Id of the File generated 
	 * @throws InvalidParameterException
	 * @throws TTSGenerationFailedException
	 * @throws APIException
	 */
	public static String generateTTS(String inText, boolean getId, String languageCode) throws InvalidParameterException, TTSGenerationFailedException, APIException {
		
		final Action theAction = new Synthetize();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("text", inText);
		theParams.put("voice", languageCode);
		theParams.put("getInfo", getId);
		final FilesInformationMap theFilesInformationMap = (FilesInformationMap) Admin.processRequest(theAction, theParams);
		return theFilesInformationMap.getString("id");
	}
	
	/**
	 * Check the validity of the URL given
	 * @param inURL the URL to check
	 * @return <code>false</code> if the URL is invalid or alreaedy existing in the database; or <code>true</code> if the URL is valid.
	 */
	public static boolean checkURL(String inURL){
		
		if(Factories.FEED.findByUrlAndType(inURL, Type.PODCAST) != null || Factories.FEED.findByUrlAndType(inURL, Type.RSS) != null){
			return false;
		}
		
		return FeedsTools.isFeedValid(inURL, null, null);
	}
}
