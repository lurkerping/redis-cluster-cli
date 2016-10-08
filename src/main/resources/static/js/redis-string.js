(function ($) {
    'use strict';

    $(function () {

        $("#string-key-id").autocomplete({
                source: "/keys",
                select: function (event, ui) {
                    $.get(
                        "/key",
                        {
                            keyName: ui.item.value
                        },
                        function (data) {
                            $("#string-value-id").val(data);
                        }
                    );
                }
            }
        );

    });
})(jQuery);
