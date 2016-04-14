var mongoose = require('mongoose');


var WidgetSchema = new mongoose.Schema({
    type: String,
    startX: Number,
    endX: Number,
    startY: Number,
    endY: Number,
    packageName: String,
    resourceId: String,
    className: String,
    enabled: String,
    checked: String,
    selected: String
});

module.exports = mongoose.model('Widget', WidgetSchema);
