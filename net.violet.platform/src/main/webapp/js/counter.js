/* Contains all the code in charge of the counter application */
	
// filter part

// the filter object
function Filter(){
	this.setValues(new $H());
}

// sets the values in the filter, values is a map associating available keys to their value.
Filter.prototype.setValues = function(values){
	
	// init values
	this.gender=null;
	this.hardware = null;
	this.after = null;
	this.minAge = null;
	this.maxAge = null;
	
	values.each(function(pair){
		if( pair.key == "gender" ){
			filter.gender = pair.value;
		} else if( pair.key == "hardware" ){
			filter.hardware = pair.value;
		} else if( pair.key == "minAge" && parseInt(pair.value,10) != NaN){
			filter.minAge = parseInt(pair.value,10);
		} else if( pair.key == "maxAge" && parseInt(pair.value,10) != NaN ){
			filter.maxAge = parseInt(pair.value,10);
		} else if (pair.key == "timeFrame" && parseInt(pair.value,10) != NaN ){
			filter.after = new Date().getTime() - (parseInt(pair.value, 10) * 3600000);
		}
	});
	
}

//Return true if the given nano object matches the filter, false otherwise.
Filter.prototype.matches = function (nano){
	if( this.gender != null && this.gender != 'both' && nano.gender != this.gender){
		return false;
	}
	if( this.hardware != null && this.hardware != 'violet' && nano.hardware != this.hardware ){
		return false;
	}	
	if( this.minAge != null && nano.age < this.minAge){
		return false;
	}
	if( this.maxAge != null && nano.age > this.maxAge){
		return false;
	}
	if( this.after != null && nano.last_time < this.after ){
		return false;
	}
	return true;
}

//Change the values stored in the filter and applies it.
function udpateFilter(form, value){
	var values = $H();
	$A(value.split("&")).each(function(s, index) {
		var aValue = s.split("=");
		values.set(aValue[0], aValue[1]);
	});
	filter.setValues(values);
	applyFilter();
}

// applys the current filter and updates all the elements on the page (map, counter, lists).
function applyFilter(){
	var matchingObjects = $A();

	// displays or hides markers and fills up the list of matching objects.
	onlineObjects.values().each(function(s, index){
		if( !filter.matches(s) ){
			s.marker.closeInfoWindow();
			s.marker.hide();
		} else {
			s.marker.show();
			matchingObjects.push(s);
		}
	});
	
	// sorting the list : the newest comes first
	matchingObjects.sort(function(a, b){
		return b.last_time - a.last_time;
	});
	
	//$('counter').update(matchingObjects.size());	// changes the counter value
	
	var newTopContent = new Element("div", {'id' : 'highlightedActives'});		// creates new elements to replace the old ones.
	var newOtherContent = new Element("div", {'id' : 'otherActives'});
	matchingObjects.each(function(s, index){
		if( index >= 5 ){
			newOtherContent.insert(s.getHtmlObject(true));
		} else {
			newTopContent.insert(s.getHtmlObject(false));
		}		
	});
	
	$('highlightedActives').replace(newTopContent);
	$('otherActives').replace(newOtherContent);	
}

// end of filter part

// Nano object : contains all useful information about a connected object.
function Nano(nanoInfo) {
	this.serial = nanoInfo["serial"];
	this.location = nanoInfo["location"];

	this.name = nanoInfo["name"];
	this.profile = nanoInfo["profile"];
	this.picture = nanoInfo["picture"];
	this.address = nanoInfo["address"];
		
	this.last_time = nanoInfo["last_time"];
	this.gender = nanoInfo["gender"];
	this.age = nanoInfo["age"];
	this.hardware = nanoInfo["hardware"];
};

// Returns a GMarker located on the nano's position and opening a customized info window
// The marker is set to hidden if it does not match the current filter.
Nano.prototype.onPointCreation = function() {
	this.marker = new GMarker(new GLatLng(this.location.lat, this.location.lng), {icon:nanoIcon});
	GEvent.bind(this.marker, "click", this, this.centerOnNano);

	if(!filter.matches(this)){
		this.marker.hide();
	}
		
	return this.marker;
};

