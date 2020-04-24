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
            phoneNumber: {
                required: $("#phoneUs").attr("content")
            },
            checkbox: {
                required: $("#checkboxMessage").attr("content")
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
                required: true,
                passwordCheck: true
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
            phoneNumber: {
                required: $("#phoneUs").attr("content")
            },
            checkbox: {
                required: $("#checkboxMessage").attr("content")
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
    }, '');

    // Add Password Validation
    var value = $("#volunteer-pass").val();
    $.validator.addMethod("passwordCheck", function (value) {
        return /^[A-Za-z0-9]*$/.test(value);
    });

});
