<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.Collection" %>
<%@ page import="net.violet.platform.web.apps.OnlineObject" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Ztamp counter</title>
<link rel="stylesheet" href="css/reset.css" />
<link rel="stylesheet" href="css/grid_16.css" />
<link rel="stylesheet" href="css/layout.css" />

<script src="http://maps.google.com/maps?file=api&v=2&key=ABQIAAAAB7yqZ41gqENqqc8VwfD3ThQ2tmoUQsuJWyAJRdaRYWIWzZyo4hSHa0vceN0oT7pI9Vem8v_nlv8lbw&sensor=false" type="text/javascript"></script>
<script src="http://gmaps-utility-library.googlecode.com/svn/trunk/markermanager/release/src/markermanager.js" type="text/javascript"></script>
<script type="text/javascript" src="js/lib/prototype.js"></script>
<script type="text/javascript" src="js/lib/scriptaculous.js?load=effects" ></script>
<script type="text/javascript" src="js/counter.js"></script>

</head>

<body>

<%	
	Collection<OnlineObject> onlineObjects = (Collection<OnlineObject>)request.getAttribute("objects");
	String objectsAmount = String.valueOf(request.getAttribute("amount"));
%>

<div id="header">
	<div class="container_16">
  		<div class="grid_16">
    		<h1>Ztamp Counter</h1>
      		<h2>Powered by Violet</h2>
    	</div>
    	<div class="clear"></div>
  	</div>
</div>

<div id="slider">
	<div class="slider-content" id="filtersContainer" style="display:none;">
		<form id="filtersForm" action="#" class="container_16 filters"  >
			<div class="grid_8">
				<h4>Gender</h4>
				<label><input type="radio" name="gender" value="M"/>Male</label>
				<label><input type="radio" name="gender" value="F"/>Female</label>
				<label><input type="radio" name="gender" value="both"/>Both</label>
				<h4>Age</h4>
				<label><span>Between :</span><input type="text" name="minAge" class="digits" />years</label>
				<label><span>and :</span><input type="text" name="maxAge" class="digits" />years</label>
			</div>
			<div class="grid_8">
				<h4>Object's type</h4>
				<label class="type all"><input type="radio" name="hardware" value="violet" /><span>All</span></label>
				<label class="type bookz"><input type="radio" name="hardware" value="violet.rfid.book" /><span>Book:z</span></label>
				<label class="type ztamps"><input type="radio" name="hardware" value="violet.rfid.ztamp" /><span>Ztamp:s</span></label>
				<label class="type nanoztag"><input type="radio" name="hardware" value="violet.rfid.nanoztag" /><span>Nano:ztag</span></label>
				<label class="type other"><input type="radio" name="hardware" value="violet.rfid.other" /><span>Other</span></label>
				<label>Active in the last : <input type="text" name="timeFrame" class="digits" />hours</label>
			</div>
			<div class="clear"></div>
		</form>
	</div>
	<div class="slide" id="slideButton">
		<a href="javascript:void(0);" class="up">Fermer</a>
		<a href="javascript:void(0);" class="down">Ouvrir</a>
	</div>
</div>


<div id="main">
  
	<div class="container_16 map">
  		<div class="grid_12">
    		<div id="map_canvas" style="width: 710px; height: 500px"></div>
    	</div>
    	
    	<div class="grid_4 recent">
    		<h3>Recently active</h3>
    		<div id="highlightedActives">
				<% for(OnlineObject anObject : onlineObjects) { %> 	
        		<span class="object">
        			<span class="pic"><img src="<%=anObject.getPictureUrl() %>"/></span>
        			<span class="name"><%= anObject.getName()%></span>
        			<span class="location"><%=anObject.getLocation()%></span>
        			<a class="link" href="<%=anObject.getProfileUrl()%>">Voir son profil</a>
        		</span>    
      			<% } %>
      		</div>
    	</div>
    	
    	<div class="clear"></div>
    
    	<div class="recent more">
      		<div id="otherActives"></div>
    	</div>
	</div>
	
</div>
	
<div id="footer">
	<div class="container_16">
  		<div class="grid_16">
    		<a class="link-www" href="http://www.violet.net" title="www.violet.net"></a>
		</div>
	</div>
</div>

</body>
</html>