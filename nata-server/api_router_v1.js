var express = require('express');
var router = express.Router();

var deviceApi= require('./api/v1/api_device.js');
var recordApi= require('./api/v1/api_record.js');

router.post('/devices',deviceApi.create);
router.post('/records',recordApi.create);

module.exports = router;
