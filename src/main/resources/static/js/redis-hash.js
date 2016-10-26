(function ($) {
    'use strict';

    $(function () {

        function refreshHashValue(key){
            $.getJSON("/hash/get", {key:key}, function (data) {
                $("#entriesTable tbody").empty();
                $.each(data, function (k, v) {
                    var tr = $("<tr/>");
                    tr.append("<td>" + k + "</td>");
                    tr.append("<td>" + v + "</td>");
                    $("#entriesTable tbody").append(tr);
                });
            });
        }

        function refreshStringTtl(key) {
            $.get(
                "/hash/getTtl",
                {
                    keyName: key
                },
                function (data) {
                    $("#hash-ttl-id").val(data);
                }
            );
        }

        var key = $.getParam("key");
        if (key != null && key.length > 0) {
            $("#hash-key-id").val(key);
            refreshHashValue(key);
            refreshStringTtl(key);
        }

        $("#queryBtn").click(function (event) {
            event.preventDefault();
            var key = $("#hash-key-id").val();
            refreshHashValue(key);
            refreshStringTtl(key);
        });

        $("#entriesTable tbody").on("click", "tr td:last-child", function () {
            var td = $(this);
            if($("input", td).length > 0){
                return;
            }
            var value = td.text();
            td.text("");// clear
            td.append("<input type='text'>");
            $("input", td).val(value);
        });

        $("#entriesTable tbody").on("blur", "tr input", function () {
            var input = $(this);
            var value = input.val();
            var name = $("td:first", input.parent().parent()).text();
            $.post("/hash/set", {
                key: $("#hash-key-id").val(),
                hashKey: name,
                value:value
            }, function (data) {
                if(data.retCode == 'succ'){
                    input.parent().text(value);
                }
            });
        });

    });
})(jQuery);
