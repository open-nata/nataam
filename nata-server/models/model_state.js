var mongoose = require('mongoose');


var StateSchema = new mongoose.Schema({
    appPackage: String,
    activity: String,
    widgets: [Widget]
});

module.exports = mongoose.model('State', StateSchema);
