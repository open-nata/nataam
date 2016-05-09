//var TestcaseModel = require('../models/model_testcase.js');
//var DeviceModel = require('../models/model_device.js');
//var ApkModel = require('../models/model_apk.js');
//var eventproxy= require('eventproxy');
//var _ = require('lodash');
//
//
///**
// * 获取录制任务列表
// */
//module.exports.show = function (req, res, next) {
//
//  var ep = new eventproxy();
//  ep.fail(next);
//
//  ep.all('testcases','devices','apks',function(testcases,devices,apks){
//
//    testcases.forEach(function (testcase) {
//      console.log(testcase.apk_id);
//      ApkModel.findOne({_id: testcase.apk_id}).exec(ep.done(function (apk) {
//        testcase.apk_name = apk.name;
//        testcase.package_name = apk.package_name;
//        testcase.activity_name = apk.activity_name;
//        ep.emit('apk');
//      }));
//    });
//
//    ep.after('apk', testcases.length, function () {
//      res.render('testcases/index', {
//        title: '录制任务',
//        testcases: testcases,
//        devices: devices,
//        apks: apks
//      });
//    })
//  });
//
//  TestcaseModel.find({}).sort({create_at: -1}).exec(ep.done("testcases"));
//  DeviceModel.find({}).sort({create_at: -1}).exec(ep.done("devices"));
//  ApkModel.find({}).sort({create_at: -1}).exec(ep.done("apks"));
//};
//
//module.exports.record = function (req, res, next) {
//  var testcase_id = req.params.id;
//  var ep = new eventproxy();
//  ep.fail(next);
//
//  TestcaseModel.findOne({_id:testcase_id}).exec(ep.done('testcase'));
//
//  ep.on('testcase',function(testcase){
//    ApkModel.findOne({_id:testcase.apk_id}).exec(ep.done('apk'));
//  });
//
//  ep.all('testcase','apk',function(testcase,apk){
//    res.render('testcases/record', {
//      title: '录制任务',
//      apk : apk,
//      testcase: testcase,
//    });
//  });
//};
//
//module.exports.edit = function (req, res, next) {
//  var testcase_id = req.params.id;
//  var ep = new eventproxy();
//  ep.fail(next);
//
//  TestcaseModel.findOne({_id:testcase_id}).exec(ep.done('testcase'));
//
//  ep.on('testcase',function(testcase){
//    var actions = "";
//    _(testcase.actions).forEach(function(action){
//      actions += action +"\n";
//    });
//    console.log(actions);
//
//    res.render('testcases/edit', {
//      title: '录制任务',
//      testcase: testcase,
//      actions: actions.trim()
//    });
//  });
//};