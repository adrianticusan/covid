var findOrdered = '/volunteer/findNextOrdered';
var helpUserUrl = '/volunteer/addUserToHelpedUsers'
var offsetCounter = 1;
var startedHelpingText = $("#started-helping").text();
var errorText = $("#error").text();

$("#load-more").click(function (e) {

    e.preventDefault();
    $.ajax({
        url: findOrdered,
        method: "GET",
        data: {
            offset: 5 * offsetCounter
        },
        success: function (res) {
            offsetCounter++;
            var usersTable = $(res).find("#users-table");
            if (usersTable.length == 0) {
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
    $.ajax({
        url: helpUserUrl,
        method: "POST",
        data: {
            userToBeHelpedId: $(this).siblings('.user-id').val()
        },
        success: function () {
            clickedElement.find(".btn-helping").text(startedHelpingText);
        },
        fail: function (err) {

        }

    });
});
