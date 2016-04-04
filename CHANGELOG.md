# CHANGELOG

### v0.0.1(2016/1/13)
- Construct the basic structure of nataam

### (2016/1/18)
- Add Gradle support
- Add Log4j & Dom4j

### (2016/1/20)
- Add sub-module nata-monkey to hold the main logic
- Add sub-module nata-kit to hold the common tools

### (2016/3/7)
- Add TapAction
- Make the normal monkey tests run with click clickable widgets randomly

### (2016/3/8)
- Add BackAction
- Add MenuAction
- Add TextInputAction

### (2016/3/9)
- Add Swipe Action(UP,DOWN,LEFT,RIGHT)
- Can get Device Resolution
- Add Input Dictionary including valid and invalid inputs

### (2016/3/13)
- Add StartAppAction , make every action recorded
- get back to the app when pkg changes

### (2016/3/24)
- fix bug : can't read chinese from dumpfile.xml
- Add ActionFactory to manage the action creations
- Add State Class to record States
- Add LongClick Action
- Add QLearningMonkey Class

### (2016/3/28)
- Applying QLearning Algorithm to Monkey
- Add QLMRunner to run QLearningMonkey
- Make every action to record its own executing times and give its own reward
- Add hashCode function and equals function to every action and widget ,UINode
- clear app data when start the app

### (2016/3/30)
- Add JSONparser using fastjson
- Add resource Rule
- Add knowledge to monkey so that it can learn from rules and check knowledge after choose action and change the action behavior
- Remove useless Strategy class
- change backAction Reward to be small

### (2016/4/3)
- Make action getReward function abstract, different actions should have different reward
- Rewrite Action toString function 
- Add Enum Rule 
- Add KeyValue Rule
- Add SetUpRule
- Add subState

### (2016/4/4)
- move "fill Edittext" duty from AbstrctMonkey to TextInputDictionary
- rewrite play() funciton to make it clear
- remove UINode and keep widget too hold all the info(UINode and widget are confusing)