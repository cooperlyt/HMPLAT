

(function ($) {
    jQuery.fn.describeDisplay = function () {
        return this.each(function () {
            var $this = $(this)
            var data  = $this.data('describeDisplay')

            if (!data) {

                var lines = $this.data('description').dataLines;
                $.each(lines, function (i, n){
                    var datas = n.displayDatas;

                    var data = '';
                    $.each(datas,function (i1,n1) {
                        data += '<span class="list-description-block biz-' + n1.displayStyle + '">' + n1.value + '</span>';
                    });

                    $this.append('<p class="repo-list-description biz-' + n.displayStyle + '">' + data + '</p>');

                });

                $this.data('describeDisplay', {target: $this})
            }

        })
    };

    $(window).on('load.describeDisplay', function () {

        $('[data-describe="json"]').each(function () {

            $(this).describeDisplay();


        })
    })

})(jQuery);