// the 'onClick' function related to a nano object : center the map on the nano and open its info window.
Nano.prototype.centerOnNano = function(){
	map.panTo(this.marker.getLatLng());	
	this.marker.openInfoWindowTabsHtml([new GInfoWindowTab("Info", "<div class='info_bubble'><img src='"+this.picture+"' /> <b>"+this.name+"</b>"+"<span>"+this.address+"</span><a href='"+this.profile+"' target='_blank'>View profile</a></div>")
	//,new GInfoWindowTab("Message", "<div class='message_bubble'><b>Not supported yet!</b></div>")
	]);
}

// returns a html element representing this nano object.
Nano.prototype.getHtmlObject = function(withWrapper) {
	var container = new Element('span', {'class' : 'object'});
	var newContent = "<span class='pic'><img src='"+this.picture+"'/></span>";
	newContent += "<span class='name'>"+this.name+"</span><span class='location'>"+this.address+"</span>";
	newContent += "<a class='link' href='"+this.profile+"' target='_blank'>Voir son profil</a></span>";
	container.insert(newContent);
	
	Event.observe(container, 'click', this.centerOnNano.bindAsEventListener(this));
	
	if( withWrapper ){
		var wrapper = new Element('div', {'class':'grid_4'});
		wrapper.insert(container);
		return wrapper;
	} else {
		return container;
	}
}
// end of nano object


// Updates the markers on the map, the counter and the list of last_seen objects.
function updateElements(){
		
	new Ajax.Request('/OS/ztampApp',
		  {
		    method:'get',
		    parameters:{application:'counter', action:'getObjectsUpdate', all:window.allElements},

				onSuccess: function(transport){
			      	var result = transport.responseText.evalJSON();
					var online = result.online;
					var offline = result.offline;

					// will add the new objects (or update the time value of the already known objects)
			      	for (var i = 0, len = online.length; i < len; i++) {
			      		var newNano = new Nano(online[i]);
			      		var knownNano = onlineObjects.get(newNano.serial)
						if( knownNano == null ){  // this nano is not currently in the map, we create the marker
							markerManager.addMarker(newNano.onPointCreation(),2);
							onlineObjects.set(newNano.serial, newNano);
						} else {
							knownNano.last_time = newNano.last_time;	// just edit the last_time attribute
						}
					}
					window.allElements = false;

					for (var i = 0, len = offline.length; i < len; i++) {
						var nano = onlineObjects.get(offline[i]["serial"]);
						if( nano != null ){   // the nano is currently displayed
							markerManager.removeMarker(nano.marker);
							onlineObjects.unset(nano.serial);
						}
					}

					// at this point, the main Hash and the map are up to date.
					applyFilter();
			    }
		  });	
}

window.allElements = true;  // true if we want to retrieve all the online objects (only for the first query).
window.nanoIcon = true;
window.map = window.markerManager = null;  // Google map components (the map itself and the markers manager).
window.onlineObjects = new Hash();  // Hash of online objects (i.e. displayed on the map), serials are used as keys, nano objects are the values.
window.filter = new Filter(); // the filter

//the satellite map has a default max resolution of 19 but the markermanager can't handle them (markers are not displaied)
G_SATELLITE_MAP.getMaximumResolution = function () { return 17 }; 

// The main function : inits the google map and starts the MarkersUpdater
function initialize(){
	map = new GMap2(document.getElementById("map_canvas"));
	map.removeMapType(G_HYBRID_MAP);
	map.setMapType(G_SATELLITE_MAP );
	map.enableScrollWheelZoom();
	map.setCenter(new GLatLng(48.8566667, 2.3509871), 2);  // the map is centered on Paris, France
	map.addControl(new GMapTypeControl());
	map.addControl(new GLargeMapControl3D());
		
	markerManager = new MarkerManager(map);

	// default icon
	nanoIcon = new GIcon(G_DEFAULT_ICON);
	nanoIcon.image = "images/nano_icon.png";
		
	new PeriodicalExecuter(updateElements, 8);

	// observes for any change on the filter form
	new Form.Observer('filtersForm', 2, udpateFilter);
	
	// the toggle effect on the filters form
	Event.observe('slideButton', 'click', function(event){
		Effect.toggle($('filtersContainer'), 'slide', { duration: 1.0 });
		$('slideButton').toggleClassName('active');
	});
}

Event.observe(window, 'load', initialize);
Event.observe(window, 'unload', GUnload);