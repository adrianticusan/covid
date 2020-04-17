var findOrdered = '/volunteer/findNextOrdered';
var offsetCounter = 1;

$("#load-more").click(function(e) {
    e.preventDefault();
    $.ajax({
        url: findOrdered,
        method: "GET",
        data: {
            longitude: getUrlVars()["longitude"],
            latitude: getUrlVars()["latitude"],
            userRangeInMeters: getUrlVars()["userRangeInMeters"],
            offset: 5 * offsetCounter
        },
        success: function (res) {
            offsetCounter ++;
            var usersTable =$(res).find("#users-table");
            $("#users-table").append($(usersTable).html());
        },
        fail: function (err) {
           //TO-DO :ADD ERROR
        }

    });
});


function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}