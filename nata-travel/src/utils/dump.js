import fs from 'fs'
import eyes from 'eyes'
import xml2js from 'xml2js'

const inspect = eyes.inspector({ maxLength: false })

const parser = new xml2js.Parser()


function parseFile(target) {
  return new Promise((resolve, reject) => {
    fs.readFile(target, (err, data) => {
      if (err) reject(err)
      parser.parseString(data, (error, result) => {
        if (error) reject(error)
        console.log(inspect(result))
        resolve(result)
      })
    })
  })
}

function treeWalk(doc, nodes) {
  console.log(doc)
}

async function getNodes(target) {
  const doc = await parseFile(target)
  const nodes = []
  treeWalk(doc, nodes)
}

export default getNodes