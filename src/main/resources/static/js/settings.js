"use strict";

var slider = $("#myRange")[0];
var output = $("#value-slider")[0];
const googleFormsMapping = {
    street_number: {
        type: 'short_name',
        name: 'streetNumber'
    },
    route: {
        type: 'long_name',
        name: 'streetAddress'
    },
    locality: {
        type: 'long_name',
        name: 'locality'
    },
    administrative_area_level_1: {
        type: 'short_name',
        name: 'state',
    },
    country: {
        type: 'long_name',
        name: 'country'
    },
    postal_code: {
        type: 'short_name',
        name: 'zipCode'
    }
};

// Display the default slider value
// Update the current slider value (each time you drag the slider handle)

$(document).ready(function () {
    $("#myRange").change(changeDistance);
});

function changeDistance() {
    var distance = $(this).val();
    var token = $('#_csrf').attr('content');
    var header = $('#_csrf_header').attr('content');
    $.post({
        url: $("#change-distance-url").attr('content'),
        data: {distance: distance},
        beforeSend: function beforeSend(xhr) {
            xhr.setRequestHeader(header, token);
        }
    }).fail(function (error) {
    }).done(function () {
        window.location.reload();
    });
}

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

    var currentUserPosition = new google.maps.LatLng($("#user-current-latitude").attr("content"), $("#user-current-longitude").attr("content"));
    addMarkerWithInfo(currentUserPosition, map, "You");
    addUsersOnMap(map);

    var autocomplete = new google.maps.places.Autocomplete(
        $("#location-input")[0], {types: ['address']});
    // Avoid paying for data that you don't need by restricting the set of
    // place fields that are returned to just the address components.
    autocomplete.setFields(['address_component']);

    // When the user selects an address from the drop-down, populate the
    // address fields in the form.
    autocomplete.addListener('place_changed', listener => {
        getCoordinatesAndFillAddress($("#location-input"), autocomplete, map);
    });

    map.addListener('bounds_changed', function () {
        autocomplete.setBounds(map.getBounds());
    });

}

function addUsersOnMap(map) {
    $.get({
        url: $("#find-users-map").attr('content'),
        data: {
            latitude: $("#user-current-latitude").attr("content"),
            longitude: $("#user-current-longitude").attr("content")
        }
    }).done(function (result) {
        var displayHtml = $("#display-on-map").html();
        result.forEach(function (user, index) {
            var position = new google.maps.LatLng(user.locationDto.latitude, user.locationDto.longitude);
            var html = displayHtml.replace("{firstName}", user.firstName);
            html = html.replace("{lastName}", user.lastName);
            html = html.replace("{phoneNumber}", user.phoneNumber);
            addMarkerWithInfo(position, map, html);
        })
    });
}

function getCoordinatesAndFillAddress(addressElement, autoComplete, map) {
    console.log(addressElement.val())
    var geocoder = new google.maps.Geocoder();
    return geocoder.geocode({'address': addressElement.val()}, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            var latitude = results[0].geometry.location.lat();
            var longitude = results[0].geometry.location.lng();
            map.setCenter({lat: latitude, lng: longitude});
            fillAddress(addressElement, autoComplete, {
                latitude: latitude,
                longitude: longitude
            });
        }
    });
}

function fillAddress(autoCompleteAddressElement, autoComplete, coordinates) {
    var place = autoComplete.getPlace();
    var form = autoCompleteAddressElement.closest("form");
    Object.keys(googleFormsMapping).forEach((value, index) => {
        var addressElement = $(form.find("input[name='" + googleFormsMapping[value].name + "']"));
        addressElement.val('');
    });

    // Get each component of the address from the place details,
    // and then fill-in the corresponding field on the form.
    for (var i = 0; i < place.address_components.length; i++) {
        var addressType = place.address_components[i].types[0];
        if (googleFormsMapping[addressType]) {
            var val = place.address_components[i][googleFormsMapping[addressType].type];
            $(form.find("input[name='" + googleFormsMapping[addressType].name + "']")).val(val);
        }
    }
    Object.keys(coordinates).forEach((value, index) => {
        $(form.find("input[name='" + value + "']")).val(coordinates[value]);
    })
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

