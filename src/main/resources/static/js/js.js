"use strict";

$(document).ready(function () {
    $(".j-go-to-section").click(function (e) {
        goToSection($(e.target).parent().attr('class'));
    });

    // open mobile menu
    $(".bars").click(displayMenu);

    // open login modal on desktop and mobile
    $(".j-modal-desktop-btn, .j-modal-mobile-btn").click(function (e) {
        e.preventDefault();
        displayModal(".login-modal");
    });
    // reset login modal and email modal after clicking on overlay
    $(".overlay-modal").click(hideModals);
    $(".j-forgot").click(forgotPasswordModal);
    $(".j-button-login").click(manageLogin);
    displayLoginSuccesfulIfNeeded();
    goToFormWithErrors();
    $(".j-beatingheart-message,.j-beatingheart-message-mobile").click(usersWhoNeedHelp);

});

function usersWhoNeedHelp(e) {
    e.preventDefault();
    if (!navigator.geolocation) {
        $(".notification-message .primary-color").html("");
        displayNotificationMessagge()
    }
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var geolocation = {
                latitude: position.coords.latitude,
                longitude: position.coords.longitude
            };
            $.get({
                url: $("#countUsers").attr("content"),
                data: geolocation,
            }).fail(function (error) {
                $(".notification-message .primary-color").html("");
                $(".notification-message").show();
            }).done(function (response) {
                $(".notification-message .primary-color").html(response);
                $(".notification-message").show();
            });

        });
        displayNotificationMessagge()
    }
}

function displayNotificationMessagge() {
    $(".notification-message").fadeIn().removeClass('hidden');
}
function hideNotificationMessage() {
    $(".notification-message").fadeOut().addClass('hidden');
}

function displayLoginSuccesfulIfNeeded() {
    if ($("#registrationSuccessful").attr("content") == "true") {
        elderRegistrationModal();
    }
}

var goToFormWithErrors = function () {
    var getHere = $(".get-here");
    if (getHere.length == 0) {
        return;
    }
    $('html, body').animate({
        scrollTop: getHere.offset().top
    }, 100);
}

var goToSection = function goToSection(section) {
    var offSetElement = $("section.".concat(section)).offset();
    $('html, body').animate({
        scrollTop: offSetElement.top
    }, 100);
};
/* display mobile menu */


var displayMenu = function displayMenu(e) {
    e.preventDefault();
    hideNotificationMessage();
    $(".mobile-menu").toggleClass("show-flex-element");
    /* we need to increase the nav bar height to push the cover
  down, so in this way the menu can have a decent height */

    $(".header-area").toggleClass("header-area-height");
};
/* display modal's login and success registration elder*/


var displayModal = function displayModal(modal) {
    var body = $("body");
    var bodyWidth = body.width();
    body.addClass("body-overflow-hidden").css({'width': bodyWidth});
    $(".overlay-modal").addClass("display-overlay-modal"); // when elder registration modal appears, button scroll up will be display none

    $(".scroll-up").hide().removeClass("show-scroll-up");
    setTimeout(function () {
        // ////////////////////////////
        // modal comes in middle
        $(modal).addClass("modal-visible-top");
    }, 100);
}; // reset css


var hideModals = function hideModals(e) {
    e.preventDefault(); // animating modal's for exit

    animatingModals(); // remove error mesegges and success from modal's

    hideMsg(); // clear inputs values and reset css

    inputsReset();
    /* reset the overlay and body, and modals are set to
  default */

    clearPage();
};

var animatingModals = function animatingModals() {
    // /////////////////////////////////////
    // animating  modal's to go over top
    $(".login-modal, .success-modal").addClass("modal-reverse-top"); // animating modal email to go over down

    $(".email-forgot-modal").addClass("forgot-reverse-bottom");
};

var hideMsg = function hideMsg() {
    $(".login-mesg ").removeClass("login-mesg-visible");
    $(".email-mesg").removeClass("email-mesg-visible error-color primary-color");
};

var inputsReset = function inputsReset() {
    $(".login-modal input, .email-forgot-modal input").removeClass("border-error-color");
    $(".j-login-form, .j-forgot-email-form").trigger("reset");
};

var clearPage = function clearPage() {
    setTimeout(function () {
        // ///////////////////////////////////////////////////////////////

        /* reset overlay and body, remove classes from modal's so can be added
        again when click event occurs j-modal-desktop-btn or .j-modal-mobile-btn */
        resetOverlayBody();

        if (window.innerWidth > 1024) {
            $("body").removeClass("body-padding-right");
        }

        setToDefaultModals();
    }, 600);
};

var resetOverlayBody = function resetOverlayBody() {
    $(".overlay-modal").removeClass("display-overlay-modal");
    $("body").removeClass("body-overflow-hidden");
};

var setToDefaultModals = function setToDefaultModals() {
    $(".login-modal, .success-modal").removeClass("modal-reverse-top  modal-visible-top");
    $(".email-forgot-modal").removeClass("forgot-bottom forgot-reverse-bottom ");
};

var forgotPasswordModal = function forgotPasswordModal(e) {
    e.preventDefault(); // after click on forgot password make login modal to go overtop

    $(".login-modal").addClass("modal-reverse-top"); // show the email modal

    $(".email-forgot-modal").addClass("forgot-bottom");
};
/* after elder registration is complete show this modal */
var elderRegistrationModal = function elderRegistrationModal(e) {
    displayModal(".success-modal");
}; // When the user scrolls down 100px from the top of the document, show the button


window.onscroll = function () {
    scrollFunction();
};

var scrollFunction = function scrollFunction() {
    hideNotificationMessage();

    var scrollToTopBtn = $(".scroll-up");

    if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
        //
        /* we added fade in because the button will disappear when elder registration modal appears */
        scrollToTopBtn.addClass("show-scroll-up").fadeIn();
    } else {
        scrollToTopBtn.removeClass("show-scroll-up");
    }
};

function manageLogin(e) {
    e.preventDefault();
    var loginForm = $(".j-login-form");
    var username = $("#login-user");
    var password = $("#login-pass");
    var loginErrorSpan = $(".login-mesg");

    var displayInvalidLoginErrors = function displayInvalidLoginErrors() {
        username.addClass("border-error-color");
        password.addClass("border-error-color");
        loginErrorSpan.addClass("login-mesg-visible");
    };

    var hideLoginErrors = function hideLoginErrors() {
        username.removeClass("border-error-color");
        loginErrorSpan.removeClass("login-mesg-visible");
    };

    hideLoginErrors();

    var token = $('#_csrf').attr('content');
    var header = $('#_csrf_header').attr('content');
    $.post({
        url: loginForm.attr("action"),
        data: loginForm.serialize(),
        beforeSend: function beforeSend(xhr) {
            xhr.setRequestHeader(header, token);
        }
    }).fail(function (error) {
        displayInvalidLoginErrors();
    }).done(function () {
        window.location = $("#volunteerPage").attr('content');
    });
}

$(".j-email-btn").click(function (e) {
    e.preventDefault(); // put your email validation

    var email = "an email";
    var forgotPassInput = $(".email-forgot-modal input");
    var inputValue = forgotPassInput.val();
    var emailMesgSpan = $(".email-mesg");
    emailMesgSpan.addClass("email-mesg-visible");

    if (email === inputValue) {
        emailMesgSpan.addClass("primary-color").text("An email has been sent").removeClass("error-color");
        forgotPassInput.removeClass("border-error-color");
    } else {
        emailMesgSpan.addClass("error-color").text("The email that you entered does not exists").removeClass("primary-color");
        forgotPassInput.addClass("border-error-color");
    }
});
