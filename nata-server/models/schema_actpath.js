var mongoose = require('mongoose');

var ActPathSchema = new mongoose.Schema({
  activity_name: {
    type: String,
    unique: true,
    trim: true
  },
  actions: [String]
},{ id: false });

module.exports = ActPathSchema;