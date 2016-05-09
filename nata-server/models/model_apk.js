var mongoose = require('mongoose');
var ActPathSchema = require('./schema_actpath');

var ApkSchema = new mongoose.Schema({
  name: String,
  version: String,
  package_name: {
    type:String,
    unique: true,
    trim: true
  },
  actpaths: [ActPathSchema],
  activity_name: String,
  create_at: {type: Date, default: Date.now},
});

module.exports = mongoose.model('Apk', ApkSchema);
