import adb from 'adbkit'

const bluebird = require('bluebird')
const client = adb.createClient()

client.listDevices()
  .then((devices) => {
    return bluebird.filter(devices, (device) => {
      return client.getFeatures(device.id)
        .then((features) => {
          return features['android.hardware.nfc']
        })
    })
  })
  .then((supportedDevices) => {
    console.log('The following devices support NFC:', supportedDevices)
  })
  .catch((err) => {
    console.error('Something went wrong:', err.stack)
  })