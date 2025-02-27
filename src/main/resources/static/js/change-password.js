$(document).ready(function () {
    addPasswordRules();
    changePassword();
});

function changePassword() {
    $(".j-change-password").click(function () {
        $(".j-change-password-form").submit();
    });
}
function addPasswordRules() {
    var value = $(".j-new-password").val();

    $(".j-change-password-form").validate(passwordRules)
}

var translatedMessages = {
    requiredGeneric: $("#message-generic-required").attr("content"),
    minlengthGeneric: $("#message-generic-min").attr("content"),
    passwordValidation: $("#message-password-validation").attr("content"),
};

var passwordRules = {
    rules: {
        oldPassword: {
            required: true,
            minlength: 6,
        },
        newPassword: {
            required: true,
            minlength: 6,
        },
    },
    messages: {
        firstName: {
            required: translatedMessages.requiredGeneric,
            minlength: translatedMessages.minlengthGeneric
        },

    },
    highlight: function (element) {
        $(element).addClass('border-error-color');
    },
    unhighlight: function (element) {
        $(element).removeClass('border-error-color');
    }
};