/*
 * Thickbox 2.0 - One Box To Rule Them All.
 * By Cody Lindley (http://www.codylindley.com)
 * Copyright (c) 2006 cody lindley
 * Licensed under the MIT License:
 *   http://www.opensource.org/licenses/mit-license.php
 * Thickbox is built on top of the very light weight jQuery library.
 * 
 * Nabaztag adapted Nicolas CHALLEIL. 2006
 */

//on page load call TB_init
$(document).ready(TB_init);

function noCache(url){
	var ts = new Date().getTime(); 

	if (url.indexOf("?")>0) {
		url+="&nocache="+ts;
	} else {
		url+="?nocache="+ts;		
	}	
	
	return url;
}

var gTB_params = new Object ();

//add thickbox to href elements that have a class of .thickbox
// init trés modifiée par Nico C, on peut indiquer a init un "div" de context
function TB_init(idDivContext){
	var f=	function(){
				var t = this.title || this.name || null;
				var g = this.rel || false;
				TB_show(t,this.href,g);
				this.blur();
				return false;
			};
	
	//$("a.thickbox").unbind("click", f);
			
	if (idDivContext!=null) {
		
		if (idDivContext.length!=null) {
			$("a.thickbox", document.getElementById(idDivContext)).click(f);
		} else {
			$("a.thickbox", idDivContext).click(f);
		}
		
	} else {
		
		$("a.thickbox").click(f);
		
	}
}

function TB_lockUserClose(state){
	if (state == true) {
		$("#TB_overlay").unbind("click");
		$("#TB_closeWindowButton").unbind("click");
		$("#TB_closeWindowButton").hide();
	} else {
		$("#TB_overlay").click(TB_remove);
		$("#TB_closeWindowButton").click(TB_remove);	
		$("#TB_closeWindowButton").show();		
	}
}


function TB_show(caption, url, imageGroup) {//function called when the user clicks on a thickbox link
	try {
		if (document.getElementById("TB_HideSelect") == null) {
		$("body").append("<iframe id='TB_HideSelect'></iframe><div id='TB_overlay'></div><div id='TB_window'></div>");
		$("#TB_overlay").click(TB_remove);
		}
		
		if(caption==null){caption=""};
		
		$(window).scroll(TB_position);
 		
		TB_overlaySize();
		
		$("body").append("<div id='TB_load'><img src='../include_img/loadingAnimation.gif?v=1' /></div>");
		TB_load_position();
		
		var urlString = /\.jpg|\.jpeg|\.png|\.gif|\.html|\.htm|\.php|\.cfm|\.asp|\.aspx|\.jsp|\.jst|\.rb|\.txt|\.do|\.bmp/g;
		var urlType = url.toLowerCase().match(urlString);
		
		if(urlType=='.do'||urlType=='.htm'||urlType=='.html'||urlType=='.php'||urlType=='.asp'||urlType=='.aspx'||urlType=='.jsp'||urlType=='.jst'||urlType=='.rb'||urlType=='.txt'||urlType=='.cfm' || (url.indexOf('TB_inline') != -1) || (url.indexOf('TB_iframe') != -1) ){//code to show html pages
			
			var queryString = url.replace(/^[^\?]+\??/,'');
			var params = TB_parseQuery( queryString );
			
			TB_WIDTH = (params['width']*1) + 16; // original : 30 (Nico C)
			TB_HEIGHT = (params['height']*1) + 40;
			ajaxContentW = TB_WIDTH - 16; // original : 30 (Nico C)
			ajaxContentH = TB_HEIGHT - 45;
			
			if(url.indexOf('TB_iframe') != -1){				
					urlNoQuery = url.substr(0,TB_strpos(url, "?"));			
					$("#TB_window").append("<div id='TB_title'><div id='TB_ajaxWindowTitle'>"+caption+"</div><div id='TB_closeAjaxWindow'><a href='#' id='TB_closeWindowButton'><span>close</span></a></div></div><iframe src='"+urlNoQuery+"' id='TB_iframeContent' style='width:"+(ajaxContentW + 30)+"px;height:"+(ajaxContentH + 18)+"px;'></iframe>");
				}else{
					$("#TB_window").append("<div id='TB_title'><div id='TB_ajaxWindowTitle'>"+caption+"</div><div id='TB_closeAjaxWindow'><a href='#' id='TB_closeWindowButton'><span>close</span></a></div></div><div id='TB_ajaxContent' style='width:"+ajaxContentW+"px;height:"+ajaxContentH+"px;'></div>");
			}
					
			$("#TB_closeWindowButton").click(TB_remove);
			
				if(url.indexOf('TB_inline') != -1){	
					$("#TB_ajaxContent").html($('#' + params['inlineId']).html());
					TB_position();
					$("#TB_load").remove();
					$("#TB_window").css({display:"block"}); 
				}else if(url.indexOf('TB_iframe') != -1){
					TB_position();
					$("#TB_load").remove();
					$("#TB_window").css({display:"block"}); 
				}else{
					$("#TB_ajaxContent").load(noCache(url), function(){
						TB_position();
						$("#TB_load").remove();
						$("#TB_window").css({display:"block"}); 
					});
				}
			
		}
		
		$(window).resize(TB_position);
		
	} catch(e) {
		alert( e );
	}
}

