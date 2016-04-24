var express = require('express');
var router = express.Router();

var deviceApi= require('./api/v1/api_device');

router.post('/devices',deviceApi.create);

module.exports = router;
