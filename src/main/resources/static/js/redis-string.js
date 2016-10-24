(function ($) {
    'use strict';

    $(function () {

        function refreshStringValue(key) {
            $.get(
                "/string/get",
                {
                    keyName: key
                },
                function (data) {
                    $("#string-value-id").val(data);
                }
            );
        }

        function refreshStringTtl(key) {
            $.get(
                "/string/getTtl",
                {
                    keyName: key
                },
                function (data) {
                    $("#string-ttl-id").val(data);
                }
            );
        }

        var key = $.getParam("key");
        if (key != null && key.length > 0) {
            $("#string-key-id").val(key);
            refreshStringValue(key);
            refreshStringTtl(key);
        }

        $("#string-key-id").autocomplete({
                source: "/keys",
                select: function (event, ui) {
                    refreshStringValue(ui.item.value);
                    refreshStringTtl(ui.item.value);
                }
            }
        );

        $("#stringValueForm").submit(function (event) {
            event.preventDefault();
            $.post("/string/set", {
                keyName: $("#string-key-id").val(),
                keyValue: $("#string-value-id").val(),
                ttl: $("#string-ttl-id").val()
            }, function (data) {
                if (data.retCode == 'succ') {
                    alert('succ');
                }
            });
        });

        $("#newValueBtn").click(function () {
            var key = $("#string-key-id").val();
            refreshStringValue(key);
            refreshStringTtl(key);
        });

    });
})(jQuery);
