$(document).ready(() => {

    var elderRegisterForm = $("#j-elder-registration");
    elderRegisterForm.validate({
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
            adress: {
                required: true,
                country: true
            },
            phoneNumber: {
                required: true,
                phoneUS: true
            }
        },
        messages: {
            checkbox: {
                required: "You must check the box."
            }
        },
        highlight: function (element) {
            $(element).addClass('border-error-color');
        },
        unhighlight: function (element) {
            $(element).removeClass('border-error-color');
        }

    })
    var volunteerRegisterForm = $('#j-volunteer-registration');
    volunteerRegisterForm.validate({
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
                maxlength: 30,
                required: true,
                passwordCheck: true,
                checklower: true,
                checkupper: true,
                checkdigit: true
            },
            adress: {
                required: true,
                country: true
            },
            phoneNumber: {
                required: true,
                phoneUS: true
            }
        },
        messages: {
            checkbox: {
                required: "You must check the box."
            },
            password: {
                passwordCheck: "Password is not strong enough."
            },
            uploadedFile: {
                required: "You must upload a photo with your id"
            }
        },
        highlight: function (element) {
            $(element).addClass('border-error-color');
        },
        unhighlight: function (element) {
            $(element).removeClass('border-error-color');
        }

    })
    var contactForm = $("#j-contact-form");
    contactForm.validate({
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
        highlight: function (element) {
            $(element).addClass('border-error-color');
        },
        unhighlight: function (element) {
            $(element).removeClass('border-error-color');
        }

    })
    // Add US Phone Validation
    $.validator.addMethod('phoneUS', function (phoneNumber, element) {
        phoneNumber = phoneNumber.replace(/\s+/g, '');
        return this.optional(element) || phoneNumber.length > 9 && phoneNumber.match(/^(1-?)?(\([2-9]\d{2}\)|[2-9]\d{2})-?[2-9]\d{2}-?\d{4}$/);
    }, 'Please enter a valid phone number.');

    // Add Password Validation
    var value = $("#volunteer-pass").val();

    $.validator.addMethod("checklower", function (value) {
        return /[a-z]/.test(value);
    });
    $.validator.addMethod("checkupper", function (value) {
        return /[A-Z]/.test(value);
    });
    $.validator.addMethod("checkdigit", function (value) {
        return /[0-9]/.test(value);
    });
    $.validator.addMethod("passwordCheck", function (value) {
        return /^[A-Za-z0-9\d=!\-@._*]*$/.test(value) && /[a-z]/.test(value) && /\d/.test(value) && /[A-Z]/.test(value);
    });

});
