
var map = L.map('simuMap').setView([40.4167047, -3.7035825], 15);

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 20,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    minZoom: 15,
    accessToken: 'pk.eyJ1IjoiZmFiaWVucHVpc3NhbnQiLCJhIjoiY2s5YTJtdWY4MDAyazNtcXVodjczcGwxcCJ9.K49PEwo4aFG5oQUXaTnubg'
}).addTo(map);



const imgFire = 'images/fire.gif';
const imgSmoke = 'images/Smoke.png';
const imgCO2 = 'images/CO2.png';
const imgThermic = 'images/Thermic.png';


var north = map.getBounds().getNorth(); 

var south = map.getBounds().getSouth();

var east = map.getBounds().getEast();

var west = map.getBounds().getWest();

var width = east - west;

var height =  north - south;

console.log(map.getBounds());



var imagesFire = [];



function displayElementSimu(x, y, imgSrc){
	
	var n = 256;
	var minX = (width/n) * (y - 2);
	var maxX = (width/n) * (y + 2);
	var minY = (height/n) * (x - 4);
	var maxY = (height/n) * (x + 4 );
	var imageUrl = imgSrc;
	imageBounds = [[north - minY, west + minX], [north - maxY, west + maxX]];
	var image = L.imageOverlay(imageUrl, imageBounds)
	image.addTo(map);
	
	if(imgSrc == imgFire){
		imagesFire.push(image);
	}
	
	
}

function displayAllElements(type){
	
	for (var i = 0; i < imagesFire.length; i++){
		map.removeLayer(imagesFire[i]);
	}
	
	imagesSimu = [];
	
	
	var url, imgSrc;
	
	if(type == "Fire"){
		url = "http://localhost:8081/FireWebService/getAllCoords";
		imgSrc = imgFire;
	}
	else  {
		url = "http://localhost:8081/ProbeWebService/getAll";
	
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
	
	else {
		
		$.ajax({
			
			  url:url,
			  type: "GET",
			  success: function( data ){
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
					  x = json[i]["x"];
					  y = json[i]["y"];
					  displayElementSimu(x, y, imgSrc);
				  }
			  }
		  });
		
	}
	
	
}


$("#FireForm").submit(function(event){
	
	event.preventDefault();
	
	var x = $("#FireX").val();
	var y = $("#FireY").val();
	var type = $('#FireType').val();
	var intensity = $("#FireIntensity").val();
	
	var json = {
			"intensity": intensity ,
			"type":  type
	};
	
	if(x != null && y != null && type != null && intensity != null){

		$.ajax({
			  url:"http://localhost:8081/FireWebService/add/" + x + "/" + y,
			  type: "POST",
			  data : JSON.stringify(json),
			  contentType:"application/json; charset=utf-8"
		  });	
		
	}
});


$("#ProbeForm").submit(function(event){
	
	event.preventDefault();
	
	var x = $("#ProbeX").val();
	var y = $("#ProbeY").val();
	var type = $('#ProbeType').val();
	var range = $("#ProbeRange").val();
	
	if(x != null && y != null && type != null && range != null){

		$.ajax({
			  url:"http://localhost:8081/ProbeWebService/add/" + type + "/" + range + "/" + x + "/" + y,
			  type: "GET"
		  });	
	
		displayAllElements("Probe");
	}
	
	displayAllElements("Probe");
});


$("#clearFireButton").on("click", function(){
		$.ajax({
		  url:"http://localhost:8081/FireWebService/removeAll",
		  type: "GET"
	  });
	for (var i = 0; i < imagesSimu.length; i++){
		map.removeLayer(imagesSimu[i]);
	}
	
})

displayAllElements("Probe");
setInterval(displayAllElements, 2000, "Fire");

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
					var image = L.imageOverlay(imgVehicule, imageBounds);
					image.addTo(map);
					images.push(image);
			  }
		  }
	});
	
}

const imgVehicule = 'images/fireTruck.png';
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
				  displayElementSimu(x, y, imgVehicule);
			  }
		  }
	});
	
}

//itinerary(30, 150, 60, 80);

		
	
	










