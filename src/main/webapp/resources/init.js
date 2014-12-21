

$(document).ready(
    function(){
        $('.selectpicker').selectpicker();
        $('.js-switch').bootstrapSwitch();



        $('.select-menu-header').click(function(event){ if(!$(event.target).hasClass("js-menu-close")) event.stopPropagation();});
        $('.table-floatThead').floatThead({
            useAbsolutePositioning: false
        });

        $('.select-menu').on('show.bs.dropdown',function(){
            $('.table-floatThead').floatThead('destroy');
        });

        $('.select-menu').on('hide.bs.dropdown',function(){
            $('.table-floatThead').floatThead({
                useAbsolutePositioning: false
            });
        });


        $('.modal').on('show.bs.modal', function (e) {

            $('.js-input-field',document.getElementById(e.target.id)).removeClass('has-success');
            $('.js-input-field',document.getElementById(e.target.id)).removeClass('has-error');
            $('.js-form-input',document.getElementById(e.target.id)).removeClass('edit-valid');
            $('.js-form-input',document.getElementById(e.target.id)).removeClass('edit-success');
            $('.js-form-input',document.getElementById(e.target.id)).removeClass('edit-error');
        })
        initEditInput();
    }
);

function initEditInput(){
    $('.js-input-field').removeClass('has-success');
    $('.js-input-field').removeClass('has-error');
    $('.js-form-input').removeClass('edit-valid');
    $('.js-form-input').removeClass('edit-success');
    $('.js-form-input').removeClass('edit-error');
}


function startEditValid(obj){
    $(document.getElementById(obj.id).parentElement).removeClass('edit-valid');
    $(document.getElementById(obj.id).parentElement).removeClass('edit-success');
    $(document.getElementById(obj.id).parentElement).removeClass('edit-error');
    $(document.getElementById(obj.id).parentElement).addClass('edit-valid');
}