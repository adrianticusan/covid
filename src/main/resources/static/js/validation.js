var translatedMessages = {
    requiredGeneric: $("#message-generic-required").attr("content"),
    minlengthGeneric: $("#message-generic-min").attr("content"),
    passwordValidation: $("#message-password-validation").attr("content"),
    requiredPhone: $("#message-phone-required").attr("content"),
    requiredCheckbox:$("#message-checkbox-required").attr("content"),
};
$(document).ready(() => {
    addValidation($("#j-elder-registration"), userRules);
    addValidation($('#j-volunteer-registration'), volunteerRules);
    addValidation($("#j-contact-form"), contactFormRules);
    addExtraValidationRules();

});

function addValidation(form, rules) {
    form.validate(rules);
}

function addExtraValidationRules() {
    // Add US Phone Validation
    $.validator.addMethod('phoneUS', function (phoneNumber, element) {
        return this.optional(element) || phoneNumber.length > 9 && phoneNumber.match(/^(1-?)?(\([2-9]\d{2}\)|[2-9]\d{2})-?[2-9]\d{2}-?\d{4}$/);
    }, '');

    // Add Password Validation
    var value = $("#volunteer-pass").val();
    $.validator.addMethod("passwordCheck", function (value) {
        return /^[A-Za-z0-9]*$/.test(value);
    });
}

var userRules = {
    rules: {
        firstName: {
            required: true,
            minlength: 3,
        },
        lastName: {
            required: true,
            minlength: 3
        },
        email: {
            required: true,
            email: true
        },
        address: {
            required: true,
        },
        phoneNumber: {
            required: true,
            phoneUS: true
        }
    },
    messages: {
        firstName: {
            required: translatedMessages.requiredGeneric,
            minlength: translatedMessages.minlengthGeneric
        },
        lastName: {
            required: translatedMessages.requiredGeneric,
            minlength: translatedMessages.minlengthGeneric
        },
        address: {
            required: translatedMessages.requiredGeneric,
        },
        email: {
            required: translatedMessages.requiredGeneric,
            minlength: translatedMessages.minlengthGeneric
        },
        phoneNumber: {
            required: translatedMessages.requiredPhone
        },
        checkbox: {
            required: translatedMessages.requiredCheckbox
        }
    },
    highlight: function (element) {
        $(element).addClass('border-error-color');
    },
    unhighlight: function (element) {
        $(element).removeClass('border-error-color');
    }
};

var volunteerRules = {
    rules: {
        firstName: {
            required: true,
            minlength: 3
        },
        lastName: {
            required: true,
            minlength: 3
        },
        email: {
            required: true,
            email: true
        },
        password: {
            minlength: 6,
            required: true,
            passwordCheck: true
        },
        address: {
            required: true,
        },
        phoneNumber: {
            required: true,
            phoneUS: true
        }
    },
    messages: {
        firstName: {
            required: translatedMessages.requiredGeneric,
            minlength: translatedMessages.minlengthGeneric
        },
        lastName: {
            required: translatedMessages.requiredGeneric,
            minlength: translatedMessages.minlengthGeneric
        },
        address: {
            required: translatedMessages.requiredGeneric,
        },
        email: {
            required: translatedMessages.requiredGeneric,
            minlength: translatedMessages.minlengthGeneric
        },
        password: {
            minlength: translatedMessages.minlengthGeneric,
            required: translatedMessages.requiredGeneric,
            passwordCheck: translatedMessages.passwordValidation
        },
        phoneNumber: {
            required:translatedMessages.requiredPhone
        },
        checkbox: {
            required: translatedMessages.requiredCheckbox
        }
    },
    highlight: function (element) {
        $(element).addClass('border-error-color');
    },
    unhighlight: function (element) {
        $(element).removeClass('border-error-color');
    }
};

contactFormRules = {
    rules: {
        firstName: {
            required: true,
            minlength: 3
        },
        email: {
            required: true,
            email: true
        }
    },
    messages: {
        firstName: {
            required: $("#message-generic-required").attr("content"),
            minlength: $("#message-generic-min").attr("content")
        },
        email: {
            required: $("#message-generic-required").attr("content"),
            minlength: $("#message-generic-min").attr("content")
        },
    },
    highlight: function (element) {
        $(element).addClass('border-error-color');
    },
    unhighlight: function (element) {
        $(element).removeClass('border-error-color');
    }
};


