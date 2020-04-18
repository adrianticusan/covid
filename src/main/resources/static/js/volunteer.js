var findOrdered = '/volunteer/findNextOrdered';
var helpUserUrl = '/volunteer/addUserToHelpedUsers'
var offsetCounter = 1;

$("#load-more").click(function(e) {
    e.preventDefault();
    $.ajax({
        url: findOrdered,
        method: "GET",
        data: {
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

$(".started-helping").click(function(e) {
    e.preventDefault();
   var userSelected = $(this).siblings('.user-id').val();
   console.log(userSelected);
    $.ajax({
        url: helpUserUrl,
        method: "POST",
        data: {
            userToBeHelpedId: userSelected
        },
        success: function (res) {
          alert("YAS");
        },
        fail: function (err) {
            //TO-DO :ADD ERROR
        }

    });
});