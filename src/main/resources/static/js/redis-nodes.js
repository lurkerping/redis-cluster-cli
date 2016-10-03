(function ($) {
    'use strict';

    $(function () {

        function formatMySlotRangeList(mySlotRangeList) {
            var result = "";
            $.each(mySlotRangeList, function (index, mySlotRange) {
                result += "[" + mySlotRange.start + "-" + mySlotRange.end + "]";
                if(index < mySlotRangeList.length - 1){
                    result += "<br>";
                }
            });
            return result;
        }

        $.getJSON("/nodes", function (data) {
            $.each(data, function (index, myNode) {
                var tr = $("<tr/>");
                tr.append("<td>" + myNode.node.type + "</td>");
                tr.append("<td>" + myNode.node.id + "</td>");
                tr.append("<td>" + myNode.node.host + "</td>");
                tr.append("<td>" + myNode.node.port + "</td>");
                tr.append("<td>" + formatMySlotRangeList(myNode.mySlotRangeList) + "</td>");
                tr.append("<td>" + myNode.node.linkState + "</td>");
                tr.append("<td>" + myNode.node.flags + "</td>");
                $("#nodesTable tbody").append(tr);
            });
        });

    });
})(jQuery);
