
var map = L.map('simuMap').setView([40.4167047, -3.7035825], 14);

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 20,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1IjoiZmFiaWVucHVpc3NhbnQiLCJhIjoiY2s5YTJtdWY4MDAyazNtcXVodjczcGwxcCJ9.K49PEwo4aFG5oQUXaTnubg'
}).addTo(map);

var map2 = L.map('emergencyMap').setView([40.4167047, -3.7035825], 14);

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 20,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1IjoiZmFiaWVucHVpc3NhbnQiLCJhIjoiY2s5YTJtdWY4MDAyazNtcXVodjczcGwxcCJ9.K49PEwo4aFG5oQUXaTnubg'
}).addTo(map2);

const imgFire = 'images/fire.gif';
const imgVehicule = 'images/fireTruck.png';
const imgAlert = 'images/alert.png';
const imgSmoke = 'images/Smoke.png';
const imgCO2 = 'images/CO2.png';
const imgThermic = 'images/Thermic.png';
const imgCarserne = "images/caserne.jpg"

var north = map.getBounds().getNorth(); 

var south = map.getBounds().getSouth();

var east = map.getBounds().getEast();

var west = map.getBounds().getWest();

var width = east - west;

var height =  north - south;

console.log(map.getBounds());

var imagesSimu = [];
var imagesEmergency = [];



function displayElementSimu(x, y, imgSrc){
	
	var n = 256;
	var minX = (width/n) * (y - 4);
	var maxX = (width/n) * (y + 4);
	var minY = (height/n) * (x - 4);
	var maxY = (height/n) * (x + 4 );
	var imageUrl = imgSrc;
	imageBounds = [[north - minY, west + minX], [north - maxY, west + maxX]];
	var image = L.imageOverlay(imageUrl, imageBounds)
	image.addTo(map);
	imagesSimu.push(image)
	
}

function displayElementEmergency(x, y, imgSrc) {
	
	var n = 256;
	var minX = (width/n) * (y - 4);
	var maxX = (width/n) * (y + 4);
	var minY = (height/n) * (x - 4);
	var maxY = (height/n) * (x + 4);
	var imageUrl = imgSrc;
	imageBounds = [[north - minY , west + minX], [north - maxY, west + maxX]];
	var image = L.imageOverlay(imageUrl, imageBounds)
	image.addTo(map2);
	imagesEmergency.push(image)
	
}


function displayAllElements(type){
	
	for (var i = 0; i < imagesSimu.length; i++){
		map.removeLayer(imagesSimu[i]);
	}
	
	imagesSimu = [];
	

	for (var i = 0; i < imagesEmergency.length; i++){
		map2.removeLayer(imagesEmergency[i]);
	}
	imagesEmergency = [];
	
	displayElementEmergency(30, 150, imgCarserne);
	
	
	var url, imgSrc;
	
	if(type == "Fire"){
		url = "http://localhost:8081/FireWebService/getAllCoords";
		imgSrc = imgFire;
	}
	else if (type == "Alert"){
		url = "http://localhost:8082/EmergencyWebService/getAllCoords";
		imgSrc = imgAlert;
	}
	
	else if (type == "Probe") {
		url = "http://localhost:8081/ProbeWebService/getAllCoords";
	
	}
	
	else{
		url = "http://localhost:8082/VehiculeWebService/getAllCoords"
		imgSrc = imgVehicule;
	}
	
	if(type == "Fire"){
		
		$.ajax({
			
			  url:url,
			  type: "GET",
			  success: function( data ){
				  json = JSON.parse(data);
				  var x, y;
				  for(var i = 0 ; i < json.length; i++){
					  x = json[i]["x"];
					  y = json[i]["y"];
					  displayElementSimu(x, y, imgSrc);
				  }
			  }
		  });
		
	}
	
	else if (type == "Probe" ){
		
		$.ajax({
			
			  url:url,
			  type: "GET",
			  success: function( data ){
				  console.log(data);
				  json = JSON.parse(data);
				  var x, y;
				  for(var i = 0 ; i < json.length; i++){
					  if(json[i]["type"] == "Smoke"){
						  imgSrc = imgSmoke;
					  }
					  else if (json[i]["type"] == "Thermic"){
						  imgSrc = imgThermic;
					  } else {
						  imgSrc = imgCO2;
					  }
					  console.log(json[i]["x"]);
					  x = json[i]["x"];
					  y = json[i]["y"];
					  displayElementSimu(x, y, imgSrc);
				  }
			  }
		  });
		
	}
	
	else {
		
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
	
	
	
}


$("#addFireButton").on("click", function(){
	$.ajax({
		  url:"http://localhost:8081/FireWebService/addRandom",
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

//displayAllElements("Probe");

setInterval(displayAllElements, 2000, "Alert");
setInterval(displayAllElements, 2000, "Probe");
setInterval(displayAllElements, 2000, "Fire");
setInterval(displayAllElements, 2000, "Vehicule");






//Test Intineraire

function Realitinerary(xInit, yInit, xFinal, yFinal){
	
	$.ajax({
		
		  url: "http://localhost:8083/MapWebService/getRealItinerary/" + xInit+ "/" + yInit + "/" + xFinal + "/" + yFinal,
		  type: "GET",
		  success: function( data ){
			  json = JSON.parse(data);
			  var x, y;
			  for(var i = 0 ; i < json.length; i++){
				  x = json[i]["x"];
				  y = json[i]["y"];
				  imageBounds = [[x, y], [x + width/128, y + height/128]];
				  console.log(imageBounds);
					var image = L.imageOverlay(imgVehicule, imageBounds);
					image.addTo(map);
					images.push(image);
			  }
		  }
	});
	
}

function itinerary(xInit, yInit, xFinal, yFinal){
	
	$.ajax({
		
		  url: "http://localhost:8083/MapWebService/getItinerary/" + xInit+ "/" + yInit + "/" + xFinal + "/" + yFinal,
		  type: "GET",
		  success: function( data ){
			  json = JSON.parse(data);
			  var x, y;
			  for(var i = 0 ; i < json.length; i++){
				  x = json[i]["x"];
				  y = json[i]["y"];
				  displayElementEmergency(x, y, imgVehicule);
			  }
		  }
	});
	
}


function Realitinerary(xInit, yInit, xFinal, yFinal){
	$.ajax({
		
		  url: "http://localhost:8083/MapWebService/getRealItinerary/" + xInit+ "/" + yInit + "/" + xFinal + "/" + yFinal,
		  type: "GET",
		  success: function( data ){
			  json = JSON.parse(data);
			  var x, y;
			  for(var i = 0 ; i < json.length; i++){
				  x = json[i]["x"];
				  y = json[i]["y"];
				  var marker = L.marker([x, y])
				  marker.addTo(map);
			  }
		  }
	});
	
}

//itinerary(28, 30, 53, 21);

		
	
	










