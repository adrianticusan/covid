var translatedMessages = {
    requiredGeneric: $("#message-generic-required").attr("content"),
    minlengthGeneric: $("#message-generic-min").attr("content"),
    passwordValidation: $("#message-password-validation").attr("content")
};

$(document).ready(function () {
    $(".j-login-desktop").click(showLoginModal);
    $(".j-reset-password").click(showResetPasswordModal);
    addValidation($('#j-recovery-password'), passwordRecovery);
    addExtraValidationRules();
});

function addValidation(form, rules) {
    form.validate(rules);
}

function addExtraValidationRules() {
    //
    // Add Password Validation
    $.validator.addMethod("passwordCheck", function (value) {
        return /^[A-Za-z0-9]*$/.test(value);
    });

}


var passwordRecovery = {
    rules: {
        password: {
            minlength: 6,
            required: true,
            passwordCheck: true
        },
        passwordConfirm: {
            minlength: 6,
            required: true,
            equalTo: "#password"
        }
    },
    messages: {
        password: {
            minlength: translatedMessages.minlengthGeneric,
            required: translatedMessages.requiredGeneric,
            passwordCheck: translatedMessages.passwordValidation
        }
    },
    highlight: function (element) {
        $(element).addClass('border-error-color');
    },
    unhighlight: function (element) {
        $(element).removeClass('border-error-color');
    }
}

function showLoginModal() {
    resetInputs();
    clearErrorMessages();
    moveOverlayToLeft();
    $('.reset-password-overlay').fadeIn().removeClass("hidden");
    $(".login-overlay").fadeOut().addClass('hidden');
}
function moveOverlayToLeft() {
    $(".overlay-container").addClass('go-left').removeClass('go-right');
}


function showResetPasswordModal() {
    resetInputs();
    clearErrorMessages()
    moveOverlayToRight();
    $(".login-overlay").fadeIn().removeClass('hidden');
    $('.reset-password-overlay').fadeOut().addClass('hidden');
}
function moveOverlayToRight() {
    $(".overlay-container").addClass('go-right').removeClass('go-left');
}


function resetInputs() {
    setTimeout(function () {
        $("input").removeClass("border-error-color");
        $("form").trigger("reset");
    }, 300)
}
function clearErrorMessages() {
    setTimeout(function () {
        $(".login-mesg ").removeClass("login-mesg-visible");
        $('#j-recovery-password').validate().resetForm();
    }, 300);
}
