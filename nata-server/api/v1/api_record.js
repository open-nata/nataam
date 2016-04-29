module.exports = function() {

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

    var remove = function(req, res, next) {
        var record_id = req.params.id;
        RecordModel.findOneAndRemove({
            _id: record_id
        }, function(err, record) {
            if (err || !record) {
                return res.status(500).json();
            }
            res.status(200).json(record);
        });
    };

    var start = function(req, res, next) {
        var record_id = req.params.id;
        RecordModel.findOne({
            _id: record_id
        }, function(err, record) {
            if (err || !record) {
                return res.status(500).json();
            }

            record.status = "running";
            record.save(function(err) {
                if (err) return next(err);
                res.status(200).send("success");
            });
        });
    };

    var cancel = function(req, res, next) {
        var record_id = req.params.id;
        RecordModel.findOne({
            _id: record_id
        }, function(err, record) {
            if (err || !record) {
                return res.status(500).json();
            }

            record.status = "failure";
            record.save(function(err) {
                if (err) return next(err);
                res.status(200).send("success");
            });
        });
    };

    var finish = function(req, res, next) {
        var record_id = req.params.id;
        RecordModel.findOne({
            _id: record_id
        }, function(err, record) {
            if (err || !record) {
                return res.status(500).json();
            }

            record.status = "success";
            record.save(function(err) {
                if (err) return next(err);
                res.status(200).send("success");
            });
        });
    };

    var summary = function(req, res, next) {
        var record_id = req.params.id;
        var action = parseInt(req.body.action, 10);
        var widget = parseInt(req.body.widget, 10);
        var state = parseInt(req.body.state, 10);
        var activity = parseInt(req.body.activity, 10);
        var data = {
            widget: widget,
            action: action,
            activity: activity,
            state: state
        };

        // io.sockets.emit("summary", data);

        RecordModel.findOne({
            _id: record_id
        }, function(err, record) {
            if (err || !record) {
                return res.status(500).json();
            }

            record.summaries.push(data);
            record.save(function(err) {
                if (err) return next(err);
                res.status(200).send("success");
            });
        });
    }

    var activity = function(req, res, next) {
        var record_id = req.params.id;
        var activity = req.body.message;

        // io.sockets.emit("activity", activity);

        RecordModel.findOne({
            _id: record_id
        }, function(err, record) {
            if (err || !record) {
                return res.status(500).json();
            }

            record.activities.push(activity);
            record.save(function(err) {
                if (err) return next(err);
                res.status(200).send("success");
            });
        });
    };

    var widget = function(req, res, next) {
        var record_id = req.params.id;
        var widget = req.body.message;

        // io.sockets.emit("widget", widget);

        RecordModel.findOne({
            _id: record_id
        }, function(err, record) {
            if (err || !record) {
                return res.status(500).json();
            }

            record.widgets.push(widget);
            record.save(function(err) {
                if (err) return next(err);
                res.status(200).send("success");
            });
        });
    };

    var action = function(req, res, next) {
        var record_id = req.params.id;
        var action = req.body.message;
        // io.sockets.emit("action", action);

        RecordModel.findOne({
            _id: record_id
        }, function(err, record) {
            if (err || !record) {
                return res.status(500).json();
            }

            record.actions.push(action);
            record.save(function(err) {
                if (err) return next(err);
                res.status(200).send("success");
            });
        });
    };

    var state = function(req, res, next) {
        var record_id = req.params.id;
        var state = req.body.message;

        // io.sockets.emit("state", state);

        RecordModel.findOne({
            _id: record_id
        }, function(err, record) {
            if (err || !record) {
                return res.status(500).json();
            }

            record.states.push(state);
            record.save(function(err) {
                if (err) return next(err);
                res.status(200).send("success");
            });
        });
    };

    return {
        create: create,
        start: start,
        cancel: cancel,
        finish: finish,
        remove: remove,
        summary: summary,
        activity: activity,
        widget: widget,
        action: action,
        state: state
    }

}