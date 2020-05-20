
mapboxgl.accessToken = "pk.eyJ1IjoiZmFiaWVucHVpc3NhbnQiLCJhIjoiY2s5YTJsamZ6MDkwYzNkbnJoOGQxMmVzbCJ9.DlARHyPXPoU0cllMdUgs6g";

const long = -3.7035825;
const lat = 40.4167047

var map = new mapboxgl.Map({
	container: 'map', // container id
	style: 'mapbox://styles/mapbox/streets-v11', // stylesheet location
	center: [long, lat], // starting position [lng, lat]
	zoom: 14 // starting zoom
});

var bounds = map.getBounds();


var north = bounds.getNorth();

var south = bounds.getSouth();

var east = bounds.getEast();

var west = bounds.getWest();

var width = east - west;

var height = north - south;

const imgFire = 'images/fire.gif';
const imgVehicule = 'images/fireTruck.png';
const imgAlert = 'images/alert.png';

var images = [];

function convertCoord(x, y) {
	var n = 32;

	var minX = (width / n) * y;
	var maxX = (width / n) * (y + 1);
	var minY = (height / n) * x;
	var maxY = (height / n) * (x + 1);

	imageBounds = [[north - minY, west + minX], [north - maxY, west + maxX]];

	imageBoundsBox = [west + maxX, north - minY];
	return imageBoundsBox;
}

var imgId = 0;

function displayElement(x, y, imgSrc, imgId) {
	console.log(imgId);
	map.loadImage(
		imgSrc,
		function (error, image) {
			if (error) throw error;
			map.addImage(imgId, image);
			map.addSource('point', {
				'type': 'geojson',
				'data': {
					'type': 'FeatureCollection',
					'features': [
						{
							'type': 'Feature',
							'geometry': {
								'type': 'Point',
								'coordinates': convertCoord(x, y)
							}
						}
					]
				}
			});
			map.addLayer({
				'id': 'points',
				'type': 'symbol',
				'source': 'point',
				'layout': {
					'icon-image': imgId,
					'icon-size': 0.1
				}
			});
		}
	);


}



function displayAllElements(type) {

	for (var i = 0; i < images.length; i++) {
		mapSimu.removeLayer(images[i]);
	}

	var url, imgSrc;

	if (type == "Fire") {
		url = "http://localhost:8081/FireWebService/getAllCoords";
		imgSrc = imgFire;
		type = "Fire";
	}
	else if (type == "Alert") {
		url = "http://localhost:8082/EmergencyWebService/getAllCoords";
		imgSrc = imgAlert;
		type = "Alert";
	}

	else {
		url = "http://localhost:8082/VehiculeWebService/getAllCoords"
		imgSrc = imgVehicule;
		type = "Vehicule";
	}


	$.ajax({
		url: url,
		type: "GET",
		success: function (data) {
			json = JSON.parse(data);
			var x, y;
			for (var i = 0; i < json.length; i++) {
				x = json[i]["x"];
				y = json[i]["y"];
				var imageId = getImageId(type)

				displayElement(x, y, imgSrc, imageId);
			}
		}
	});

}

var FireId = 0;
var VehiculeId = 0;
var AlertId = 0;

function getImageId(type) {

	if (type == "Fire") {
		FireId++;
		return "Fire" + FireId.toString();
	}
	else if (type == "Alert") {
		AlertId++;
		return "Alert" + AlertId.toString();
	}
	else {
		VehiculeId++;
		return "Vehicule" + VehiculeId.toString();
	}
}





//setInterval(displayAllFires, 2000);
//setInterval(displayAllProbes, 2000);

displayAllElements("Alert");
displayAllElements("Vehicule");
displayAllElements("Fire");
