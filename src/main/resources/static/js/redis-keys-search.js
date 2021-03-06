(function ($) {
    'use strict';

    $(function () {

        $("#keyPattern").keypress(function (e) {
            var key = e.which;
            if (key == 13) {
                $("#keyPatternBtn").click();
                return false;
            }
        });

        $("#keyPatternBtn").click(function (event) {
            event.preventDefault();
            var keyPattern = $("#keyPattern").val();
            $.getJSON("/keyInfoList", {"term": keyPattern}, function (data) {
                $("#keyInfoTable tbody").empty();
                $.each(data, function (index, keyInfo) {
                    var tr = $("<tr/>");
                    tr.append("<td>" + keyInfo.key + "</td>");
                    tr.append("<td>" + keyInfo.dataType + "</td>");
                    tr.append("<td>" + keyInfo.ttl + "</td>");
                    $("#keyInfoTable tbody").append(tr);
                });
            });
        });

        $("#keyInfoTable tbody").on("click", "tr", function () {
            var key = $("td:first-child", $(this)).text();
            var type = $("td:nth-child(2)", $(this)).text();
            var url = "/redis-" + type + ".html?key=" + key;
            window.location.href = url;
        });

    });
})(jQuery);
