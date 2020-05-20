
var map = L.map('mapid').setView([40.4167047, -3.7035825], 14);

L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 20,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1IjoiZmFiaWVucHVpc3NhbnQiLCJhIjoiY2s5YTJtdWY4MDAyazNtcXVodjczcGwxcCJ9.K49PEwo4aFG5oQUXaTnubg'
}).addTo(map);


var north = map.getBounds().getNorth(); 

var south = map.getBounds().getSouth();

var east = map.getBounds().getEast();

var west = map.getBounds().getWest();

var width = east - west;

var height =  north - south;

var images = [];


function displayFire(x, y){
	
	var n = 16;
	
	var minX = (width/n) * y;
	var maxX = (width/n) * (y+1);
	var minY = (height/n) * x;
	var maxY = (height/n) * (x+1);
	var imageUrl = 'images/fire.gif',
	imageBounds = [[north - minY, west + minX], [north - maxY, west + maxX]];
	var image = L.imageOverlay(imageUrl, imageBounds)
	image.addTo(map);
	images.push(image)
	
}


function displayAllFires(){
	
	for (var i = 0; i < images.length; i++){
		map.removeLayer(images[i]);
	}
	
	$.ajax({
		
		  url:"http://localhost:8081/FireWebService/getAll",
		  type: "GET",
		  success: function( data ){
			  json = JSON.parse(data);
			  var x, y;
			  /*for(var i = 0; i < json.length; i++){
				  for(var j = 0; j < json.length){
					  if (abs(json[i]["x"] - json[j]["x"]) == 1 ||
							  abs(json[i]["y"] - json[j]["y"])) {
						  
					  }
				  }
			  }*/
			  for(var i = 0 ; i < json.length; i++){
				  x = json[i]["x"];
				  y = json[i]["y"];
				  displayFire(x, y);
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


setInterval(displayAllFires, 2000);



		
	
	










