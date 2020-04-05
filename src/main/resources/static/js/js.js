// Show menu mobile
const menu = document.querySelector(".mobile-menu");
const heightOfNav = document.querySelector(".header-area");


const displayMenu = () => {
    if (menu.style.display === "none" || menu.style.display === "") {
        menu.style.display = "flex";
        heightOfNav.style.height = "40rem";
    } else {
        menu.style.display = "none";
        heightOfNav.style.height = "7rem";
    }
};
document.querySelector(".bars").addEventListener("touchend", displayMenu);

$(document).ready(function () {
    $(".j-button-login").click(manageLogin);
    const modalLogin = document.querySelector(".holder-login");
    const overlay = document.querySelector(".overlay-modal");


    const displayModal = () => {
        if (modalLogin.style.display === "none" || modalLogin.style.display === "") {
            modalLogin.style.display = "flex";
            overlay.style.display = "block"
        } else {
            modalLogin.style.display = "none";
            overlay.style.display = "none"
        }

    }
    const cancelModal = () => {
        modalLogin.style.display = "none";
        overlay.style.display = "none";
    }

    const loginContainer = document.querySelector("div.container  div.login-container");
    loginContainer.addEventListener("click", displayModal, false);
    overlay.addEventListener("click", cancelModal, false);
});

function manageLogin(event) {
    event.preventDefault();
    var loginForm = $(".j-login-form");
    $.ajax({
        type: "POST",
        url: loginForm.attr('action'),
        data: loginForm.serialize(),
    }).error(function (error) {

    }).success(function() {
        window.location.reload();
    })
}
