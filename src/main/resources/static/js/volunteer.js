var findNextOrdered = '/volunteer/findNextOrdered/';
var helpUserUrl = '/volunteer/help-user'
var findOrdered = '/volunteer/';
var stopHelpingUserUrl = '/volunteer/stop-helping-user'
var offsetCounter = 1;
var startedHelpingText = $("#started-helping").text();
var stoppedHelpingText = $("#stopped-helping").text();
var errorText = $("#error").text();

$("#load-more").click(function (e) {
    e.preventDefault();
    $.ajax({
        url: getUrl(),
        method: "GET",
        data: {
            offset: 5 * offsetCounter
        },
        success: function (res) {
            offsetCounter++;
            var usersTable = $(res).find("#users-table");
            if ($(res).find(".container-people").length < 5) {
                $("#load-more").hide();
            }
            $("#users-table").append($(usersTable).html());
        },
        fail: function (err) {
            //TO-DO :ADD ERROR
        }

    });
});

$("#users-table").on('click', '.started-helping', function (e) {
    e.preventDefault();
    var clickedElement = $(this);

    var token = $("#_csrf").attr("content");
    var header = $("#_csrf_header").attr("content");

    $.ajax({
        url: helpUserUrl,
        method: "POST",
        data: {
            userToBeHelpedId: $(this).siblings('.user-id').attr("content")
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function () {
            clickedElement.find(".btn-helping").text(startedHelpingText);
        },
        fail: function (err) {

        }

    });
});

$("#users-table").on('click', '.stop-helping', function (e) {
    e.preventDefault();
    var clickedElement = $(this);

    var token = $("#_csrf").attr("content");
    var header = $("#_csrf_header").attr("content");

    $.ajax({
        url: stopHelpingUserUrl,
        method: "DELETE",
        data: {
            userToBeRemovedId: $(this).siblings('.user-id').attr("content")
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function () {
            clickedElement.find(".btn-helping-stop").text(stoppedHelpingText);
        },
        fail: function (err) {

        }

    });
});

function getUrl() {
    return $("#need-help").attr("content");
}
