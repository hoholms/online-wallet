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

(function ($) {

    "use strict";

    $(".tran-card").tilt({
        maxTilt: 20,
        perspective: 1400,
        easing: "cubic-bezier(.03,.98,.52,.99)",
        speed: 1200,
        glare: true,
        maxGlare: 0.05,
        scale: 1.04,
    });
}(jQuery));

function toggleCategorySelects() {
    var isIncomeChecked = document.getElementById('isIncome').checked;
    var isExpenseChecked = document.getElementById('isExpense').checked;
    document.getElementById('incomeCategories').disabled = !isIncomeChecked;
    document.getElementById('expenseCategories').disabled = !isExpenseChecked;
}

$( '#incomeCategories' ).select2( {
    theme: "bootstrap-5",
    width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
    placeholder: $( this ).data( 'placeholder' ),
    closeOnSelect: false,
    allowClear: true,
} );

$( '#expenseCategories' ).select2( {
    theme: "bootstrap-5",
    width: $( this ).data( 'width' ) ? $( this ).data( 'width' ) : $( this ).hasClass( 'w-100' ) ? '100%' : 'style',
    placeholder: $( this ).data( 'placeholder' ),
    closeOnSelect: false,
    allowClear: true,
} );