//helper functions below

function TB_remove() {
	gTB_params = null;
	$("#TB_window").fadeOut("fast",function(){$('#TB_window,#TB_overlay,#TB_HideSelect').remove();});
	$("#TB_load").remove();
	$(document).unbind("keyup");
	return false;
}

function TB_position() {
	var pagesize = TB_getPageSize();	
	var arrayPageScroll = TB_getPageScrollTop();
	
	$("#TB_window").css({width:TB_WIDTH+"px",left: ((pagesize[0] - TB_WIDTH)/2)+"px", top: (arrayPageScroll[1] + ((pagesize[1]-TB_HEIGHT)/2))+"px" });
	TB_overlaySize();
}

function TB_overlaySize(){
	if (window.innerHeight && window.scrollMaxY) {	
		yScroll = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
		yScroll = document.body.scrollHeight;
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
		yScroll = document.body.offsetHeight;
  	}
	$("#TB_overlay").css("height",yScroll +"px");
	$("#TB_HideSelect").css("height",yScroll +"px");
}

function TB_load_position() {
	var pagesize = TB_getPageSize();
	var arrayPageScroll = TB_getPageScrollTop();

	$("#TB_load")
	.css({left: ((pagesize[0] - 100)/2)+"px", top: (arrayPageScroll[1] + ((pagesize[1]-100)/2))+"px" })
	.css({display:"block"});
}

function TB_parseQuery ( query ) {
   var Params = new Object ();
   if ( ! query ) return Params; // return empty object
   var Pairs = query.split(/[;&]/);
   for ( var i = 0; i < Pairs.length; i++ ) {
      var KeyVal = Pairs[i].split('=');
      if ( ! KeyVal || KeyVal.length != 2 ) continue;
      var key = unescape( KeyVal[0] );
      var val = unescape( KeyVal[1] );
      val = val.replace(/\+/g, ' ');
      Params[key] = val;
   }
   return Params;
}

function TB_getPageScrollTop(){
	var yScrolltop;
	if (self.pageYOffset) {
		yScrolltop = self.pageYOffset;
	} else if (document.documentElement && document.documentElement.scrollTop){	 // Explorer 6 Strict
		yScrolltop = document.documentElement.scrollTop;
	} else if (document.body) {// all other Explorers
		yScrolltop = document.body.scrollTop;
	}
	arrayPageScroll = new Array('',yScrolltop);
	return arrayPageScroll;
}

function TB_getPageSize(){
	var de = document.documentElement;
	var w = window.innerWidth || self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
	var h = window.innerHeight || self.innerHeight || (de&&de.clientHeight) || document.body.clientHeight;
	
	arrayPageSize = new Array(w,h);
	return arrayPageSize;
}

function TB_strpos(str, ch) {
	for (var i = 0; i < str.length; i++)
	if (str.substring(i, i+1) == ch) return i;
	return -1;
}

