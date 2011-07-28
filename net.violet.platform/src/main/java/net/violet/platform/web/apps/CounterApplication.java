package net.violet.platform.web.apps;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.platform.api.converters.ConverterFactory;
import net.violet.platform.datamodel.MimeType.MIME_TYPES;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.events.ZtampDetectionEvent;
import net.violet.platform.events.ZtampRemovalEvent;

public class CounterApplication implements DemoApplication {

	private final OnlineObjectsContainer container = new OnlineObjectsContainer();

	private void ztampInteraction(String event, HttpServletRequest request, HttpServletResponse response) {
		final VObjectData ztamp = VObjectData.findBySerial(request.getParameter("ztampserial"));
		final VObjectData reader = VObjectData.findBySerial(request.getParameter("reader"));

		if ((ztamp != null) && ztamp.isValid() && ztamp.getObjectType().instanceOf(ObjectType.RFID) && (reader != null) && reader.isValid()) {
			final OnlineObject onlineObject = new OnlineObject(ztamp, reader);

			if (ZtampDetectionEvent.NAME.equals(event)) {
				this.container.addObject(onlineObject);
			} else if (ZtampRemovalEvent.NAME.equals(event)) {
				this.container.removeObject(onlineObject);
			}
		}

		try {
			sendJSONResponse(response, this.container.getAmount());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void sendJSONResponse(HttpServletResponse response, Object value) throws IOException {
		response.setContentType(MIME_TYPES.JSON.getLabel());
		response.getWriter().print(ConverterFactory.JSON.convertTo(value));
		response.getWriter().close();
	}

	private void getObjectsUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {
			final Map<String, List<OnlineObject>> result = new HashMap<String, List<OnlineObject>>();

			final List<OnlineObject> onlineObjects;
			if ((request.getParameter("all") == null) || !Boolean.valueOf(request.getParameter("all"))) {
				onlineObjects = this.container.getNewObjects();
			} else {
				onlineObjects = this.container.getOnlineObjects();
			}

			result.put("online", onlineObjects);
			result.put("offline", this.container.getOfflineObjects());
			sendJSONResponse(response, result);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public String process(HttpServletRequest request, HttpServletResponse response) {

		final String event = request.getParameter("event");
		if (event != null) {
			ztampInteraction(event, request, response);
			return null;
		}

		// a special action may have been called
		final String action = request.getParameter("action");
		if ((action != null) && action.equals("getObjectsUpdate")) {
			getObjectsUpdate(request, response);
			return null;
		}

		request.setAttribute("objects", this.container.getOnlineObjects(5));
		request.setAttribute("amount", this.container.getAmount());

		return "counter.jsp";
	}

}
