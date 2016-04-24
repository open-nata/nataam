var RecordModel = require('../../models/model_record.js');

module.exports.create = function(req, res, next) {
    
    var record = new RecordModel();
    record.device_id = req.body.device_id;
    record.app_name = req.body.app_name;
    record.package_name = req.body.package_name;
    record.activity_name = req.body.activity_name;
    record.action_count = req.body.action_count;
    record.algorithm = req.body.algorithm;
    record.status = "running";

    record.save(function(err, record) {
        if (err) {
            return next(err);
        } else {
            res.status(200).json(record);
        }
    });
};
