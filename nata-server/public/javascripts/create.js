
var baseUrl = "http://localhost:9001";
$('#btn-device').click(function(){
    $.ajax({
        url: baseUrl+'/device',
        type: 'GET',
        success: function (device) {

             $.ajax({
                url: "api/v1/devices",
                data: device,
                type: 'POST',
                success : function(message){
                    console.log("创建设备成功");
                },
                error : function(request){
                    console.log("创建设备失败");
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
        error: function (request) {
            $('#status').removeClass("label-success label-primary").addClass("label-danger").text("获取失败");
        }
    });
});

$('#btn-create').click(function(){
    $.ajax({
        url: '/test',
        type: 'POST',
        success: function (task) {

        },
        error: function (request) {
        }
    });
});