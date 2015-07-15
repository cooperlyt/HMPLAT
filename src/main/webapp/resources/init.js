

$(document).ready(
    function(){
        $('.selectpicker').selectpicker();
        $('.mini-select').selectpicker('setStyle', 'btn-sm');
        $('.js-switch').bootstrapSwitch();

        $('.js-only-int').keyup(function(){
            $(this).val($(this).val().replace(/\D|^0/g,''));
        }).bind("paste",function(){
            $(this).val($(this).val().replace(/\D|^0/g,''));
        }).css("ime-mode", "disabled");

        $(".js-only-number").keyup(function(){
            $(this).val($(this).val().replace(/[^0-9.]/g,''));
        }).bind("paste",function(){
            $(this).val($(this).val().replace(/[^0-9.]/g,''));
        }).css("ime-mode", "disabled");


        $('.select-menu-header').click(function(event){ if(!$(event.target).hasClass("js-menu-close")) event.stopPropagation();});
        $('.select-menu-filters').click(function(event){ event.stopPropagation();});


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
        });

        $('.js-datepicker').datetimepicker({
            language: "zh-CN",
            format: 'yyyy-mm-dd',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });

        $('.js-datetimepicker').datetimepicker({
            language: "zh-CN",

            clearBtn: 1,
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1
        });

        $('.js-timepicker').datetimepicker({
            language: "zh-CN",

            clearBtn: 1,
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1
        });


    }
);

function initEditInput(){
    $('.js-input-field').removeClass('has-success');
    $('.js-input-field').removeClass('has-error');
    $('.js-form-input').removeClass('edit-valid');
    $('.js-form-input').removeClass('edit-success');
    $('.js-form-input').removeClass('edit-error');
}

function initDynamicEditInputs(id){
    $('.js-input-field',document.getElementById(id)).removeClass('has-success');
    $('.js-input-field',document.getElementById(id)).removeClass('has-error');
    $('.js-form-input',document.getElementById(id)).removeClass('edit-valid');
    $('.js-form-input',document.getElementById(id)).removeClass('edit-success');
    $('.js-form-input',document.getElementById(id)).removeClass('edit-error');
}


function startEditValid(obj){
    $(document.getElementById(obj.id).parentElement).removeClass('edit-valid');
    $(document.getElementById(obj.id).parentElement).removeClass('edit-success');
    $(document.getElementById(obj.id).parentElement).removeClass('edit-error');
    $(document.getElementById(obj.id).parentElement).addClass('edit-valid');
}