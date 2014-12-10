

$(document).ready(
    function(){
        $('.selectpicker').selectpicker();
        $('.select-menu-header').click(function(event){ if(!$(event.target).hasClass("js-menu-close")) event.stopPropagation();});
    }
);

