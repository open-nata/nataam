import adb from 'adbkit'
import os from 'os'
import fs from 'fs'

const client = adb.createClient()

class Device {
  constructor(deviceId) {
    this.deviceId = deviceId
  }

  async dumpUI() {
    const source = '/storage/sdcard0/window_dump.xml'
    const cmd = `uiautomator dump ${source}`
    const target = `${os.tmpdir()}/dumpfile.xml`
    try {
      await client.shell(this.deviceId, cmd)
      await this.pullFile(source,target)
    } catch (err) {
      console.log(err)
    }
  }

  pullFile (source, target) {
    client.pull(this.deviceId,source)
      .then(function(transfer) {
        return new Promise(function(resolve, reject) {
            transfer.on('end', function() {
              resolve(device.id)
            })
            transfer.on('error', reject)
            transfer.pipe(fs.createWriteStream(target))
          })
      }) 

  }
}

export default Device

const deviceId = 'DU2SSE1478031311'
const device = new Device(deviceId)

device.dumpUI()
