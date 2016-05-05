var TestcaseModel = require('../../models/model_testcase.js');
var _ = require('lodash');

module.exports.create = function(req, res, next) {
  var testcase = new TestcaseModel();
  testcase.device_id = req.body.device_id;
  testcase.apk_id= req.body.apk_id;

  testcase.save(function(err, testcase) {
    if (err) {
      return next(err);
    } else {
      res.status(200).json(testcase);
    }
  });
};

module.exports.remove = function(req, res, next) {
  var testcase_id = req.params.id;
  TestcaseModel.findOneAndRemove({
    _id: testcase_id
  }, function(err, testcase) {
    if (err) {
      return next(err);
    }
    res.status(200).json(testcase);
  });
};

module.exports.finish = function(req, res, next) {
  var testcase_id = req.params.id;
  var actions = req.body.actions;


  TestcaseModel.findOne({
    _id: testcase_id
  }, function(err, testcase) {
    if (err) {
      next(err);
    }
    var splits = actions.trim().split("\n");
    _(splits).forEach(function(action){
       testcase.actions.push(action);
    });

    testcase.isFinish= true;

    testcase.save(function(err) {
      if (err) return next(err);
      res.status(200).send("success");
    });
  });
};

