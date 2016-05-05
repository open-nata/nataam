var ApkModel = require('../../models/model_apk');

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
  var apk_id= req.params.id;
  ApkModel.findOneAndRemove({
    _id:apk_id
  }, function(err, apk) {
    if (err) {
      next(err);
    }
    res.status(200).json(apk);
  });
};
