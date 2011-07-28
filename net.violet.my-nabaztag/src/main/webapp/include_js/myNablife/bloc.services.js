/**
 * @author nicolas
 */
bloc.services = function($) {
	var c = null;
	
    return {
		
		init: function(){
			contener = $("#bloc-MyServices");
			$("ul.list a", contener)
				.unbind()
				.bind("click", bloc.services.serviceLoader);
		},
		
		clearSelected: function(){
			$("ul.list a", contener).removeClass("selected");
		},

		getElementByServiceName: function(name){
			var obj = $("a[title*="+name+"]");
			var found = false;
			$(obj).each(function(index, o){
			    var title = $.trim( $(o).attr("title"));
			    if (title == name){
			        found = o;
			        return false;
			    }
			});
			return found;
		},
		
		// on peut donner directement l'element
		// OU on peut donner le nom du service qu'on veut détruire (attention ça ne détruit que l'affichage !)		
		elementDestroy: function(ele){
			var e = ($(ele).length > 0) ? ele : bloc.services.getElementByServiceName(ele);
			var p = $(e).parents("li");
			bloc.services.clearSelected();
			$(p).destroyMe();						
		},
		
		// on peut donner directement l'element
		// OU on peut donner le nom du service qu'on veut activer
		elementHighlight: function(ele){
			var e = ($(ele).length > 0) ? ele : bloc.services.getElementByServiceName(ele);
			var p = $(e).parents("li");
			$("a", p).addClass("selected");
			
			// le le service est pas visible dans la colonne, on va sur le bon onglet
			if ( !$(p).is(":visible") && $(p).length>0) 
				$("ul.tri a.tout", contener).trigger("click");
			
			//
		},
		
		elementLoadingOff:function() {
			$("img.loader", contener ).hide();
		},
		
		serviceLoader: function(){
			//#bloc-MyServices .list li img.loader {display:none; position:absolute; left:-5px; top:2px;}
			var url = $(this).attr("href");
			
			
			if ( nablife.loadSrvPage(url) != -1 ) { // c'est pas puissant ça ? :)
				var pli = $(this).parents("li");
				
				// on affiche le ptit loader
				$("img.loader", pli).show();
				
				bloc.services.clearSelected();
				bloc.services.elementHighlight(this);
			}
			
			return false;
		}
		
	}

}(jQuery);	