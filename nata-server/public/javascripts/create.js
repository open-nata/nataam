var baseUrl = "http://localhost:9001";
$('#btn-device').click(function() {
    $.ajax({
        url: baseUrl + '/device',
        type: 'GET',
        success: function(device) {

            $.ajax({
                url: "api/v1/devices",
                data: device,
                type: 'POST',
                success: function(message) {
                    console.log("创建设备成功");
                },
                error: function(request) {
                    // console.log("创建设备失败");
                }
            });

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
        error: function(request) {
            $('#status').removeClass("label-success label-primary").addClass("label-danger").text("获取失败");
        }
    });
});

$('#btn-create').click(function() {
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
        success: function(record) {
            console.log(record);
            window.location.href = "/records";
            return false;
        },
        error: function(request) {
            // console.log("创建设备失败");
            return false;
        }
    });
});
