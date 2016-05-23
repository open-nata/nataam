module.exports = function () {
  var express = require('express');
  var router = express.Router();

  var deviceApi = require('./api/v1/api_device.js');
  var apkApi = require('./api/v1/api_apk.js');
  var testcaseApi = require('./api/v1/api_testcase.js');
  var recordApi = require('./api/v1/api_record.js')();

  router.post('/apks', apkApi.create);
  router.delete('/apks/:id', apkApi.remove);
  router.put('/apks/:id', apkApi.update);


  router.get('/apks/:id/testcases', apkApi.testcases);


  router.post('/apks/:package/actpath', apkApi.actpath);
  router.delete('/apks/:id/:actpath', apkApi.removeActpath);


  router.post('/apks/:id/blacklist',apkApi.addBlacklist);
  router.delete('/apks/:id/blacklist',apkApi.removeBlacklist);


  router.get('/apks/:id/:actpath', apkApi.getactions);



  router.post('/devices', deviceApi.create);
  router.delete('/devices/:id', deviceApi.remove);

  router.post('/testcases', testcaseApi.create);
  router.delete('/testcases/:id', testcaseApi.remove);
  router.get('/testcases/:id', testcaseApi.getactions);
  router.put('/testcases/:id/finish', testcaseApi.finish);
  router.put('/testcases/:id/save', testcaseApi.save);

  router.post('/records', recordApi.create);
  router.post("/records/:id/summary", recordApi.summary);
  router.get("/records/:id/summary", recordApi.getSummary);

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