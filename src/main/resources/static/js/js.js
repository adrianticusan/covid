$(document).ready(() => {
    // ////////////////////
    // open mobile menu
    $(".bars").click(displayMenu);
    // open login modal on desktop and mobile
    $(".j-modal-desktop-btn, .j-modal-mobile-btn").click(displayLoginModal);
    // reset login modal and email modal after clicking on overlay
    $(".overlay-modal").click(hideModals);
    $(".j-forgot").click(forgotPasswordModal);
    $(".j-button-login").click(manageLogin);
});

// set class active to link click from sub-menu


/* display mobile menu */
const displayMenu = (e) => {
    e.preventDefault();
    $(".mobile-menu").toggleClass("show-flex-element");
    /* we need to increase the nav bar height to push the cover
    down, so in this way the menu can have a decent height */
    $(".header-area").toggleClass("header-area-height");
};


/* display login modal mobile and desktop */
const displayLoginModal = (e) => {
    const body = $("body");
    e.preventDefault();
    if (window.innerWidth > 1024) {
        body.addClass("body-padding-right");
    }
    body.addClass("body-overflow-hidden");
    $(".overlay-modal").addClass("display-overlay-modal");
    setTimeout(() => {
        // ////////////////////////////
        // login modal comes in middle
        $(".login-modal").addClass("login-top");

    }, 100);
};

const forgotPasswordModal = (e) => {
    e.preventDefault();
    // after click on forgot password make login modal to go overtop
    $(".login-modal").addClass("login-reverse-top");
    // show the email modal
    $(".email-forgot-modal").addClass("forgot-bottom");
};

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
    $(".login-modal").addClass("login-reverse-top");
    // animating modal email to go over down
    $(".email-forgot-modal").addClass("forgot-reverse-bottom");
};


const hideMsg = () => {
    $(".login-mesg ").removeClass("login-mesg-visible");
    $(".email-mesg").removeClass("email-mesg-visible error-color primary-color");
};


const inputsReset = () => {
    $(".login-modal input, .email-forgot-modal input").removeClass("border-error-color");
    $(".j-login-form, .j-forgot-email-form").trigger('reset');
};

const clearPage = () => {
    setTimeout(() => {
        // ///////////////////////////////////////////////////////////////
        /* reset overlay and body, remove classes from modal's so can be added
            again when click event occurs j-modal-desktop-btn or .j-modal-mobile-btn */
        resetOverlayBody();
        if (window.innerWidth > 1024) {
            $("body").removeClass("body-padding-right");
        }
        setToDefaultModals();
    }, 600)
};
const resetOverlayBody = () => {
    $(".overlay-modal").removeClass("display-overlay-modal");
    $("body").removeClass("body-overflow-hidden");
};

const setToDefaultModals = () => {
    $(".login-modal").removeClass("login-reverse-top  login-top");
    $(".email-forgot-modal").removeClass("forgot-bottom forgot-reverse-bottom ");
};


function manageLogin(e) {
    e.preventDefault();
    var loginForm = $(".j-login-form");
    const username = $("#login-user");
    const password = $("#login-pass");
    const loginErrorSpan = $(".login-mesg");

    const displayInvalidLoginErrors = () => {
        username.addClass("border-error-color");
        password.addClass("border-error-color")
        loginErrorSpan.addClass("login-mesg-visible");
    };

    const hideLoginErrors = () => {
        username.removeClass("border-error-color");
        loginErrorSpan.removeClass("login-mesg-visible");
    };

    hideLoginErrors();
    if (! isValidLoginData(username.val(), password.val())) {
        displayInvalidLoginErrors();
        return;
    }

    $.post({url: loginForm.attr("action"), data: loginForm.serialize()}).fail(function (error) {
        displayInvalidLoginErrors()
    }).done(function () {
        window.location.reload();
    });
}


$(".j-email-btn").click((e) => {
    e.preventDefault();
    // put your email validation
    const email = "an email";
    const forgotPassInput = $(".email-forgot-modal input");
    const inputValue = forgotPassInput.val();
    const emailMesgSpan = $(".email-mesg");
    emailMesgSpan.addClass("email-mesg-visible");
    if (email === inputValue) {
        emailMesgSpan.addClass("primary-color").text("An email has been sent").removeClass("error-color");
        forgotPassInput.removeClass("border-error-color");
    } else {
        emailMesgSpan.addClass("error-color").text("The email that you entered does not exists").removeClass("primary-color");
        forgotPassInput.addClass("border-error-color");
    }
});

function isValidLoginData(username, password) {
    return username != null && validateEmail(username) && password != null && password.length > 6;
}

function validateEmail(email) {
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}
