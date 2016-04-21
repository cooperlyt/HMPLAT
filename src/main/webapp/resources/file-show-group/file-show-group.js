/*

* 连接 bootstrap-treeview , jquery-ad-gallery,



 */

;(function ($, window, document, undefined) {

    var pluginName = 'fileShowGroup';

    var _default = {};

    _default.gallery_options = {

        loader_image: '',
        update_window_hash: false
    };


    _default.tree_options= {

        color: undefined
    };

    _default.fancybox_options = {
        closeBtn: true
    };


    _default.options = {
        imageServer: '',


    };

    var Group = function (element, options) {

        this.options = $.extend({}, _default.options, options);



        this.$element = $(element);

        this.$tree_elements = this.$element.find(".file-group-tree");

        this.$gallery_elements = this.$element.find(".file-group-gallery");

        this.galleryTemplate = this.$gallery_elements.clone().html();
        this.$file_empty_elements = this.$element.find(".file-group-empty");

        this.init(options);

        return {
            options: this.options,
            init: $.proxy(this.init, this),
        };
    };

    Group.prototype.init = function (options) {
        this.tree_option = $.extend({},options, {onNodeSelected:$.proxy(this.treeNodeSelected, this) ,onNodeUnselected:$.proxy(this.treeNodeUnSelected, this)});
        this.gallery_options = $.extend({}, _default.gallery_options, options);
        this.fancybox_options = $.extend({}, _default.fancybox_options, options);

        if (options.data) {
            this.$tree_elements.treeview(this.tree_option);


            this.showGallery(this.getData(this.$tree_elements.treeview('getSelected', 0)[0]));



        }
    };

    Group.prototype.treeNodeSelected = function (event, node) {
        this.showGallery(this.getData(node));
    }

    Group.prototype.treeNodeUnSelected = function (event, node) {

        this.$gallery_elements.children(".ad-gallery").remove();
    }

    Group.prototype.showGallery = function (data) {

        this.$gallery_elements.children(".ad-gallery").remove();


        if (data && (data.length > 0)) {
      
            this.$file_empty_elements.show().hide();
            this.$gallery_elements.append(this.galleryTemplate);

            var imageServer = this.options['imageServer'];


            for (var i = 0; i < data.length; i++) {

                this.$gallery_elements.children(".ad-gallery").find('.ad-thumb-list').append(
                    '<li><a href="' + imageServer + 'img/800x600s/' + data[i].fid + '">' +
                    '<img src="' + imageServer + 'img/100x100/' + data[i].fid + '" data-file-id="' + data[i].fid + '" title="' + data[i].title + '" alt="' + data[i].description + '"/></a></li>'
                );
            }
            this.$gallery_elements.children(".ad-gallery").adGallery(this.gallery_options);

            this.$gallery_elements.children(".ad-gallery").on("click", ".ad-image", function () {

                // alert($(this).find("img").data("file-id"));
                //TODO 多个图

                var opt = $.extend({}, this.fancybox_options, {
                    href: imageServer + 'img/orig/' + $(this).find('img').data('file-id') + '.jpg',
                    title: $(this).find('img').attr('title')
                });

                $.fancybox(opt);
            });

        }else{

            this.$file_empty_elements.show();
        }
        //this.gallery.init();

    }

    Group.prototype.getData = function (node) {

        var result = [];
        var children = node.nodes;
        if (children) {
            for (var i = 0; i < children.length; i++) {
                var childNode = children[i];
                result = result.concat(this.getData(childNode));

            }
        }else{
            result = result.concat(node.files)
        }

        return result;
    }

    $.fn[pluginName] = function (options) {
        var result;

        this.each(function () {

                $.data(this, pluginName, new Group(this, $.extend(true, {}, options)));
            }
        );

        return result || this;
    }

})(jQuery, window, document);