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
- Move fill EditText duty from AbstractMonkey to TextInputDictionary
- Rewrite play() Function to make it clear
- Remove UINode and keep widget too hold all the info(UINode and widget are confusing)
- Choose  max_value actions randomly
- Make swipe action can be located to specific widget
- Add ImageView tap action and RelativeLayout tap action
- Add some widgets without resourceId can be useful
- Add Home Action to press Home

### (2016/4/5)
- Rewrite RandomMonkey

### (2016/4/6)
- Start using log4j to print logs
- Remove bugs and reconstruct code function
- Add Pure Random Monkey

### (2016/4/7)
- Move common actions to abstract monkey and rewrite other monkeys

### (2016/4/8)
- Doing experiments

### (2016/4/10)
- Add ImageUtil to compare two pictures
- Add Reward to State
- Increase the initial Action Reward
- Add (LinerLayout,checkBox,Radio)click Action
- Adjust ListView and ViewPager in its own direction
- Adjust Reward Strategy

### (2016/4/11)
- Rewrite rules structure

### (2016/4/13)
- Add DFS Monkey
- Add StateGraph to manage the states and action relationships
- Add ActionEdge to link two states
- Add HttpClient package to help send http requests
- Add HttpUtil to send post request

### (2016/4/14)
- Add Module nata-server written in node.js
- Send actions to sever with socket.io
- Add mongoose model : TestUnit, Action, State, Widget
- Add config and connect mongodb

### (2016/4/15)
- Add TestResult to collect test information and interact with nata-server
- Rewrite server views

### (2016/4/16)
- Add action ,state ,widget ,activity states show
- Using ECharts to show the widgets and activity increases with actions 

### (2016/4/18)
- Add DFSState to manage actions of DFS monkey
- Add StateFactory to manage the creation of states
- Make DFS Monkey run

### (2016/4/19)
- Adjust DFS Monkey to run better

### (2016/4/22)
- Construct nata-server UI Design, add introduction,run,records and contact functions
- Add java end server blade to provide service

### (2016/4/23)
- Get device information from java server
- Create device model to store 

### (2016/4/24)
- Create device objects and show them in devices table
- Add Config class to store running config
- Add RunnerFactory to manage runner thread
- Display the test records in the website
- Show detailed running results

### (2016/4/25)
- Use Config class as monkey running parameter
- Successfully send running results to remote url
- Add MonkeyType to restore algorithm types
- Add toCommand method to output human-friendly action string
- Add CleanDataAction
- Add ActionParser to parse Actions
- Add scripts dir to hold action scripts and now you can parse a action script to replay
- Manage the statuses of a running task including ready, running, success, failure

### (2016/4/28)
- Assemble stf minicap module with which you can view the device run live in the website

### (2016/5/3)
- Add Replay function on the website

### (2016/5/4)
- Add Record user case function to the website

### (2015/5/5)
- Increase the user experience of Recording
- Rewrite device login process
- Add Apk logon process
- Rewrite auto test task creation process
- Write record testcase task process

### (2015/5/6)
- Optimize record testcase
- Add testcase edit function
- Start recording after click "start" button ,so that you can do things before recording
- Record text input action

### (2015/5/8)
- Add setInterval ajax event to get summary instead of socket.io
- Add TreeWeb to generate shortest paths to go to activities

### (2015/5/9)
- Send paths to nata-server
- Show act paths in the apk view and can replay act paths
- Rewrite controllers to decouple web_router
- Add act paths to record testcase
- Add test cases to record testcase
- Add replay all functions

### (2015/5/12)
- Debug: can not create apk model
- Add apk info edit
- Debug: WebSocket Error when changing tabs
- Add Icons and Add REC when recording
- Add cancel record button
- Remove text input widget and replace it with prompt
- Rewrite the UI of record testcase

### (2015/5/16)
- Ready to rewirte the java client using nodejs
- Add nata-travel to explore how to write dfs using nodejs

### (2015/5/17)
- Add ESLint to control code style
- Add Babel to use es6

### (2015/5/19)
- Add device.js, Monkey.js

### (2015/5/22)
- add testcase type to rerun the finished complete testcase

### (2015/5/23)
- Add setup rule for monkeys
- Add blacklist for apk
- fix bugs

### (2015/5/24)
- Make nata-travel nata-dfs and publish as a dependent module and remove from this repo

