var RecordModel = require('../models/model_record.js');
var DeviceModel = require('../models/model_device.js');
var ApkModel = require('../models/model_apk.js');
var TestcaseModel = require('../models/model_testcase.js');
var eventproxy= require('eventproxy');
var _ = require('lodash');

/**
 * 获取自动任务运行详情
 */
module.exports.run = function (req, res, next) {
  var record_id = req.params.id;
  RecordModel.findOne({
    _id: record_id
  }, function (err, record) {
    if (err || !record) {
      return res.status(500).json();
    }
    res.render('records/run', {
      title: '任务详情',
      record: record
    });
  });
};

/**
 * 重新运行
 */
module.exports.replay = function (req, res, next) {
  var record_id = req.params.id;
  RecordModel.findOne({
    _id: record_id
  }, function (err, record) {
    if (err || !record) {
      return res.status(500).json();
    }
    res.render('replay', {
      title: '回放',
      actions: record.actions
    });
  });
};

/**
 * 获取详细报告
 */
module.exports.report =  function (req, res, next) {
  var record_id = req.params.id;
  RecordModel.findOne({
    _id: record_id
  }, function (err, record) {
    if (err || !record) {
      return res.status(500).json();
    }
    var summaries = record.summaries;

    var xData = [];
    var yDataWidget = [];
    var yDataActivity = [];
    for (var i = 0; i < summaries.length; i++) {
      xData.push(summaries[i].action);
      yDataActivity.push(summaries[i].activity);
      yDataWidget.push(summaries[i].widget);
    }
    console.log(xData);
    res.render('records/report', {
      title: '详细报告',
      xData: xData,
      yDataWidget: yDataWidget,
      yDataActivity: yDataActivity
    });
  });
};

/**
 * 获取任务列表
 */
 module.exports.show = function (req, res, next) {
  var ep = new eventproxy();
  ep.fail(next);

  ep.all('records','devices','apks',function(records,devices,apks){

    records.forEach(function (record) {
      // join
      if(record.setup.length !== 0 ) {
        var actions = _.join(record.setup,'\n');
        record.setup = actions;
      }



      ApkModel.findOne({_id: record.apk_id}).exec(ep.done(function (apk) {
        record.apk_name = apk.name;
        record.package_name = apk.package_name;
        record.activity_name = apk.activity_name;

        if(apk.blacklist.length !==0  ) {
          var resources = _.join(apk.blacklist,'\n');
          record.blacklist = resources;
        }


        ep.emit('apk');
      }));
    });

    ep.after('apk', records.length, function () {
      res.render('records/records', {
        title: '自动遍历任务',
        records: records,
        devices: devices,
        apks: apks
      });
    })
  });

  RecordModel.find({}).sort({create_at: -1}).exec(ep.done("records"));
  DeviceModel.find({}).sort({create_at: -1}).exec(ep.done("devices"));
  ApkModel.find({}).sort({create_at: -1}).exec(ep.done("apks"));


};