<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Violet &bull;&bull; Let All Things Be Connected</title>

<style type="text/css">
body {background-position:left top; background-repeat:no-repeat; margin:0; padding:0; font-family: "lucida grande", verdana, arial, sans-serif; font-size:1.2em; }
body.white {background-image: url(images/bg_page_video.gif); }
body.blue {background-image: url(images/bg_blue.gif); }
body.kiwi {background-image: url(images/bg_kiwi.gif); }
body.lime {background-image: url(images/bg_lime.gif); }
body.silver {background-image: url(images/bg_silver.gif); }
body.lemon {background-image: url(images/bg_lemon.gif); }
body.apricot {background-image: url(images/bg_apricot.gif); }
body.orange {background-image: url(images/bg_orange.gif); }
body.raspberry {background-image: url(images/bg_raspberry.gif); }
body.black {background:#000;}
.content { display:block; width:700px; margin:0 auto; }
#header { display:block; margin-top:10px; }

#header a.logo {display:block; width:188px; height:70px; background:url(images/logo_violet_top.png) no-repeat left top; text-decoration:none; }
#header a.logo span {visibility:hidden;}
.black #header a.logo {background:url(images/logo_violet_top_white.png) no-repeat left top; }



#header a.logo span { visibility:hidden; }
#video {}
.player-video { text-align:center; height:551px; padding-top:20px; }
#footer {display:none;}
#footer .link-www { float:right; display:block; height:35px; width:80px; background: url(logo_violet_small.gif) no-repeat left top; margin-top:20px; }
</style>

</head>

<% 	String videoId = request.getParameter("id"); 
	String background = request.getParameter("background");
%>

<body class="<%= background %>">

<div id="header">
  <div class="content"> <a href="http://my.violet.net/welcome" class="logo" title="Violet &bull;&bull; Let All things Be Connected"><span>Violet &bull;&bull; Let all things Be Connected</span></a> </div>
</div>
<div id="video">
  <div class="content">
    <div class="player-video">
      <object width="680" height="551">
        <param name="movie" value="http://www.youtube.com/v/<%=videoId%>&hl=en&fs=1"></param>
        <param name="allowFullScreen" value="true"></param>
        <param name="allowscriptaccess" value="always"></param>
        <embed src="http://www.youtube.com/v/<%=videoId%>&hl=en&fs=1&autoplay=1" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="680" height="551"></embed>
      </object>
    </div>
  </div>
</div>
<div id="footer">
  <div class="content"> <a class="link-www" href="http://www.violet.net" title="www.violet.net"></a> </div>
</div>


</body>
</html>
