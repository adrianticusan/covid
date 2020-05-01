"use strict";

var slider = $("#myRange")[0];
var output = $("#value-slider")[0];
output.innerHTML = slider.value + " miles";
// Display the default slider value
// Update the current slider value (each time you drag the slider handle)

slider.oninput = function () {
    output.innerHTML = "".concat(this.value, " miles");
};


function initAutocomplete() {
    var map = new google.maps.Map(document.getElementById('map'), {
        center: { // add current location
            lat: $("#user-current-latitude").attr("content") * 1,
            lng: $("#user-current-longitude").attr("content") * 1
        },
        zoom: 15,
        mapTypeId: 'roadmap'
    }); // Create the search box and link it to the UI element.
    setPinImage();
    var input = $("#location-input")[0];


    var currentUserPosition = new google.maps.LatLng($("#user-current-latitude").attr("content"), $("#user-current-longitude").attr("content"));
    addMarkerWithInfo(currentUserPosition, map, "You");
    var searchBox = new google.maps.places.SearchBox(input); // Bias the SearchBox results towards current map's viewport.

    map.addListener('bounds_changed', function () {
        searchBox.setBounds(map.getBounds());
    });


    searchBox.addListener('places_changed', function () {
        var bounds = new google.maps.LatLngBounds();
        map.fitBounds(bounds);
    });
}

var pinImage;

function setPinImage() {
    pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|FF0000",
        new google.maps.Size(21, 34),
        new google.maps.Point(0, 0),
        new google.maps.Point(10, 34));
}

function addMarkerWithInfo(postion, map, content) {
    var marker = new google.maps.Marker({
        position: postion,
        title: content,
        icon: pinImage
    });
    marker.setMap(map);
    var infoUser = new google.maps.InfoWindow({
        content: content
    });

    marker.addListener('click', function () {
        infoUser.open(map, marker);
    });

}
