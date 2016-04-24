var mongoose = require('mongoose');


var ActionSchema = new mongoose.Schema({
    type: String,
    description: String
});

module.exports = mongoose.model('Action', ActionSchema);
