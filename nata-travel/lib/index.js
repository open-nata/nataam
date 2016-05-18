'use strict';

var _adbkit = require('adbkit');

var _adbkit2 = _interopRequireDefault(_adbkit);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var bluebird = require('bluebird');
var client = _adbkit2.default.createClient();

client.listDevices().then(function (devices) {
  return bluebird.filter(devices, function (device) {
    return client.getFeatures(device.id).then(function (features) {
      return features['android.hardware.nfc'];
    });
  });
}).then(function (supportedDevices) {
  console.log('The following devices support NFC:', supportedDevices);
}).catch(function (err) {
  console.error('Something went wrong:', err.stack);
});