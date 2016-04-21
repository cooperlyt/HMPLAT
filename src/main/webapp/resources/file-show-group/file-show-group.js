/*

* 连接 bootstrap-treeview , jquery-ad-gallery,



 */

;(function ($, window, document, undefined) {

    var pluginName = 'fileShowGroup';

    var _default = {};


    _default.options = {


    };


    var Group = function (element, options) {


        this.$element = $(element);

        this.$tree_elements = this.$element.find(".file-group-tree");

        this.$gallery_elements = this.$element.find(".file-group-gallery");
        this.init(options);

        return {
            options: this.options,
            init: $.proxy(this.init, this),
        };
    };

    Group.prototype.init = function (options) {

        if (options.data) {
          this.$tree_elements.treeview({data: options.data ,onNodeSelected:$.proxy(this.treeNodeSelected, this) ,onNodeUnselected:$.proxy(this.treeNodeUnSelected, this)});
          this.showGallery(this.getData(this.$tree_elements.treeview('getSelected', 0)[0]));



        }
    };

    Group.prototype.treeNodeSelected = function (event, node) {
        this.showGallery(this.getData(node));
    }

    Group.prototype.treeNodeUnSelected = function (event, node) {

        alert('treeNodeUnSelected')
    }

    Group.prototype.showGallery = function (data) {
        alert(options['imageServer'])
        this.$gallery_elements.children(".ad-nav").children('.ad-thumbs').children('.ad-thumb-list').children('li').remove();
        for(var i = 0; i < data.length; i++){

            this.$gallery_elements.children(".ad-nav").children('.ad-thumbs').children('.ad-thumb-list').append(

                '<li><a href="images/1.jpg" data-file-id="" title="" alt=""><img src="images/thumbs/t1.jpg" title="Title for 1.jpg"></a></li>'
            );
        }

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