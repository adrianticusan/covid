$(document).ready(function () {
    $(".j-login-desktop").click(animateOverlay);
});

function animateOverlay(e) {
    e.preventDefault();
    var overlayGoLeft = $(".overlay-container");
    overlayGoLeft.addClass('go-left');
    $(".reset-password").addClass('go-right').hide();
    $(".login-modal-desktop").addClass('show-login-modal-desktop go-zero');
    $(".welcome .btn").hide();
    $(".welcome i").removeClass('hidden');
}
