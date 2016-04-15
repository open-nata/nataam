var mongoose = require('mongoose');


var TestResultSchema= new mongoose.Schema({
    name: String,
    action_count: Number,
    create_at: {type:Date, default : Date.now},
});

module.exports = mongoose.model('TestResult', TestResultSchema);
