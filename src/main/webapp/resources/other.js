

$(document).ready(
    function(){
        $('.selectpicker').selectpicker();
        $('.select-menu-header').click(function(event){ if(!$(event.target).hasClass("js-menu-close")) event.stopPropagation();});

        $('.modal').on('show.bs.modal', function (e) {

            $('.form-group',document.getElementById(e.target.id)).removeClass('has-success');
            $('.form-group',document.getElementById(e.target.id)).removeClass('has-error');
            $('.js-form-input',document.getElementById(e.target.id)).removeClass('edit-valid');
            $('.js-form-input',document.getElementById(e.target.id)).removeClass('edit-success');
            $('.js-form-input',document.getElementById(e.target.id)).removeClass('edit-error');
        })
        initEditInput();
    }
);

function initEditInput(){
    $('.form-group').removeClass('has-success');
    $('.form-group').removeClass('has-error');
    $('.js-form-input').removeClass('edit-valid');
    $('.js-form-input').removeClass('edit-success');
    $('.js-form-input').removeClass('edit-error');
}


