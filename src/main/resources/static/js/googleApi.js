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
var autoCompletes = [];


function initAutocomplete() {
    // Create the autocomplete object, restricting the search predictions to
    // geographical location types.
    autoCompleteAddresses('userAddress');
    autoCompleteAddresses('volunteerAddress');

}
$(document).ready(function () {
$("#userAddress, #volunteerAddress").blur(clearAddress);
});
function clearAddress() {
    var form = $(this).closest("form");
    Object.keys(googleFormsMapping).forEach((value, index) => {
        var addressElement = $(form.find("input[name='" + googleFormsMapping[value].name + "']"));
        addressElement.val('');
    });
}
function autoCompleteAddresses(addressElementId) {
    var autocomplete = new google.maps.places.Autocomplete(
        document.getElementById(addressElementId), {types: ['address']});
    autoCompletes.push(autocomplete);

    // Avoid paying for data that you don't need by restricting the set of
    // place fields that are returned to just the address components.
    autocomplete.setFields(['address_component']);

    // When the user selects an address from the drop-down, populate the
    // address fields in the form.
    autocomplete.addListener('place_changed', listener => {
        var addressEleement = $("#" + addressElementId);
        getCoordinatesAndFillAddress(addressEleement, autocomplete);
    })
}

function getCoordinatesAndFillAddress(addressElement, autoComplete) {
    var geocoder = new google.maps.Geocoder();
    return geocoder.geocode({'address': addressElement.val()}, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            var latitude = results[0].geometry.location.lat();
            var longitude = results[0].geometry.location.lng();
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
        $(form.find("input[name='" + value+ "']")).val(coordinates[value]);
    })
}
function onloadCallbackCaptcha() {
    grecaptcha.execute('6LeeiOoUAAAAAGKVCsmtdES0wF5kcHjkzxTRrRhR', { action: 'homepage' }).then(function (token) {
        $(".captchaResponse").val(token);
    });

}


// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var geolocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            var circle = new google.maps.Circle(
                {center: geolocation, radius: position.coords.accuracy});

            autoCompletes.forEach((autoComplete, index) => {
                autoComplete.setBounds(circle.getBounds());
            });
        });
    }
}