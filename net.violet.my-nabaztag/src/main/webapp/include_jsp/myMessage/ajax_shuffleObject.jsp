<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

document.getElementById("destName").value="<bean:write name="myMessagesForm" property="pseudo" />";
