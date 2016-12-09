

(function ($) {
    jQuery.fn.describeDisplay = function () {
        return this.each(function () {
            var $this = $(this)
            var data  = $this.data('describeDisplay')

            if (!data) {

                var lines = $this.data('description').dataLines;
                $.each(lines, function (i, n){
                    $this.append('append');
                    $this.prepend('prepend');
                    $this.after('after');
                    $this.before('before');


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
