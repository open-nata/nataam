$(function () {
  var baseUrl = "http://localhost:9001";
  var startRecrod = true;

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
  var listString = '<li class="list-group-item">';

  $('#btn-clear').click(function (e) {
    e.preventDefault();
    $('#testcase').empty();
  });

  $('#btn-clear-last').click(function (e) {
    e.preventDefault();
    $('#testcase li:last-child').remove();
  });

  $('.actpath').click(function(e){
    e.preventDefault();
    var act_name = $(this).data("activity");
    if(!act_name){
      return false;
    }

    var apk_id = $(this).data("id");

    var ele = $(this);

    $.ajax({
      url: "/api/v1/apks/" + apk_id + "/" + act_name,
      type: 'GET',
      success: function (data) {

        $.ajax({
          url: baseUrl + "/actions",
          type: 'POST',
          data: {"actions": data},
          success: function () {
            var toAppend = "";
            var actions = data.trim().split("\n");
            for(var i = 0 ; i< actions.length;i++){
              toAppend += listString + actions[i] + "</li>"
            }
            if (startRecrod)$('#testcase').append(toAppend);
            ele.prop("disabled", false);

          },
          error: function () {
            ele.prop("disabled", false);
          }
        });
      },
      error: function () {
        ele.prop("disabled", false);
      }
    });

  });

  $('.testcase').click(function(e){
    e.preventDefault();
    var testcase_id= $(this).data("id");
    if(!testcase_id){
      return false;
    }
    var ele = $(this);

    $.ajax({
      url: "/api/v1/testcases/" + testcase_id,
      type: 'GET',
      success: function (data) {
        $.ajax({
          url: baseUrl + "/actions",
          type: 'POST',
          data: {"actions": data},
          success: function () {
            var toAppend = "";
            var actions = data.trim().split("\n");
            for(var i = 0 ; i< actions.length;i++){
              toAppend += listString + actions[i] + "</li>"
            }
            if (startRecrod)$('#testcase').append(toAppend);
            ele.prop("disabled", false);

          },
          error: function () {
            ele.prop("disabled", false);
          }
        });
      },
      error: function () {
        ele.prop("disabled", false);
      }
    });

  });


  $('#btn-back').click(function (e) {
    var ele = $(this);
    ele.prop("disabled", true);
    var action = ActionType.BACK;

    $.ajax({
      url: baseUrl + "/action",
      type: 'POST',
      data: {"action": action},
      success: function (message) {
        if (startRecrod)$('#testcase').append(listString + action + "</li>");
        ele.prop("disabled", false);

      },
      error: function () {
        ele.prop("disabled", false);
      }
    });
  });

  $('#btn-home').click(function (e) {
    var action = ActionType.HOME;
    var ele = $(this);
    ele.prop("disabled", true);

    $.ajax({
      url: baseUrl + "/action",
      type: 'POST',
      data: {"action": action},
      success: function (message) {
        if (startRecrod) $('#testcase').append(listString + action + "</li>");
        ele.prop("disabled", false);
      },
      error: function () {
        ele.prop("disabled", false);
      }
    });
  });

  $('#btn-menu').click(function (e) {
    var action = ActionType.MENU;
    var ele = $(this);
    ele.prop("disabled", true);

    $.ajax({
      url: baseUrl + "/action",
      type: 'POST',
      data: {"action": action},
      success: function (message) {
        if (startRecrod) $('#testcase').append(listString + action + "</li>");
        ele.prop("disabled", false);
      },
      error: function () {
        ele.prop("disabled", false);
      }
    });
  });

  $('#btn-restart').click(function (e) {
    var ele = $(this);
    ele.prop("disabled", true);
    var action = ActionType.START_APP + " " + apk.package_name + "/" + apk.activity_name;

    $.ajax({
      url: baseUrl + "/action",
      type: 'POST',
      data: {"action": action},
      success: function (message) {
        if (startRecrod) $('#testcase').append(listString + action + "</li>");
        ele.prop("disabled", false);
      },
      error: function () {
        ele.prop("disabled", false);
      }
    });
  });

  $('#btn-cleandata').click(function (e) {
    var ele = $(this);
    ele.prop("disabled", true);
    var action = ActionType.CLEAN_DATA + " " + apk.package_name;

    $.ajax({
      url: baseUrl + "/action",
      type: 'POST',
      data: {"action": action},
      success: function (message) {
        if (startRecrod) $('#testcase').append(listString + action + "</li>");
        ele.prop("disabled", false);
      },
      error: function () {
        ele.prop("disabled", false);
      }
    });
  });

  $('#btn-stop').click(function(e){
    e.preventDefault();
    var pauseSpan = '<span class="icon ion-pause"> 暂停录制</span>';
    var playSpan = '<span class="icon ion-play"> 继续录制</span>';


    if (!startRecrod) {
      $(this).empty().append(pauseSpan);
      $('#rec-span').show();

      startRecrod = true;
      return;
    }else{
      $(this).empty().append(playSpan);
      $('#rec-span').hide();
      startRecrod = false;
      return;
    }
  });

  $('#btn-cancel').click(function(e){
    e.preventDefault();
    parent.history.back();
    return false;
  });

  $('#btn-finish').click(function (e) {
    e.preventDefault();

    var testcase_id = $(this).data("id");
    var actions = [];
    $("#testcase li").each(function () {
      actions.push($(this).text())
    });
    if (actions.length === 0) {
      alert("没有动作");
      return;
    }
    console.log(actions);

    $.ajax({
      url: "/api/v1/testcases/" + testcase_id + "/finish",
      type: 'PUT',
      data: {"actions": actions},
      traditional: true,
      success: function (message) {
        window.location.href = '/testcases';
      },
      error: function () {

      }
    });
  });

  $('#btn-getactions').click(function (e) {
    e.preventDefault();
    var ele = $(this);
    ele.text("获取中...");
    ele.prop("disabled", true);
    $.ajax({
      url: baseUrl + "/actions",
      type: 'GET',
      success: function (message) {
        var actions = message.trim().split('\n');
        var btnGroupString = '<div class="btn-group btn-group-justified" role="group" aria-label="...">';
        var toAppend = btnGroupString;

        for (var i = 1; i <= actions.length; i++) {
          var params = actions[i - 1].split(" ", 5);
          var actionType = params[0];
          var at = params[1];
          var x = params[2];
          var y = params[3];
          var direction = params[2];
          var action = actionType;
          if (actionType === ActionType.SWIPE) action += direction;

          toAppend += '<div class="btn-group" role="group"><button type="button" class="btn btn-primary" style="border-radius : 0"' +
            ' data-action=' + actionType +
            ' data-at=' + at +
            ' data-x=' + x +
            ' data-y=' + y +
            ' data-direction=' + direction +
            '>' + action + '</button></div>';

          if (i % 5 == 0 && i !== actions.length) {
            toAppend += '</div>' + btnGroupString;
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
        ele.prop("disabled", false);
        ele.text("获取界面动作");
      },
      error: function () {
        ele.prop("disabled", false);
        ele.text("获取界面动作");
      }
    });

  });

  $('#action-panel').delegate('button', 'mouseover', function (e) {
    var at = $(e.target).data("at");
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

  $('#action-panel').delegate('button', 'click', function (e) {
    var ele = $(e.target);
    ele.prop("disabled", true);
    var actionType = ele.data("action");
    var at = ele.data("at");
    var x = ele.data("x");
    var y = ele.data("y");
    var direction = ele.data("direction");
    var action = actionType + " " + at;
    switch (actionType) {
      case ActionType.TAP:
      case ActionType.LONG_CLICK:
        action += " " + x + " " + y;
        break;
      case ActionType.INPUT:
        var textinput =prompt("请输入文本内容","")

        if (!textinput) {
          alert("请填写输入内容");
          ele.prop("disabled", false);
          return;
        }
        action += " " + x + " " + y + " " + textinput;
        //$('#textinput').val("");
        break;
      case ActionType.SWIPE:
        action += " " + direction;
        break;
    }

    $.ajax({
      url: baseUrl + "/action",
      type: 'POST',
      data: {"action": action},
      success: function (message) {
        if (startRecrod)  $('#testcase').append(listString + action + "</li>");
        ele.prop("disabled", false);
      },
      error: function () {
        ele.prop("disabled", false);
      }
    });
    //console.log(action);
  });

});

