$(function() {
    var baseUrl = "http://localhost:9001";

    $('.btn-start').on('click', function(e) {
        e.preventDefault();
        var config = {};
        config.record_id = $(this).data('id');
        config.device_id = $(this).data('device');
        config.action_count = $(this).data('action');
        config.package_name = $(this).data('package');
        config.activity_name = $(this).data('activity');
        config.algorithm = $(this).data('algorithm');
        config.app_name = $(this).data('name');


        $.ajax({
            url: baseUrl + "/device/online",
            data: { "device_id": config.device_id },
            type: 'GET',
            success: function(message) {
                console.log("设备在线");
                // 设置 record的状态为running
                $.ajax({
                    url: baseUrl + '/start',
                    type: 'POST',
                    data: config,
                    success: function() {
                        console.log("success");
                        window.location.href = "/records/" + config.record_id;
                        return false;
                    },
                    error: function(request) {
                        // alert(request.status);
                        console.log("error");
                        if (request.status === 406)
                            alert("有测试任务正在运行");
                        else {
                            alert("创建测试任务失败");
                        }
                        return false;
                    }
                });
               

            },
            error: function(request) {
                alert("设备不在线");
                return false;
                // console.log("创建设备失败");
            }
        });
    });
});
