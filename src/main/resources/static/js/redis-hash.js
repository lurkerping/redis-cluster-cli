(function ($) {
    'use strict';

    $(function () {

        var key = $.getParam("key");
        if (key != null && key.length > 0) {
            $("#hash-key-id").val(key);
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
            $.getJSON("/hash/get", {key:$("#hash-key-id").val()}, function (data) {
                $("#entriesTable tbody").empty();
                $.each(data, function (k, v) {
                    var tr = $("<tr/>");
                    tr.append("<td>" + k + "</td>");
                    tr.append("<td>" + v + "</td>");
                    $("#entriesTable tbody").append(tr);
                });
            });
        })

    });
})(jQuery);
