var DeviceModel = require('../models/model_device');

/**
 * 获取设备列表
 */
module.exports.show = function (req, res, next) {
  DeviceModel.find({}, function (err, devices) {
    if (err) {
      return res.status(500).json();
    }
    res.render('devices', {
      title: '设备列表',
      devices: devices
    });
  });
};