$(function() {

  $('#btn-create').click(function () {
    var config = $('#config').serialize();
    var device_id = $('#device-id').text().trim();
    if (!device_id) {
      alert("请先获取设备信息");
      return false;
    }
    config += "&device_id=" + device_id;
    console.log(config);

    $.ajax({
      url: "api/v1/records",
      data: config,
      type: 'POST',
      success: function (record) {
        console.log(record);
        window.location.href = "/records";
        return false;
      },
      error: function (request) {
        // console.log("创建设备失败");
        return false;
      }
    });
  });
});
