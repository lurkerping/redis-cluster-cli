(function ($) {
    'use strict';

    $(function () {

        $("#string-key-id").autocomplete({
                source: "/keys",
                select: function (event, ui) {
                    $.get(
                        "/string/get",
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

        $("#stringValueForm").submit(function (event) {
            event.preventDefault();
            $.post("/string/set", {
                keyName: $("#string-key-id").val(),
                keyValue: $("#string-value-id").val()
            }, function (data) {
                if(data.retCode == 'succ'){
                    alert('succ');
                }
            });
        });

    });
})(jQuery);
