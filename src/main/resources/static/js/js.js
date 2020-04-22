$(document).ready(() => {

    $(".j-go-to-section").click((e) => {
        e.preventDefault();
        goToSection($(e.target).parent().attr('class'));
    })

    // open mobile menu
    $(".bars").click(displayMenu);
    // open login modal on desktop and mobile
    $(".j-modal-desktop-btn, .j-modal-mobile-btn").click((e) => {
        e.preventDefault();
        displayModal(".login-modal");
    });
    // reset login modal and email modal after clicking on overlay
    $(".overlay-modal").click(hideModals);
    $(".j-forgot").click(forgotPasswordModal);
    $(".j-button-login").click(manageLogin);
    displayLoginSuccesfulIfNeeded();
    // clear inputs when focus
    $('input').focus(function (e) {
        this.value = '';
    });

    $('.name').blur(manageValidationName);
    $('.email').blur(manageValidationEmail);

    $(".password").click(function (e) {
        e.preventDefault();
        var message = "Your password must contain minim 6 characters, with at least a symbol, upper and lower case letters and a number ex: Abc.1234";
        infoFormat(e, message);
    });
    $('.password').blur(manageValidationPass);

    $(".adress").click(function (e) {
        e.preventDefault();
        var message = "Please select one of the addresses suggested by google";
        infoFormat(e, message);
    });
    $('.adress').blur(manageValidationAdress);
    $(".phone").blur(manageValidationPhone)
});


function manageValidationName(e) {
    var inputName = $(e.target).val();
    var errorMessage = "Your name should contain only letters, minim three. If your name contains space ex:John Doe please use a hiphen John-Doe";
    validateInput(e, isValidName(inputName), errorMessage);
}
function isValidName(name) {
    //
    // match all letters use hyphens instead of space min 3 letters
    var re = /^[a-zA-Z]+(?:-?[a-zA-Z]+){2,}$/i;
    return re.test(name);
}


// click event
function infoFormat(e, message) {
    messageInfo(e, message);
}
function manageValidationPass(e) {
    var inputPassword = $(e.target).val();
    var message = "Password does not meet requirements";
    validateInput(e, isValidPassword(inputPassword), message);
}
function isValidPassword(password) {
    //
    // min 6 letter password, with at least a symbol, upper and lower case letters and a number
    var re = /^(?=.*\d)(?=.*[!@#$%^&*.])(?=.*[a-z])(?=.*[A-Z]).{6,}$/;
    return re.test(password);
}


function manageValidationEmail(e) {
    var inputEmail = $(e.target).val();
    validateInput(e, validateEmail(inputEmail), "Keep e-mail format ex : demo@company.com");
}

function manageValidationAdress() { /* code me*/
}

function manageValidationPhone(e) {
    var inputName = $(e.target).val();
    var errorMessage = "Please specify a valid phone number";
    validateInput(e, isValidPhoneNumber(inputName), errorMessage);
}
function isValidPhoneNumber(phoneNumber) {
    //
    /*Supports :(123) 456 7899 (123).456.7899 (123)-456-7899 123-456-7899
    123 456 7899 1234567899*/
    var re = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/;
    return re.test(phoneNumber);
}


function validateInput(e, isValidInput, errorMessage) {
    if (! isValidInput) {
        displayInputsErrorBorder(e);
        messageValidationError(e, errorMessage);
    } else {
        hideInputsErrorBorder(e);
        messageValidationError(e, "");
    }
}
function displayInputsErrorBorder(e) {
    $(e.target).addClass('border-error-color');
}

function messageInfo(e, infoMessage) {
    $(e.target).next('.danger').addClass("info").text(infoMessage);
}

function messageValidationError(e, errorMessage) {
    $(e.target).next('.danger').removeClass('info').text(errorMessage);
}
function hideInputsErrorBorder(e) {
    $(e.target).removeClass('border-error-color');
}


function displayLoginSuccesfulIfNeeded() {
    if ($("#registrationSuccessful").attr('content') == "true") {
        elderRegistrationModal();
    }
}

const goToSection = (section) => {
    $('html, body').animate({
        scrollTop: $(`section.${section}`).offset().top
    }, 100);

}


/* display mobile menu */
const displayMenu = (e) => {
    e.preventDefault();
    $(".mobile-menu").toggleClass("show-flex-element");
    /* we need to increase the nav bar height to push the cover
    down, so in this way the menu can have a decent height */
    $(".header-area").toggleClass("header-area-height");
};


/* display modal's login and success registration elder*/
const displayModal = (modal) => {
    const body = $("body");
    const bodyWidth = body.width();
    body.addClass("body-overflow-hidden").css({'width': bodyWidth});
    $(".overlay-modal").addClass("display-overlay-modal");
    // when elder registration modal appears, button scroll up will be display none
    $(".scroll-up").hide().removeClass("show-scroll-up");
    setTimeout(() => {
        // ////////////////////////////
        // modal comes in middle
        $(modal).addClass("modal-visible-top");

    }, 100);
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
    // animating  modal's to go over top
    $(".login-modal, .success-modal").addClass("modal-reverse-top");
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
    $(".login-modal, .success-modal").removeClass("modal-reverse-top  modal-visible-top");
    $(".email-forgot-modal").removeClass("forgot-bottom forgot-reverse-bottom ");
};


const forgotPasswordModal = (e) => {
    e.preventDefault();
    // after click on forgot password make login modal to go overtop
    $(".login-modal").addClass("modal-reverse-top");
    // show the email modal
    $(".email-forgot-modal").addClass("forgot-bottom");
};

/* after elder registration is complete show this modal */
const elderRegistrationModal = (e) => {
    displayModal(".success-modal");
};


// When the user scrolls down 100px from the top of the document, show the button
window.onscroll = () => {
    scrollFunction()
};

const scrollFunction = () => {
    const scrollToTopBtn = $(".scroll-up");
    if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
        //
        /* we added fade in because the button will disappear when elder registration modal appears */
        scrollToTopBtn.addClass("show-scroll-up").fadeIn();
    } else {
        scrollToTopBtn.removeClass("show-scroll-up")
    }
}


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

    var token = $('#_csrf').attr('content');
    var header = $('#_csrf_header').attr('content');

    $.post({
        url: loginForm.attr("action"),
        data: loginForm.serialize(),
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
    }).fail(function (error) {
        displayInvalidLoginErrors()
    }).done(function () {
        window.location = $("#volunteerPage").attr('content');
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
