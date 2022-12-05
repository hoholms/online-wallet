/* global bootstrap: false */
(() => {
    'use strict'
    const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    tooltipTriggerList.forEach(tooltipTriggerEl => {
        new bootstrap.Tooltip(tooltipTriggerEl)
    })
})()

$('.dropup').on('show.bs.dropdown', function () {
    $('body').append($('.dropdown-menu').css({
        position: 'absolute',
        left: $('.dropdown-menu').offset().left,
        top: $('.dropdown-menu').offset().top
    }).detach());
});

$('.dropup').on('hidden.bs.dropdown', function () {
    $('.dropup').append($('.dropdown-menu').css({
        position: false, left: false, top: false
    }).detach());
});
