var RecordModel = require('../../models/model_record.js');

module.exports.create = function(req, res, next) {
    var device = new RecordModel();
    device.device = req.body.id;
    device.name = req.body.name;
    device.android_version = req.body.version;
    device.sdk_version = req.body.sdk;
    device.resolution = req.body.resolution;
    device.cpu_abi = req.body.cpu;
    device.manufacturer = req.body.manufacturer;

    device.save(function(err, device) {
        if (err) {
            return next(err);
        } else {
            res.status(200).json(device);
        }
    });
};
