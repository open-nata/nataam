'use strict';

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _Action2 = require('./Action.js');

var _Action3 = _interopRequireDefault(_Action2);

var _adbkit = require('adbkit');

var _adbkit2 = _interopRequireDefault(_adbkit);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var TapAction = function (_Action) {
  _inherits(TapAction, _Action);

  function TapAction() {
    _classCallCheck(this, TapAction);

    return _possibleConstructorReturn(this, Object.getPrototypeOf(TapAction).call(this, 'tap'));
  }

  // fire(device) {
  //   device.
  // }


  return TapAction;
}(_Action3.default);

// tests


var bluebird = require('bluebird');

var client = _adbkit2.default.createClient();

client.listDevices().then(function (devices) {
  return bluebird.filter(devices, function () {
    return client.openMonkey().then(function (monkey) {
      return monkey.press(3 /* KEYCODE_HOME */, function () {
        console.log('Pressed home button');
        client.end();
      });
    });
  });
}).then(function (supportedDevices) {
  console.log('The following devices support NFC:', supportedDevices);
}).catch(function (err) {
  console.error('Something went wrong:', err.stack);
});

exports.default = TapAction;