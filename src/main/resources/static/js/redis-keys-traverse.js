(function ($) {
    'use strict';

    $(function () {
        
        $.getJSON("/keys", {"pattern": "*"}, function (data) {
            var items = [];
            $.each(data, function (key, value) {
                items.push("<li><a href='#'>" + value + "</a></li>");
            });

            $("<ul/>", {
                "class": "am-list am-list-border",
                html: items.join("")
            }).appendTo("#keysList");
        });

    });
})(jQuery);
