module.exports = function(io) {

    var RecordModel = require('../../models/model_record.js');

    var create = function(req, res, next) {
        var record = new RecordModel();
        record.device_id = req.body.device_id;
        record.app_name = req.body.app_name;
        record.package_name = req.body.package_name;
        record.activity_name = req.body.activity_name;
        record.action_count = req.body.action_count;
        record.algorithm = req.body.algorithm;
        record.status = "ready";

        record.save(function(err, record) {
            if (err) {
                return next(err);
            } else {
                res.status(200).json(record);
            }
        });
    };


    var summary = function(req, res, next) {
        var record_id = req.params.id;

        var action = parseInt(req.body.action, 10);
        var widget = parseInt(req.body.widget, 10);
        var state = parseInt(req.body.state, 10);
        var activity = parseInt(req.body.activity, 10);

        console.log("action : " + action +
            "widget : " + widget +
            "state  : " + state +
            "activity : " + activity);
        
        var data = { "widget": widget, "action": action, "activity": activity };
        io.sockets.emit("summary", data);

        res.send({});
    }

    var activity = function(req, res, next) {
        var message = req.body.message;
        io.sockets.emit("activity", message);
        res.send({});
    };

    var widget = function(req, res, next) {
        var message = req.body.message;
        io.sockets.emit("widget", message);
        res.send({});
    };

    var action = function(req, res, next) {
        var message = req.body.message;
        io.sockets.emit("action", message);
        res.send({});
    };

    var state = function(req, res, next) {
        var message = req.body.message;
        io.sockets.emit("state", message);
        res.send({});
    };

    return {
        create: create,
        summary: summary,
        activity: activity,
        widget: widget,
        action: action,
        state: state
    }

}
