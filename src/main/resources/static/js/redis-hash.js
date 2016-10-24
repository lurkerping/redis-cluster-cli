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

        $("#hashValueForm").submit(function (event) {
            event.preventDefault();
            $.post("/hash/set", {
                key: $("#hash-key-id").val(),
                hashKey: "name",
                value:"xyz"
            }, function (data) {
                if(data.retCode == 'succ'){
                    alert('succ');
                }
            });
        });


        $("#queryBtn").click(function (event) {
            event.preventDefault();
            var key = $("#hash-key-id").val();
            refreshHashValue(key);
            refreshStringTtl(key);
        })

    });
})(jQuery);
