var findOrdered = '/users/findOrdered'
$.ajax({
    url: findOrdered,
    method: "GET",
    data:{

    },
    success: function (res) {
        $("#users-table").html(res);
    },
    fail: function (err) {
        console.log(err);
    }

});