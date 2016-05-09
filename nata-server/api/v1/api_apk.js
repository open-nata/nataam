var ApkModel = require('../../models/model_apk');
var RecordModel = require('../../models/model_record');
var TestcaseModel = require('../../models/model_testcase');
var eventproxy= require('eventproxy');
var _  = require('lodash');

module.exports.create = function(req, res, next) {
  var apk= new ApkModel();
  apk.name = req.body.name;
  apk.version = req.body.version;
  apk.package_name = req.body.package_name;
  apk.activity_name = req.body.activity_name;
  apk.save(function(err, apk) {
    if (err) {
      return next(err);
    } else {
      res.status(200).json(apk);
    }
  });
};

module.exports.remove = function(req, res, next) {
  var ep = new eventproxy();
  ep.fail(next);
  var apk_id= req.params.id;
  console.log("apk_id");

  ep.all('records','testcases','apk',function(records,testcase,apk){
    res.status(200).json(apk);
  });

  TestcaseModel.find({apk_id: apk_id}).remove(ep.done('testcases'));
  RecordModel.find({apk_id: apk_id}).remove(ep.done('records'));
  ApkModel.findOneAndRemove({_id:apk_id}).exec(ep.done('apk'));

};

module.exports.actpath= function(req, res, next) {
  var package_name = req.params.package;
  var activity_name = req.body.activity_name;
  console.log(activity_name);
  var actions = req.body.actions;
  if(!activity_name || ! actions){
    return res.status(422).send("error");
  }
  actions = actions.trim().split("\n");
  //console.log(actions.length);
  //console.log(actions[0]);

  ApkModel.findOne({package_name:package_name}).exec(function(err,apk){
    var actPaths = apk.actPaths;
    var actPath = _.find(actPaths, {'activity_name': activity_name});
    if(!actPath || actPath.actions.length > actions.length){
      var newActPath = {
        "activity_name" : activity_name,
        "actions" : actions
      }
      apk.actPaths.push(newActPath);
      apk.save(function (err) {
        if (err) return next(err);
        res.status(200).send("success");
      });
    }else{
      res.status(200).send("success");
    }

  });
};

