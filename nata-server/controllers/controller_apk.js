var ApkModel = require('../models/model_apk');
var _ = require('lodash');

/**
 * 获取应用列表
 */
module.exports.show = function (req, res, next) {
  ApkModel.find({}, function (err, apks) {
    if (err) {
      return res.status(500).json();
    }
    res.render('apk/apks', {
      title: '应用列表',
      apks: apks
    });
  });
};

/**
 * 获取应用详细信息
 */
module.exports.detail = function (req, res, next) {
  var apk_id = req.params.id;

  ApkModel.findOne({_id: apk_id}, function (err, apk) {
    if (err) {
      return res.status(500).json();
    }
    res.render('apk/apk_detail', {
      title: '应用详细情况',
      apk: apk,
    });
  });
};

/**
 * 获取应用详细信息
 */
module.exports.replay= function (req, res, next) {
  var apk_id = req.params.id;
  var act_name = req.params.act_name;

  console.log(act_name);

  ApkModel.findOne({_id: apk_id}, function (err, apk) {
    if (err) {
      return res.status(500).json();
    }

    var actpath = _.find(apk.actpaths, { 'activity': act_name, });
    if(actpath){
      res.render('replay', {
        title: 'Act回放',
        actions: actpath.actions
      });
    }else{
      return res.status(500).json();
    }

  });
};
