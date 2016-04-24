var mongoose = require('mongoose');


var RecordSchema= new mongoose.Schema({
    name: String,
    action_count: Number,
    create_at: {type:Date, default : Date.now},
    end_at: {type:Date, default : Date.now}
});

module.exports = mongoose.model('Record', RecordSchema);
