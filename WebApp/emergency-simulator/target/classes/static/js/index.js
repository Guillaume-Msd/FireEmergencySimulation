
var map = L.map('mapid').setView([40.4167047, -3.7035825], 14);

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 20,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1IjoiZmFiaWVucHVpc3NhbnQiLCJhIjoiY2s5YTJtdWY4MDAyazNtcXVodjczcGwxcCJ9.K49PEwo4aFG5oQUXaTnubg'
}).addTo(map);

const imgFire = 'images/fire.gif';
const imgVehicule = 'images/fireTruck.png';
const imgAlert = 'images/alert.png';


var north = map.getBounds().getNorth(); 

var south = map.getBounds().getSouth();

var east = map.getBounds().getEast();

var west = map.getBounds().getWest();

var width = east - west;

var height =  north - south;

var images = [];


function displayElement(x, y, imgSrc){
	
	var n = 16;
	
	var minX = (width/n) * y;
	var maxX = (width/n) * (y+1);
	var minY = (height/n) * x;
	var maxY = (height/n) * (x+1);
	var imageUrl = imgSrc,
	imageBounds = [[north - minY, west + minX], [north - maxY, west + maxX]];
	var image = L.imageOverlay(imageUrl, imageBounds)
	image.addTo(map);
	images.push(image)
	
}


function displayAllElements(type){
	
	for (var i = 0; i < images.length; i++){
		map.removeLayer(images[i]);
	}
	
	var url, imgSrc;
	
	if(type == "Fire"){
		url = "http://localhost:8081/FireWebService/getAllCoords";
		imgSrc = imgFire;
	}
	else if (type == "Alert"){
		url = "http://localhost:8082/EmergencyWebService/getAllCoords";
		imgSrc = imgAlert;
	}
	
	else{
		url = "http://localhost:8082/VehiculeWebService/getAllCoords"
		imgSrc = imgVehicule;
	}
	
	$.ajax({
		
		  url:url,
		  type: "GET",
		  success: function( data ){
			  json = JSON.parse(data);
			  var x, y;
			  for(var i = 0 ; i < json.length; i++){
				  x = json[i]["x"];
				  y = json[i]["y"];
				  displayElement(x, y, imgSrc);
			  }
		  }
	  });
	
}


$("#addFireButton").on("click", function(){
	$.ajax({
		  url:"http://localhost:8081/FireWebService/add",
		  type: "GET"
	  });
	
	
});


$("#clearFireButton").on("click", function(){
		$.ajax({
		  url:"http://localhost:8081/FireWebService/removeAll",
		  type: "GET"
	  });
	for (var i = 0; i < images.length; i++){
		map.removeLayer(images[i]);
	}
	
})


//setInterval(displayAllFires, 2000);
//setInterval(displayAllProbes, 2000);

displayAllElements("Fire");
displayAllElements("Alert");
displayAllElements("Vehicule");



		
	
	










