<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="net.violet.platform.management.Maintenance"%>

<% final Maintenance theMessage = Maintenance.getInstance(); %>

<bean:define id="display" value="<%=theMessage.getDisplay()%>"/>

<logic:equal name="display" value="ON">

<div class="top-warning-msg" style="margin:0; padding:0;  width:100%; position:absolute; top:0, left:0 text-align:center; z-index:1; opacity:0.80; filter:alpha(opacity=80)">
	<span style="position:relative; padding-right:15px; color:black; border:1px solid black; border-top:none; font-weight:bold; line-height:20px; display:block;  background:white; margin:0 240px 0 90px; text-align:center;">&bull;&nbsp;
		<%=theMessage.getMessage() %>
	&nbsp;&bull;<a class="close" href="javascript:hideMaintenanceMsg('<%=theMessage.getEndMaintenance(true) %>')" style="width:12px; height:13px; display:block; position:absolute; top:2px; right:2px; text-indent:-5000px; background-image:url(/vl/include_img/closeWin.gif);background-repeat:no-repeat;">[X]</a></span>
</div>
</logic:equal>
