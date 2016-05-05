$(function() {
  var baseUrl = "http://localhost:9001";
  var deviceContaner = {};
  $('#btn-getdevice').click(function () {
    $.ajax({
      url: baseUrl + '/device',
      type: 'GET',
      success: function (device) {
        deviceContaner = device;
        // console.log(JSON.stringify(device));
        $('#device-name').text(device.name);
        $('#device-id').text(device.id);
        $('#android-version').text(device.version);
        $('#sdk-version').text(device.sdk);
        $('#resolution').text(device.resolution);
        $('#cpu-abi').text(device.cpu);
        $('#manufacturer').text(device.manufacturer);
        $('#status').removeClass("label-danger label-primary").addClass("label-success").text("获取成功");
      },
      error: function (request) {
        $('#status').removeClass("label-success label-primary").addClass("label-danger").text("获取失败");
      }
    });
  });

  $('#btn-logdevice').click(function () {
    if($('#status').hasClass("label-success")){

      $.ajax({
        url: "api/v1/devices",
        data: deviceContaner,
        type: 'POST',
        success: function (device) {
          //alert("登记设备信息成功");
          var toAppend = "<tr>";
          toAppend += "<td>" + device.id + "</td>";
          toAppend += "<td>" + device.name + "</td>";
          toAppend += "<td>" + device.android_version + "</td>";
          toAppend += "<td>" + device.sdk_version + "</td>";
          toAppend += "<td>" + device.resolution + "</td>";
          toAppend += "<td>" + device.cpu_abi + "</td>";
          toAppend += "<td>" + device.manufacturer + "</td>";
          toAppend += '<td><a href="#" class="btn-remove btn btn-danger btn-xs" role="button" data-id=' +device.id + '>删除' + '</a></td></tr>';
          $('#device_table').append(toAppend);
        },
        error: function (request) {
           alert("登记设备信息失败,请检查设备是否已存在");
        }
      });
    }else{
      alert("请先获取设备信息");
    }

  });

  $('#device_table').delegate('a','click', function(e) {
    e.preventDefault();
    var device_id = $(this).data('id');
    $.ajax({
      url: '/api/v1/devices/' + device_id,
      type: 'DELETE',
      success: function(message) {
        $(e.target).parent().parent().remove();
      },
      error: function(message) {
        alert("删除失败");
      }
    });

  });


});