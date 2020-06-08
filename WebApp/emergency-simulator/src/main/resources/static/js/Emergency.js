var map2 = L.map('emergencyMap').setView([40.4167047, -3.7035825], 15);

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 20,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    minZoom : 15,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1IjoiZmFiaWVucHVpc3NhbnQiLCJhIjoiY2s5YTJtdWY4MDAyazNtcXVodjczcGwxcCJ9.K49PEwo4aFG5oQUXaTnubg'
}).addTo(map2);



const imgVehicule = 'images/fireTruck.png';
const imgAlert = 'images/alert.png';
const imgCaserne = "images/caserne.jpg";
const imgGasStation = 'images/GasStation.jpg';
const imgFireHydrant = 'images/FireHydrant.jpg';

var north = map2.getBounds().getNorth(); 

var south = map2.getBounds().getSouth();

var east = map2.getBounds().getEast();

var west = map2.getBounds().getWest();

var width = east - west;

var height =  north - south;

console.log(map2.getBounds());


var imagesEmergency = [];


function displayElementEmergency(x, y, imgSrc) {
	
	var n = 256;
	var minX = (width/n) * (y - 2);
	var maxX = (width/n) * (y + 2);
	var minY = (height/n) * (x - 4);
	var maxY = (height/n) * (x + 4);
	var imageUrl = imgSrc;
	imageBounds = [[north - minY , west + minX], [north - maxY, west + maxX]];
	var image = L.imageOverlay(imageUrl, imageBounds)
	image.addTo(map2);
	
	if(imgSrc != imgCaserne && imgSrc != imgGasStation && imgSrc != imgFireHydrant){
		imagesEmergency.push(image);
	}
	
	
}

function displayAllElements(type){
	

	for (var i = 0; i < imagesEmergency.length; i++){
		map2.removeLayer(imagesEmergency[i]);
	}
	imagesEmergency = [];
	
	
	
	
	var url, imgSrc;
	
	if(type == "Caserne"){
		url = "http://localhost:8082/HeadQuarterWebService/getAllCoords";
		imgSrc = imgCaserne;
	}
	else if (type == "Alerte"){
		url = "http://localhost:8082/EmergencyWebService/getAllCoords";
		imgSrc = imgAlert;
	}
	
	else if (type == "GasStation"){
		url = "http://localhost:8082/GasStationWebService/getAllCoords";
		imgSrc = imgGasStation;
	}
	
	else if (type == "FireHydrant"){
		url = "http://localhost:8082/ElementWebService/getAllCoords";
		imgSrc = imgFireHydrant;
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
					  displayElementEmergency(x, y, imgSrc);
				  }
			  }
		  });
		
}
		
	
displayAllElements("Caserne");
displayAllElements("FireHydrant");
displayAllElements("GasStation");
setInterval(displayAllElements, 1000, "Alerte");	
setInterval(displayAllElements, 1000, "Vehicule");	


$("#HQForm").submit(function(event){
	
	event.preventDefault();
	
	var x = $("#HQX").val();
	var y = $("#HQY").val();
	var nb = $('#HQNb').val();
	
	if(x != null && y != null && nb != null){

		$.ajax({
			  url:"http://localhost:8082/HeadQuarterWebService/add/" + x + "/" + y + "/" + nb,
			  type: "GET"
		  });	
		
		displayAllElements("Caserne");
	}
	
	displayAllElements("Caserne");
});




	



