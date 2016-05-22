module.exports = function () {
  var express = require('express');
  var router = express.Router();
  var ApkController = require('./controllers/controller_apk.js');
  var DeviceController = require('./controllers/controller_device.js');
  var RecordController = require('./controllers/controller_record.js');
  var TestcaseController = require('./controllers/controller_testcase.js');
  var GroupController = require('./controllers/controller_group.js');


  /* GET home page. */
  router.get('/', function (req, res, next) {
    res.render('index', {
      title: '首页'
    });
  });

  /**
   * devices operations
   */
  router.get('/devices',DeviceController.show);

  /**
   * apks operations
   */
  router.get('/apks',ApkController.show);
  router.get('/apks/:id/detail', ApkController.detail);
  router.get('/apks/:id/replay/:act_name', ApkController.replay);


  /**
   * records operations
   */
  router.get('/records',RecordController.show);
  router.get('/records/:id/run', RecordController.run);
  router.get('/records/:id/replay',RecordController.replay);
  router.get('/records/:id/report',RecordController.report);

  /**
   * testcase operations
   */
  router.get('/testcases',TestcaseController.show);
  router.get('/testcases/:id/record',TestcaseController.record);
  router.get('/testcases/:id/edit',TestcaseController.edit);
  router.get('/testcases/:id/replay',TestcaseController.replay);

  /**
   * group cases operations
   */
  //router.get('/group',GroupController.show);
  //router.get('/group/:id/record',GroupController.record);
  //router.get('/group/:id/edit',GroupController.edit);


  return router;
};