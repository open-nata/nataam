var mongoose = require('mongoose');

//var autoSchema = new mongoose.Schema({
//  action: Number,
//  algorithm : String,
//  depth: Number
//});

//setUp
var GroupSchema = new mongoose.Schema({
  name : String,
  device_id: String,
  apk_id: String,
  isFinish: {
    type: Boolean,
    default: false
  },
  //setUp: [String],
  //testcase : [String],
  //auto : autoSchema,
  actions: [String],
  create_at: {type: Date, default: Date.now},
});

module.exports = mongoose.model('Group', GroupSchema);
