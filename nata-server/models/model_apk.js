var mongoose = require('mongoose');

var ApkSchema = new mongoose.Schema({
  name: String,
  version: String,
  package_name: {
    type:String,
    unique: true,
    trim: true
  },
  activity_name: String,
  create_at: {type: Date, default: Date.now},
});

module.exports = mongoose.model('Apk', ApkSchema);
