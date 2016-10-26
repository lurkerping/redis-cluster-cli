(function ($) {
    'use strict';

    $(function () {

        $("#hostInfoBtn").click(function (event) {
            event.preventDefault();
            var hostInfo = $("#hostInfo").val();
            $.post("/home/connect", {node: hostInfo}, function (data) {
                alert(data.retMsg);
            })
        });

    });
})(jQuery);
