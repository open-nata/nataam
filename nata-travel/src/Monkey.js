import adb from 'adbkit'

let client = adb.createClient()

class Monkey {
  constructor(pkg, act, deviceId) {
    this.pkg = pkg
    this.act = act
    this.deviceId = deviceId
    this.monkey = client.openMonkey(deviceId)
  }

  getPkgAct() {
    return `$(this.pkg)/$(this.act)`
  }
}

export default Monkey