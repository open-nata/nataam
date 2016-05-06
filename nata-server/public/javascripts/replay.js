var actionCount = 0;
var baseUrl = "http://localhost:9001";
var replayBtn = $('#btn-replay');

replayBtn.on('click', function(e) {
    var tdLast = $('#actions tr:nth-child(' + (actionCount) + ') td');
    var td = $('#actions tr:nth-child(' + (actionCount + 1) + ') td');
    var action = td.text();
    replayBtn.prop("disabled", true);
    $.ajax({
        url: baseUrl + "/action",
        data: {
            "action": action
        },
        type: 'POST',
        success: function(actions) {
            tdLast.css('background-color', '#fff');
            td.css('background-color', '#44ba92');
            var scrollBottom = td.parent().height() * (actionCount);
            $('#action-table-wrapper').scrollTop(scrollBottom);
            actionCount++;
            replayBtn.prop("disabled", false);
        },
        error: function(error) {
            replayBtn.prop("disabled", false);
        }
    });
});