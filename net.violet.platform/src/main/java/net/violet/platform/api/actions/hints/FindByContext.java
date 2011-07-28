package net.violet.platform.api.actions.hints;

import java.util.LinkedList;
import java.util.List;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchContextException;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.ContextData;
import net.violet.platform.dataobjects.HintData;
import net.violet.platform.util.Constantes;

public class FindByContext extends AbstractHintAction {

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchContextException {

		final String theNameContext = inParam.getMainParamAsString();

		final List<HintInformationMap> resultList = new LinkedList<HintInformationMap>();
		final ContextData theContextData = ContextData.findByName(theNameContext);

		if (theContextData != null) {
			for (final HintData theHintData : theContextData.getListHints()) {
				resultList.add(new HintInformationMap(inParam.getCaller(), theHintData));
			}
		} else {
			throw new NoSuchContextException();
		}

		return resultList;

	}

	// Action interface

	public ActionType getType() {
		return ActionType.GET;
	}

	public boolean isCacheable() {
		return true;
	}

	public long getExpirationTime() {
		return Constantes.ONE_DAY_IN_S;
	}

	private static class HintInformationMap extends AbstractAPIMap {

		public HintInformationMap(APICaller inCaller, HintData inHintData) {
			super();

			put("id", inHintData.getApiId(inCaller));
			put("title", inHintData.getTitle());
			put("content", inHintData.getContent());
			put("image", inHintData.getPicture());
			put("image_width", inHintData.getPictureWidth());
			put("image_height", inHintData.getPictureHeight());
			put("link", inHintData.getLink());
			put("modification_date", inHintData.getModificationDate());
			put("creation_date", inHintData.getCreationDate());

		}

	}
}
