$(function () {
  var baseUrl = "http://localhost:9001";
  $('#btn-create').on('click', function (e) {
    e.preventDefault();
    var record = $('#record-form').serialize();

    $.ajax({
      url: "/api/v1/records",
      data: record,
      type: 'POST',
      success: function (record) {
        //var toAppend = "<tr>";
        //toAppend += "<td>" + record.device_id+ "</td>";
        //toAppend += "<td>" + record.apk_name+ "</td>";
        //toAppend += "<td>" + record.action_count+ "</td>";
        //toAppend += "<td>" + record.algorithm+ "</td>";
        //toAppend += "<td>" + record.status+ "</td>";
        //
        //toAppend += '<td><a href="#" class="btn-start btn btn-primary btn-xs" role="button"' +
        //  ' data-id=' + record.id +
        //  ' data-device=' + record.device_id +
        //  ' data-action=' + record.action_count +
        //  ' data-package=' + record.package_name +
        //  ' data-activity=' + record.activity_name +
        //  ' data-algorithm=' + record.algorithm +
        //  ' data-name=' + record.app_name +'>开始</a></td><tr>';
        //
        //toAppend += '<td><a href="#" class="btn-remove btn btn-danger btn-xs" role="button" data-id=' +record._id + '>删除' + '</a></td></tr>';
        //
        //$('#record-table').append(toAppend);
        location.reload();
      },
      error: function (request) {
        alert("创建任务失败");
      }
    });

  });

  $('#setup').on('click',function(e) {
    e.preventDefault();
    var apk_id = $('#apk').val();
    if(!apk_id){
      return ;
    }
    $.ajax({
      url: '/api/v1/apks/'+apk_id + '/testcases',
      type: 'GET',
      success: function (testcases) {
        var toAppend = '<option value="">无</option>';
        for(var i = 0 ; i < testcases.length ;i++){
          toAppend += '<option value="' + testcases[i]._id +'" >' + testcases[i].name +'</option>';
        }
        $('#setup').empty().append(toAppend);
      },
      error: function (message) {
        alert('获取失败')
      }
    });
  });

  $('.btn-start').on('click', function (e) {
    e.preventDefault();
    var config = {};
    config.record_id = $(this).data('id');
    config.device_id = $(this).data('device');
    config.action_count = $(this).data('action');
    config.package_name = $(this).data('package');
    config.activity_name = $(this).data('activity');
    config.algorithm = $(this).data('algorithm');
    config.app_name = $(this).data('name');
    config.setup = $(this).data('setup');
    config.blacklist = $(this).data('blacklist');


    $.ajax({
      url: baseUrl + "/device/online",
      data: {"device_id": config.device_id},
      type: 'GET',
      success: function (message) {
        console.log("设备在线");
        $.ajax({
          url: '/api/v1/records/' + config.record_id + '/start',
          type: 'PUT',
          success: function (message) {
            console.log("进入running状态");
            $.ajax({
              url: baseUrl + '/start',
              type: 'POST',
              data: config,
              success: function () {
                window.location.href = "/records/" + config.record_id + "/run";
                return false;
              },
              error: function (request) {
                if (request.status === 406)
                  alert("有测试任务正在运行");
                else {
                  alert("启动测试任务失败");
                }
                return false;
              }
            });
          },
          error: function (message) {
            console.log(message);
            alert("设置测试任务状态失败");
            return false;
          }
        });
      },
      error: function (request) {
        alert("出错,服务器未启动或设备不在线");
        return false;
      }
    });
  });

  $('.btn-remove').on('click', function (e) {
    e.preventDefault();
    var record_id = $(this).data('id');
    $.ajax({
      url: '/api/v1/records/' + record_id,
      type: 'DELETE',
      success: function (message) {
        location.reload();
      },
      error: function (message) {
        alert("删除失败");
      }
    });

  });

  $('.btn-cancel').on('click', function (e) {
    e.preventDefault();
    var record_id = $(this).data('id');
    $.ajax({
      url: '/api/v1/records/' + record_id + "/cancel",
      type: 'PUT',
      success: function (message) {
        $.ajax({
          url: baseUrl + '/stop',
          type: 'POST',
          success: function () {
            location.reload();
          },
          error: function (request) {
            alert("取消任务失败");
          }
        });
      },
      error: function (message) {
        alert("设置任务状态失败");
      }
    });

  });

});
