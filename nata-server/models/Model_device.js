var mongoose = require('mongoose');

var DeviceSchema= new mongoose.Schema({
    id: {
        type: String,
        unique: true,
        trim: true
    },
    name: String,
    android_version: String,
    sdk_version: Number,
    resolution: String,
    cpu_abi: String,
    manufacturer: String,
    create_at: {type:Date, default : Date.now},
}, { id: false });

module.exports = mongoose.model('Device', DeviceSchema);
