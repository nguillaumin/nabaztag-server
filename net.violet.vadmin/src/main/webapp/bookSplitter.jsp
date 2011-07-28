<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"
	language="java"%>
<%
	response.setContentType("text/html;charset=UTF-8");
%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	int countRow = 0;
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="../include_js/conftools/swfobject-1.5.1.js"></script>

<title>Book Splitter</title>

</head>
<body>

<logic:messagesPresent message="errors" property="fileError"> 
		<html:errors property="fileError"/>
</logic:messagesPresent>

<html:form action="/action/bookSplitter" enctype="multipart/form-data">
<bean:define name="bookSplitterForm" property="file" id="fileId"/>
<logic:empty name="bookSplitterForm" property="musicFile">
<html:file name="bookSplitterForm" property="musicFile" styleId="musicFile" />
<input type="text" name="file" value="<%= fileId%>"/>

</logic:empty>

<logic:notEmpty name="bookSplitterForm" property="musicFile">
<bean:define name="bookSplitterForm" property="fileName" id="fileName"/>
<script type="text/javascript">
function createPlayer(rowId, offset) {

	var fileName = "<%=fileName%>";
	var fo = new SWFObject('../include_flash/mp3player.swf', "player", 350, '20', '7', null, true);
	fo.addParam('align', 'center');
	fo.addParam('wmode', 'transparent');
	fo.addVariable('backcolor', '0xEEEEFF'); 
	fo.addVariable('frontcolor', '0x886622');
	
	fo.addVariable('file', "http://192.168.1.11/vl/bookpreview%3FfileName=".concat(fileName).concat("%26offset=").concat(offset, "%26s=.mp3"));
	fo.addVariable('enablejs', "true");	
	fo.addVariable('autostart', "false");	
	
	if (!fo.write("player-".concat(rowId))) {
		var container = document.getElementById("player-".concat(rowId));
		if (container) container.innerHTML = fo.getSWFHTML();
	}
}

</script>
	<table id="matable" border="1">
		<tr>
			<bean:define name="bookSplitterForm" property="nbBytes" id="nbBytes"/>
			<td><label style="display: block;">Nom</label></td>
			<td><label style="display: block;">Temps</label></td>
			<td><label style="display: block;">Octets</label></td>
		</tr>
		<logic:iterate name="bookSplitterForm" property="listPoint"
			id="listPoint">
			<bean:define name="listPoint" property="sec" id="sec" />
			<bean:define name="listPoint" property="nbByte" id="nbByte" />
			<tr>
				<td><input type="text" name="chapitres" value="Chapitre 1"  /></td>
				<td><input type="text" name="secs" value="<%=sec%>" onchange="javascript:convert(<%=countRow %>);javascript:updatePlayer(<%=countRow %>);" /></td>
				<td><input type="text" id="nbBytes-<%=countRow %>" name="nbBytes"  onchange="javascript:updatePlayer(<%=countRow %>);convertToTime(<%=countRow %>);"  value="<%=nbByte%>" /></td>
				<td>
				<div name="play" id="player-<%=countRow%>" onclick ="javascript:createPlayer(<%=countRow%>,<%=nbByte%>);">
				<script type="text/javascript">
				var offset = document.getElementsByName("nbBytes")[<%=countRow%>].value;
				createPlayer(<%=countRow++%>, offset );
				</script>
				</div>
				</td>
			</tr>
		</logic:iterate>
	</table>
	<input type="button" value="+" onclick="javascript:addRow();" />
	<input type="hidden" name="file" value="<%=fileId%>" />
	<input type="hidden" name="fileName" value="<%=fileName%>" />
	</logic:notEmpty>
	<input type="submit" value="OK" />
</html:form>
<script type="text/javascript">
var countRowJs = <%=countRow%>;

function updatePlayer(rowId) {
	document.getElementById("player-".concat(rowId)).innerHtml = "";
	var offset = document.getElementsByName("nbBytes")[rowId].value; //nbBytes
	createPlayer(rowId, offset);
}

function addRow(){
var newRow = document.getElementById('matable').insertRow(-1);
var newCell = newRow.insertCell(0);
inputChapitre = document.createElement ("input");
inputChapitre.setAttribute("type", "text");
inputChapitre.setAttribute("name", "chapitres");
inputChapitre.setAttribute("value", "Chapitre ".concat(countRowJs+1));
inputChapitre.setAttribute("onChange", "");
newCell.appendChild (inputChapitre);
var newCell = newRow.insertCell(1);
inputSec = document.createElement ("input");
inputSec.setAttribute("type", "text");
inputSec.setAttribute("name", "secs");
inputSec.setAttribute("value", "00:00.0");
inputSec.setAttribute("onChange", "javascript:convert(".concat(countRowJs,");updatePlayer(", countRowJs,");"));
newCell.appendChild (inputSec);
newCell = newRow.insertCell(2);
inputByte = document.createElement ("input");
inputByte.setAttribute("type", "text");
inputByte.setAttribute("name", "nbBytes");
inputByte.setAttribute("value", "0");
inputByte.setAttribute("onChange", "javascript:convertToTime(".concat(countRowJs,");updatePlayer(", countRowJs,");"));
newCell.appendChild (inputByte);

newCell = newRow.insertCell(3);
inputPlay = document.createElement("div");
inputPlay.setAttribute("id", "player-".concat(countRowJs));
newCell.appendChild(inputPlay);

createPlayer(countRowJs++, 0);
}

// Script pour la mise à jour automatique du champs Octets à partir du champs Temps.
var bitrate	= <bean:write name="bookSplitterForm" property="bitRate"/>;

function convert(inRow) {
	var theSec = document.getElementsByName("secs")[inRow].value;
	var theResult = theSec.match("^(\\d{1,2}):(\\d{2})\\.(\\d{1,2})$");
//TODO alert si le format est mauvais
	if (theResult != null && theResult.length == 4){
		var minute = parseInt(theResult[1], 10);
		var sec = parseInt(theResult[2], 10);
		var milli = parseInt(theResult[3], 10);
	
	if (theResult[3].length < 2) {
		milli *= 100;
	} else {
		milli *=10;
	}		
	minute *= bitrate;
	sec *= bitrate;
	milli *= bitrate;
//	alert("milli "+milli/1000);
	
	document.getElementsByName("nbBytes")[inRow].value = Math.floor((sec*1000 + minute*60000 + milli)/8);
	}
}

function convertToTime(inRow) {

  var octets = document.getElementsByName("nbBytes")[inRow].value
  octets /= (bitrate/8); //320
  var milli = octets%1000;
  var minsec = (octets - milli)/1000;
  var sec = minsec%60;
  var min = (minsec - sec)/60;

	//alert("min "+min+" sec "+sec+" milli "+milli);
  milli =Math.ceil(milli/10);
   
  if(milli<10)
  	milli="0"+String(milli); 
  if(sec<10)
  	sec="0"+String(sec);
  if(min<10)
  	min="0"+String(min);
	//alert("min "+min+" sec "+sec+" milli "+milli);
  var temps = min+":"+sec+"."+milli;
 
	document.getElementsByName("secs")[inRow].value = temps;
}
</script>
</body>
</html>
