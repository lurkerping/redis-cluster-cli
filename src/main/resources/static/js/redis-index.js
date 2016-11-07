(function ($) {
    'use strict';

    $(function () {

        function formatMySlotRangeList(mySlotRangeList) {
            var result = "";
            $.each(mySlotRangeList, function (index, mySlotRange) {
                result += "[" + mySlotRange.start + "-" + mySlotRange.end + "]";
                if (index < mySlotRangeList.length - 1) {
                    result += "<br>";
                }
            });
            return result;
        }

        function loadNodes() {
            $.getJSON("/nodes", function (data) {
                $("#nodesTable tbody").empty();
                $.each(data, function (index, myNode) {
                    var tr = $("<tr/>");
                    tr.append("<td>" + myNode.type + "</td>");
                    tr.append("<td>" + myNode.id + "</td>");
                    tr.append("<td>" + myNode.host + "</td>");
                    tr.append("<td>" + myNode.port + "</td>");
                    tr.append("<td>" + formatMySlotRangeList(myNode.mySlotRangeList) + "</td>");
                    tr.append("<td>" + myNode.linkState + "</td>");
                    tr.append("<td>" + myNode.flags + "</td>");
                    $("#nodesTable tbody").append(tr);
                });
            });
        }

        loadNodes();

        $("#hostInfoBtn").click(function (event) {
            event.preventDefault();
            var hostInfo = $("#hostInfo").val();
            $.post("/home/connect", {node: hostInfo}, function (data) {
                if (data.retCode == "succ") {
                    loadNodes();
                }
                alert(data.retMsg);
            })
        });

    });
})(jQuery);
