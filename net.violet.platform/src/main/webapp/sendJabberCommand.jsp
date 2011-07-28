<%@page import="net.violet.platform.interactif.EntryPoint"%>
<%
final String what = request.getParameter("what");

if (what != null) {
	out.clearBuffer();
	out.print(EntryPoint.sendJabberCommand(request));
} else {
%>
<html>
<head>
<title>SendInteractifCommand</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15">

</head>
<body>

<form name="form" action="sendJabberCommand.jsp?what=gosleeporactive" method="post" >
	<table height="80" border="0" cellpadding="0" cellspacing="0" >
	    <tr > 
	      <th width="36%" height="27"align="center" >Send jabber's "goSleep" or "wakeUp" packet</th>
	     </tr>
	    <tr > 
	      <th width="36%" height="27"  >Nabname: </th>
	      <td width="64%" height="27" valign="bottom"><input name="nabname" type="text" size="30"></td>
	    </tr>
	    <tr>
	    <th width="36%" height="27"  > Action: </th>
	      <td width="64%" height="27" valign="bottom">
		      go to sleep : <input type="radio" name="goto" value="sleep"/>
		      wake up : <input type="radio" name="goto" value="active" checked="checked"/>
	      </td>
	    </tr>
	    <tr>
	      <th height="31" align="center"> 
	      <input type="submit" name="Submit" value="Send">
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="reset" name="Submit2" value="Cancel" ></th>
	    </tr>
	</table>
</form>

<form name="form" action="sendJabberCommand.jsp?what=resources" method="post" >
	<table height="80" border="0" cellpadding="0" cellspacing="0" >
	    <tr > 
	      <th width="36%" height="27"align="center" >Get object's available resources</th>
	     </tr>
	    <tr > 
	      <th width="36%" height="27"  >Nabname: </th>
	      <td width="64%" height="27" valign="bottom"><input name="nabname" type="text" size="30"></td>
	    </tr>
	    <tr>
	      <th height="31" align="center"> 
	      <input type="submit" name="Submit" value="Send">
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="reset" name="Submit2" value="Cancel" ></th>
	    </tr>
	</table>
</form>

<form name="form" action="sendJabberCommand.jsp?what=send" method="post" >
  <table height="80" border="0" cellpadding="0" cellspacing="0" >
    <tr > 
      <th width="36%" height="27"align="center" >Send Jabber Command</th>
     </tr>
    <tr > 
      <th width="36%" height="27"  >Nabname: </th>
      <td width="64%" height="27" valign="bottom"><input name="nabname" type="text" size="30"></td>
    </tr>
    <tr>
      <th width="36%" height="27"  > Mode: </th>
      <td width="64%" height="27" valign="bottom">
<select name="rsrc">
  <option value="1" selected="selected">default</option>
  <option value="2">idle</option>
  <option value="3">notify</option>
  <option value="4">sources</option>
  <option value="5">streaming</option>
  <option value="6">asleep</option>
  <option value="7">urgent</option>
  <option value="8">drop</option>
</select>
      </td>
    </tr>
    <tr>
      <th width="36%" height="27"  > Ttl: </th>
      <td width="64%" height="27" valign="bottom">
	      <input type="text" name="ttl"/>
	      (en secondes)
      </td>
    </tr>
    <tr>
      <th width="36%" height="27"  > Type: </th>
      <td width="64%" height="27" valign="bottom">
	      action : <input type="radio" name="mode" value="1" checked="checked"/>
	      update source : <input type="radio" name="mode" value="2"/>
	      pojo : <input type="radio" name="mode" value="3"/>
      </td>
    </tr>
    <tr>
    	<th width="36%" height="27"  > Format (for pojo message) : </th>
		<td width="64%" height="27" valign="bottom">
			<select name="format">
			  <option value="xml" selected="selected">XML</option>
			  <option value="json">JSON</option>
			  <option value="yaml">YAML</option>
			</select>
      	</td>
    </tr>
    <tr> 
      <th width="36%" height="27"  >Commands: </th>
     <td><textarea accept-charset="utf-8" rows="16" cols="90"  name="commandes"></textarea></td>
    </tr>
    <tr> 
      <th height="31" align="center"> 
      <input type="submit" name="Submit" value="Send">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <input type="reset" name="Submit2" value="Cancel" ></th>
    </tr>
  </table>
</form>

<form name="form" action="sendJabberCommand.jsp?what=raw" method="post" >
  <table height="80" border="0" cellpadding="0" cellspacing="0" >
    <tr > 
      <th width="36%" height="27"align="center" >Send jabber raw packet</th>
     </tr>
    <tr > 
      <th width="36%" height="27"  >Packet: </th>
     <td><textarea accept-charset="utf-8" rows="16" cols="90"  name="packet"></textarea></td>
    </tr>
    <tr> 
      <th height="31" align="center"> 
      <input type="submit" name="Submit" value="Send">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <input type="reset" name="Submit2" value="Cancel" ></th>
    </tr>
  </table>
</form>

<form name="form" action="sendJabberCommand.jsp?what=getconfig"
	method="post">
<table height="80" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<th width="36%" height="27" align="center">Get rabbit's
		configuration</th>
	</tr>
	<tr>
		<th width="36%" height="27">Nabname:</th>
		<td width="64%" height="27" valign="bottom"><input name="nabname"
			type="text" size="30"></td>
	</tr>
	<tr>
		<th height="31" align="center"><input type="submit" name="Submit"
			value="Send">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="reset"
			name="Submit2" value="Cancel"></th>
	</tr>
</table>
</form>

<form name="form" action="sendJabberCommand.jsp?what=getrunningstate"
	method="post">
<table height="80" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<th width="36%" height="27" align="center">Get rabbit's
		current state</th>
	</tr>
	<tr>
		<th width="36%" height="27">Nabname:</th>
		<td width="64%" height="27" valign="bottom"><input name="nabname"
			type="text" size="30"></td>
	</tr>
	<tr>
		<th height="31" align="center"><input type="submit" name="Submit"
			value="Send">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="reset"
			name="Submit2" value="Cancel"></th>
	</tr>
</table>
</form>


</body>
</html>
<% } %>
