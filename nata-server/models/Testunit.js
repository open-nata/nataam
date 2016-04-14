var mongoose = require('mongoose');


var TestUnitSchema = new mongoose.Schema({
    type: String,
    action_count: Number,
    actions: [Action],
    states: [State],
    create_at: {type:Date, default : Date.now},
    end_at: {type:Date, default : Date.now}
});

module.exports = mongoose.model('TestUnit', TestUnitSchema);
