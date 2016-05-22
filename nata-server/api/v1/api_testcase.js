var TestcaseModel = require('../../models/model_testcase.js');
var _ = require('lodash');

module.exports.create = function (req, res, next) {
  var testcase = new TestcaseModel();
  testcase.name = req.body.name;
  testcase.device_id = req.body.device_id;
  testcase.apk_id = req.body.apk_id;
  testcase.type = req.body.type;

  testcase.save(function (err, testcase) {
    if (err) {
      return next(err);
    } else {
      res.status(200).json(testcase);
    }
  });
};

module.exports.getactions = function (req, res, next) {
  var testcase_id = req.params.id;
  TestcaseModel.findOne({
    _id: testcase_id
  }, function (err, testcase) {
    if (err) {
      return next(err);
    }
    var actions = testcase.actions.join("\n");
    res.status(200).json(actions);
  });
};


module.exports.remove = function (req, res, next) {
  var testcase_id = req.params.id;
  TestcaseModel.findOneAndRemove({
    _id: testcase_id
  }, function (err, testcase) {
    if (err) {
      return next(err);
    }
    res.status(200).json(testcase);
  });
};

module.exports.finish = function (req, res, next) {
  var testcase_id = req.params.id;
  var actions = req.body.actions;
  console.log(actions);


  TestcaseModel.findOne({
    _id: testcase_id
  }, function (err, testcase) {
    if (err) {
      next(err);
    }
    //var splits = actions.trim().split("\n");
    testcase.actions = actions;
    //_(splits).forEach(function(action){
    //   testcase.actions.push(action);
    //});

    testcase.isFinish = true;

    testcase.save(function (err) {
      if (err) return next(err);
      res.status(200).send("success");
    });
  });
};

module.exports.save = function (req, res, next) {
  var testcase_id = req.params.id;
  var name = req.body.name;
  var actions = req.body.actions;
  console.log(name);
  console.log(actions);


  TestcaseModel.findOneAndUpdate({_id: testcase_id}, {name:name,actions:actions},function (err, testcase) {
    if (err) {
      next(err);
    }
    res.status(200).send("success");
  });
};

