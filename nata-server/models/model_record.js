var mongoose = require('mongoose');

var summarySchema = new mongoose.Schema({
  action: Number,
  widget: Number,
  state: Number,
  activity: Number,
});

var RecordSchema = new mongoose.Schema({
  device_id: String,
  apk_id: String,
  action_count: Number,
  algorithm: {
    type: String,
    enum: ['dfs', 'qlm', 'rm', 'prm'],
  },
  status: {
    type: String,
    enum: ['ready', 'running', 'success', 'failure']
  },
  setup: [String],
  blacklist: [String],
  summaries: [summarySchema],
  activities: [String],
  widgets: [String],
  actions: [String],
  states: [String],
  create_at: {type: Date, default: Date.now},
  end_at: {type: Date, default: Date.now}
});

module.exports = mongoose.model('Record', RecordSchema);
