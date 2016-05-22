
class Widget {
  constructor() {
    this.text = ''
    this.resourceId = ''
    this.className = ''
    this.contentDesc = ''
    this.checkable = ''
    this.checked = ''
    this.clickable = ''
    this.bounds = ''
    this.packageName = ''
    this.enabled = ''
    this.focusable = ''
    this.focused = ''
    this.scrollable = ''
    this.long_clickable = ''
    this.password = ''
    this.selected = ''

    this.startX = 0
    this.startY = 0
    this.endX = 0
    this.endY = 0
  }

  set text(text) {
    this.text = text
  }

  get text() {
    return this.text
  }
}

export default Widget