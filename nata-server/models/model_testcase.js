var mongoose = require('mongoose');

var TestcaseSchema = new mongoose.Schema({
  name : String,
  device_id: String,
  apk_id: String,
  type: {
    type: String,
    enum: ['complete','part'],
  },
  isFinish: {
    type: Boolean,
    default: false
  },
  actions: [String],
  create_at: {type: Date, default: Date.now},
});

module.exports = mongoose.model('Testcase', TestcaseSchema);
