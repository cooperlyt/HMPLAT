

(function ($) {
    jQuery.fn.describeDisplay = function () {
        return this.each(function () {
            var $this = $(this)
            var data  = $this.data('describeDisplay')

            if (!data) {


                $this.data('describeDisplay', {target: $this})
            }

        })
    };

    $(window).on('load.describeDisplay', function () {
        $('[data-describe="json"]').each(function () {
            var $carousel = $(this)
            Plugin.call($carousel, $carousel.data())
        })
    })

})(jQuery);
