import Action from './Action.js'

class TapAction extends Action {
  constructor() {
    super('tap')
  }

  // fire(monkey) {
  //   monkey
  // }
}

// tests
import adb from 'adbkit'
const bluebird = require('bluebird')

const client = adb.createClient()

client.listDevices()
  .then((devices) => {
    return bluebird.filter(devices, (device) => {
      return client.openMonkey(device.id)
        .then((monkey) => {
          return monkey.press(3 /* KEYCODE_HOME */, () => {
            console.log('Pressed home button')
            monkey.end()
          })
        })
    })
  })
  .then(() => {
    console.log('The following devices support NFC')
  })
  .catch((err) => {
    console.error('Something went wrong:', err.stack)
  })


export default TapAction