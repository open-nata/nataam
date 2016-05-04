$(function () {
  var baseUrl = "http://localhost:9001";

  var ActionType = {
    UNKNOWN: "Unknown",
    TAP: "Tap",
    LONG_CLICK: "LongClick",
    SWIPE: "Swipe",
    INPUT: "TextInput",
    BACK: "Back",
    MENU: "Menu",
    HOME: "Home",
    START_APP: "StartApp",
    CLEAN_DATA: "CleanData"
  }

  $('#btn-getactions').click(function (e) {
    e.preventDefault();

    $.ajax({
      url: baseUrl + "/actions",
      type: 'GET',
      success: function (message) {
        var actions = message.trim().split('\n');
        var btnGroupString = '<div class="btn-group btn-group-justified" role="group" aria-label="...">';
        var toAppend = btnGroupString;

        for (var i = 1; i <= actions.length; i++) {
          var params = actions[i-1].split(" ", 5);
          var actionType = params[0];
          var at = params[1];

          toAppend+='<div class="btn-group" role="group"><button type="button" class="btn btn-primary" style="border-radius : 0"' +
            ' data-action='+actionType +
            ' data-at=' + at +
            ' data-x='
            +'>'+actionType+'</button></div>';
          if(i%5 == 0 && i !== actions.length){
            toAppend +='</div>' + btnGroupString;
          }


        //  toAppend += "<td>" + actionType + "</td>";
        //  switch (actionType) {
        //    case ActionType.BACK :
        //    case ActionType.HOME :
        //    case ActionType.MENU :
        //      toAppend +="<td></td><td></td>";
        //      break;
        //    case ActionType.CLEAN_DATA :
        //      //var pkgAct = params[1];
        //      //toAppend +="<td></td><td>"+pkgAct + "</td>";
        //      //break;
        //    case ActionType.START_APP :
        //      var pkgAct = params[1];
        //      toAppend +="<td></td><td>"+pkgAct + "</td>";
        //      break;
        //    case ActionType.LONG_CLICK :
        //      //var xy = params[1].split(" ",2);
        //      //toAppend +="<td>"+ xy[0] + "</td><td>"+ xy[1]+ "</td>";
        //      //break;
        //    case ActionType.TAP:
        //    case ActionType.SWIPE:
        //      toAppend +="<td>"+ params[1] + "</td><td>"+ params[2]+ "</td>";
        //      break;
        //    case ActionType.INPUT:
        //      var xy = params[1].split(" ",2);
        //      toAppend +="<td>"+ params[1] + "</td><td>"+ params[2]+params[3]+ "</td>";
        //  }
        //  toAppend += "<td>"+"执行"+"</td></tr>";
        }
        toAppend += "</div>";
        console.log(toAppend);
        $("#action-panel").empty().append(toAppend);
      },
      error: function () {

      }
    });

  });

  $('#list-actions').delegate('tr td:nth-child(2)', 'mouseover', function (e) {
    var at= e.target.innerHTML;
    var canvas = document.getElementById('canvas-device');
    var canvasWrapper = document.getElementById('canvas-wrapper');
    canvasWrapper.width = canvas.width;
    canvasWrapper.height = canvas.height;
    var g = canvasWrapper.getContext("2d");

    if (at !== undefined) {
      //console.log(at);
      var coodinates = at.substr(1).split(/[,x]/);
      var startX = parseInt(coodinates[0], 10) / 3;
      var startY = parseInt(coodinates[1], 10) / 3;
      var endX = parseInt(coodinates[2], 10) / 3;
      var endY = parseInt(coodinates[3], 10) / 3;

      g.lineWidth = 3;
      g.strokeStyle = "red";
      g.strokeRect(startX, startY, endX - startX, endY - startY);
    }
    else {
      g.clearRect(0, 0, canvasWrapper.width, canvasWrapper.height);
    }
  });

});

