$(document).ready(() => {
    const menu = $(".mobile-menu")[0];
    const heightOfNav = $(".header-area")[0];

    const displayMenu = (e) => {
        e.preventDefault();
        if (menu.style.display === "none" || menu.style.display === "") {
            menu.style.display = "flex";
            heightOfNav.style.height = "40rem";
        } else {
            menu.style.display = "none";
            heightOfNav.style.height = "7rem";
        }
    };

    $(".bars").click(displayMenu);


    const displayModal = (e) => {
        e.preventDefault();
        if (window.innerWidth > 1024) {
            $("body").css({"padding-right": "17px"});
        }
        $("body").css({"overflow": "hidden"}),
        $(".overlay-modal").css({"display": "block"});
        setTimeout(() => {
            // ////////////////////////////
            // login modal comes in middle
            $(".holder-login").addClass("login-top");
        }, 100);
    };


    const cancelModal = (e) => {
        e.preventDefault();
        // when overlay is clicked
        // make login modal to go over top
        $(".holder-login").addClass("login-reverse-top");
        // make modal email to go over down
        $(".holder-forgot-pass").addClass("forgot-reverse-bottom");
        // make email and login modal disappear, clear value and put css default
        const resetCss = (messagesClases, inputs) => {
            $.each(messagesClases, (valueOfElement) => {
                $(valueOfElement).css({"visibility": "hidden"});
            });
            $.each(inputs, (valueOfElement) => {
                $(valueOfElement).css({"border-color": "#35ac39"});
                $(valueOfElement).val("");
            });
        }
        // ///
        resetCss([
            ".email-mesg", ".login-mesg"
        ], [".holder-forgot-pass input", ".holder-login input"]);

        setTimeout(() => {
            // ///////////////////////////////////////////////////////////////
            /* reset overlay and body, remove classes from modal's so can be added
            again when click event occurs j-modal-desktop-btn or .j-modal-mobile-btn */
            $(".overlay-modal").css({"display": "none"})
            $("body").css({"overflow": "auto"});
            if (window.innerWidth > 1024) {
                $("body").css({"padding-right": "0px"});
            }
            $(".holder-login").removeClass("login-reverse-top  login-top");
            $(".holder-forgot-pass").removeClass("forgot-bottom forgot-reverse-bottom ");
        }, 600)
    };


    $(".j-modal-desktop-btn").click(displayModal);
    $(".j-modal-mobile-btn").click(displayModal);
    $(".overlay-modal").click(cancelModal);


    $(".j-forgot").click((e) => {
        e.preventDefault();
        // after click on forgot pass make login modal to go overtop
        $(".holder-login").addClass("login-reverse-top ");
        // show the email modal
        $(".holder-forgot-pass").addClass("forgot-bottom");
    });

    $(".j-button-login").click(manageLogin);

});

function manageLogin(event) {
    event.preventDefault();
    var loginForm = $(".j-login-form");
    $.ajax({type: "POST", url: loginForm.attr("action"), data: loginForm.serialize()}).error(function (error) {}).success(function () {
        window.location.reload();
    });
}

$(".j-button-login").click((e) => {
    e.preventDefault();
    // put your login validation
    const username = "user"
    const pass = "123";
    const inputUsername = $("#login-user").val();
    const inputPass = $("#login-pass").val();
    $(".login-mesg ").css({"visibility": "visible"});
    if (username === inputUsername && pass === inputPass) {
        $(".holder-login input").css({"border-color": "#35ac39"})
        $(".login-mesg ").css({"visibility": "hidden"});
    } else {
        $(".holder-login input").css({"border-color": "red"})
    }

});

$(".j-email-btn").click((e) => {
    e.preventDefault();
    // put your email validation
    const email = "an email";
    const inputValue = $(".holder-forgot-pass input").val();
    $(".email-mesg ").css({"visibility": "visible"});
    if (email === inputValue) {
        $(".email-mesg ").css({"color": "#35ac39"});
        $(".holder-forgot-pass input").css({"border-color": "#35ac39"})
        $(".email-mesg ")[0].textContent = "An email has been sent";
    } else {
        $(".email-mesg ").css({"color": "red"})
        $(".holder-forgot-pass input").css({"border-color": "red"})
        $(".email-mesg ")[0].textContent = "The email that you entered does not exists";
    }
});
