module.exports = function() {
    var express = require('express');
    var router = express.Router();

    var deviceApi = require('./api/v1/api_device.js');
    var recordApi = require('./api/v1/api_record.js')();

    router.post('/devices', deviceApi.create);
    router.post('/records', recordApi.create);
    router.post("/records/:id/summary", recordApi.summary);
    router.post('/records/:id/activity', recordApi.activity);
    router.post('/records/:id/widget', recordApi.widget);
    router.post('/records/:id/action', recordApi.action);
    router.post('/records/:id/state', recordApi.state);

    router.put('/records/:id/start', recordApi.start);
    router.put('/records/:id/cancel', recordApi.cancel);
    router.put('/records/:id/finish', recordApi.finish);
    router.delete('/records/:id', recordApi.remove);

    return router;
};