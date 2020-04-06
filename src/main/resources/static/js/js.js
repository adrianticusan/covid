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
        if (window.innerWidth > 1024) { // reset default browser
            $("body")[0].style.paddingRight = "17px";
        }
        $(".j-overlay-modal")[0].style.display = "block";
        $("body")[0].style.overflow = "hidden";
        setTimeout(() => {
            $(".holder-login").addClass("j-login-top");
        }, 100);
        // reset default browser
        $("body")[0].style.overflow = "auto";

    };
    const cancelModal = () => {
        $(".holder-login").addClass("j-login-reverse-top");
        $(".holder-forgot-pass").addClass("j-forgot-reverse-bottom ");
        // cancel effects of modal email messages
        $(".j-email-mesj").css({"visibility": "hidden"});
        $(".holder-forgot-pass input").css({"border-color": "#35ac39"});
        $(".holder-forgot-pass input").val("");

        setTimeout(() => {
            $(".j-overlay-modal")[0].style.display = "none";
            $("body").css({"overflow": "auto", "padding-right": "0px"});
            $(".holder-login").removeClass("j-login-reverse-top  j-login-top");
            $(".holder-forgot-pass").removeClass("j-forgot-bottom j-forgot-reverse-bottom ");
        }, 600)
    };

    $(".j-modal-desktop-btn")[0].addEventListener("click", displayModal, false);
    $(".j-modal-mobile-btn")[0].addEventListener("click", displayModal, false);
    $(".j-overlay-modal")[0].addEventListener("click", cancelModal, false);

    $(".j-forgot")[0].addEventListener("click", function () {
        $(".holder-login").addClass("j-login-reverse-top ");
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

$(".j-email-btn")[0].addEventListener('click', function () { // put your email validation
    const email = "an email";
    const inputValue = $(".holder-forgot-pass input").val();
    $(".j-email-mesj ").css({"visibility": "visible"});


    if (email === inputValue) {
        $(".j-email-mesj ").css({"color": "#35ac39"});
        $(".holder-forgot-pass input").css({"border-color": "#35ac39"})
        $(".j-email-mesj ")[0].textContent = "An email has been sent";
    } else {
        $(".j-email-mesj ").css({"color": "red"})
        $(".holder-forgot-pass input").css({"border-color": "red"})
        $(".j-email-mesj ")[0].textContent = "The email that you entered does not exists";

    }
}, false)
