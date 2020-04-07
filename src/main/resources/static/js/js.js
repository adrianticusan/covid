$(document).ready(function () {
    const menu = $(".mobile-menu")[0];
    const heightOfNav = $(".header-area")[0];

    const displayMenu = () => {
        if (menu.style.display === "none" || menu.style.display === "") {
            menu.style.display = "flex";
            heightOfNav.style.height = "40rem";
        } else {
            menu.style.display = "none";
            heightOfNav.style.height = "7rem";
        }
    };

    $(".bars")[0].addEventListener("touchend", displayMenu);

    $(".j-button-login").click(manageLogin);

    const displayModal = () => {
        $(".j-overlay-modal").css({"display": "block"});
        $("body").css({"overflow": "hidden", "padding-right": "17px"});
        setTimeout(() => {
            //
            // login modal comes in middle
            $(".holder-login").addClass("j-login-top");
        }, 100);
    };

    const cancelModal = () => {
        // when overlay is clicked
        // make login modal to go over top
        $(".holder-login").addClass("j-login-reverse-top");
        // make modal email to go over down
        $(".holder-forgot-pass").addClass("j-forgot-reverse-bottom ");
        // make email and login modal disappear, clear value and put css default
        $(".j-email-mesg").css({"visibility": "hidden"});
        $(".j-login-mesg").css({"visibility": "hidden"});
        $(".holder-forgot-pass input").css({"border-color": "#35ac39"});
        $(".holder-forgot-pass input").val("");
        $("#login-user").css({"border-color": "#35ac39"});
        $("#login-pass").css({"border-color": "#35ac39"});
        $(".holder-login input").val("");


        setTimeout(() => {
            //
            // reset all, default email modal, login modal, overlay and body
            $(".j-overlay-modal").css({"display": "none"})
            $("body").css({"overflow": "auto", "padding-right": "0px"});
            $(".holder-login").removeClass("j-login-reverse-top  j-login-top");
            $(".holder-forgot-pass").removeClass("j-forgot-bottom j-forgot-reverse-bottom ");
        }, 600)
    };

    $(".j-modal-desktop-btn")[0].addEventListener("click", displayModal, false);
    $(".j-modal-mobile-btn")[0].addEventListener("click", displayModal, false);
    $(".j-overlay-modal")[0].addEventListener("click", cancelModal, false);

    $(".j-forgot")[0].addEventListener("click", function () {
        //
        // after click on forgot pass make login modal to go overtop
        $(".holder-login").addClass("j-login-reverse-top ");
        // show the email modal
        $(".holder-forgot-pass").addClass("j-forgot-bottom");
    }, false);

});

function manageLogin(event) {
    event.preventDefault();
    var loginForm = $(".j-login-form");
    $.ajax({type: "POST", url: loginForm.attr("action"), data: loginForm.serialize()}).error(function (error) {}).success(function () {
        window.location.reload();
    });
}

$(".j-button-login")[0].addEventListener('click', function () {
    //
    // put your login validation
    const username = "user"
    const pass = "123";
    const inputUsername = $("#login-user").val();
    const inputPass = $("#login-pass").val();
    $(".j-login-mesg ").css({"visibility": "visible"});
    if (username === inputUsername && pass === inputPass) {
        $("#login-user").css({"border-color": "#35ac39"})
        $("#login-pass").css({"border-color": "#35ac39"})
        $(".j-login-mesg ").css({"visibility": "hidden"});
    } else {
        $("#login-user").css({"border-color": "red"})
        $("#login-pass").css({"border-color": "red"})
    }

}, false);

$(".j-email-btn")[0].addEventListener('click', function () {
    //
    // put your email validation
    const email = "an email";
    const inputValue = $(".holder-forgot-pass input").val();
    $(".j-email-mesg ").css({"visibility": "visible"});
    if (email === inputValue) {
        $(".j-email-mesg ").css({"color": "#35ac39"});
        $(".holder-forgot-pass input").css({"border-color": "#35ac39"})
        $(".j-email-mesg ")[0].textContent = "An email has been sent";
    } else {
        $(".j-email-mesg ").css({"color": "red"})
        $(".holder-forgot-pass input").css({"border-color": "red"})
        $(".j-email-mesg ")[0].textContent = "The email that you entered does not exists";
    }
}, false)
