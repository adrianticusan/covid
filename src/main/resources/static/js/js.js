$(document).ready(() => {
    // ////////////////////
    // open mobile menu
    $(".bars").click(displayMenu);
    // open login modal on desktop
    $(".j-modal-desktop-btn").click(displayLoginModal);
    // open login modal on mobile
    $(".j-modal-mobile-btn").click(displayLoginModal);
    // reset login modal and email modal after clicking on overlay
    $(".overlay-modal").click(hideModals);


    $(".j-forgot").click(displayEmailModal);

    $(".j-button-login").click(manageLogin);

});


const loginModal = $(".holder-login");
const emailForgotModal = $(".holder-forgot-pass");


/* display mobile menu */
const displayMenu = (e) => {
    e.preventDefault();
    $(".mobile-menu").toggleClass("show-flex-element");
    /* we need to increase the nav bar height to push the cover
    down, so in this way the menu can have a decent height */
    $(".header-area").toggleClass("header-area-height");
};


const body = $("body");
const overlayBody = $(".overlay-modal");
/* display login modal mobile and desktop */
const displayLoginModal = (e) => {
    e.preventDefault();
    if (window.innerWidth > 1024) {
        body.addClass("body-padding-right");

    }
    body.addClass("body-overflow-hidden");
    overlayBody.addClass("display-overlay-modal");
    setTimeout(() => {
        // ////////////////////////////
        // login modal comes in middle
        loginModal.addClass("login-top");
    }, 100);
};

const displayEmailModal = (e) => {
    e.preventDefault();
    // after click on forgot password make login modal to go overtop
    loginModal.addClass("login-reverse-top");
    // show the email modal
    emailForgotModal.addClass("forgot-bottom");

}
// reset css
const hideModals = (e) => {
    e.preventDefault();
    // animating modal's for exit
    animatingModals();
    // remove error mesegges and success from modal's
    hideMsg();
    // clear inputs values and reset css
    inputsReset();
    /* reset the overlay and body, and modals are set to
    default */
    clearPage();
};

const animatingModals = () => {
    // /////////////////////////////////////
    // animating login modal to go over top
    loginModal.addClass("login-reverse-top");
    // animating modal email to go over down
    emailForgotModal.addClass("forgot-reverse-bottom");

}
const loginErrorSpan = $(".login-mesg ");
const emailMesgSpan = $(".email-mesg");


const hideMsg = () => {
    loginErrorSpan.removeClass("login-mesg-visible");
    emailMesgSpan.removeClass("email-mesg-visible error-color primary-color");
}

const loginInput = $(".holder-login input");
const forgotPassInput = $(".holder-forgot-pass input");

const inputsReset = () => {
    loginInput.removeClass("border-error-color");
    forgotPassInput.removeClass("border-error-color");
    $(".j-login-form, .j-forgot-email-form").trigger('reset');
}

const clearPage = () => {
    setTimeout(() => {
        // ///////////////////////////////////////////////////////////////
        /* reset overlay and body, remove classes from modal's so can be added
            again when click event occurs j-modal-desktop-btn or .j-modal-mobile-btn */
        resetOverlayBody();
        if (window.innerWidth > 1024) {
            body.removeClass("body-padding-right");
        }
        setToDefaultModals();
    }, 600)
}
const resetOverlayBody = () => {
    overlayBody.removeClass("display-overlay-modal");
    body.removeClass("body-overflow-hidden");

}

const setToDefaultModals = () => {
    loginModal.removeClass("login-reverse-top  login-top");
    emailForgotModal.removeClass("forgot-bottom forgot-reverse-bottom ");
}


function manageLogin(e) {
    e.preventDefault();
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
    if (username !== inputUsername || pass !== inputPass) {
        loginInput.addClass("border-error-color");
        loginErrorSpan.addClass("login-mesg-visible");
    } else {
        loginInput.removeClass("border-error-color");
        loginErrorSpan.removeClass("login-mesg-visible");
    }
});

$(".j-email-btn").click((e) => {
    e.preventDefault();
    // put your email validation
    const email = "an email";
    const inputValue = forgotPassInput.val();
    emailMesgSpan.addClass("email-mesg-visible");
    if (email === inputValue) {
        emailMesgSpan.addClass("primary-color").removeClass("error-color");
        forgotPassInput.removeClass("border-error-color");
        emailMesgSpan.text("An email has been sent");
    } else {
        emailMesgSpan.addClass("error-color").removeClass("primary-color");
        forgotPassInput.addClass("border-error-color");
        emailMesgSpan.text("The email that you entered does not exists");
    }
});
