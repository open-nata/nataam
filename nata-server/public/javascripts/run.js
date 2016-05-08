// var socket = io();
//var activityCount = 0;
//var widgetCount = 0;
//var actionCount = 0;
//var stateCount = 0;

// socket.on('activity', function (message) {
//     activityCount++;
//     var content = "<tr><td>" + activityCount + "</td><td>" + message + "</td>";
//     $('#activities').append(content);
//     var scrollBottom = $('#activity-table').height();
//     $('#activity-table-wrapper').scrollTop(scrollBottom);
// });

// socket.on('widget', function (message) {
//     widgetCount++;
//     var content = "<tr><td>" + widgetCount + "</td><td>" + message + "</td>";
//     $('#widgets').append(content);
//     var scrollBottom = $('#widget-table').height();
//     $('#widget-table-wrapper').scrollTop(scrollBottom);
// });

// socket.on('action', function(message) {
//     actionCount++;
//     var content = "<tr><td>" + message + "</td>";
//     $('#actions').append(content);
//     var scrollBottom = $('#action-table').height();
//     $('#action-table-wrapper').scrollTop(scrollBottom);
// });

// socket.on('state', function (message) {
//     stateCount++;
//     var content = "<tr><td>" + stateCount + "</td><td>" + message + "</td>";
//     $('#states').append(content);
//     var scrollBottom = $('#state-table').height();
//     $('#state-table-wrapper').scrollTop(scrollBottom);
// });


// 基于准备好的dom，初始化echarts实例
var myWidgetChart = echarts.init(document.getElementById('echarts-widget'));

var yDataWidget = [];
// 指定图表的配置项和数据
var optionWidget = {
  title: {
    left: 'center',
    text: 'Widget 变迁图',
    subtextStyle: {
      color: '#fff'
    }
  },
  tooltip: {
    trigger: 'axis'
  },
  toolbox: {
      show: true,
      feature: {
          dataView: {show: true, readOnly: false},
          magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
          restore: {show: true},
          saveAsImage: {show: true}
      }
  },
  legend: {
    top: 'bottom',
    data: ['Widget数量'],
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value',
    boundaryGap: [0, '100%']
  },
  series: [{
    name: 'Widget数量',
    type: 'line',
    smooth: true,
    sampling: 'average',
    itemStyle: {
      normal: {
        color: 'rgb(255, 70, 131)'
      }
    },
    areaStyle: {
      normal: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
          offset: 0,
          color: 'rgb(255, 158, 68)'
        }, {
          offset: 1,
          color: 'rgb(255, 70, 131)'
        }])
      }
    },

    data: []
  }]
};

// 使用刚指定的配置项和数据显示图表。
myWidgetChart.setOption(optionWidget);


// 基于准备好的dom，初始化echarts实例
var myActivityChart = echarts.init(document.getElementById('echarts-activity'));

var xData = [];
var yDataActivity = [];
// 指定图表的配置项和数据
var optionActivity = {
  title: {
    left: 'center',
    text: 'Activity 变迁图'
  },
  tooltip: {
    trigger: 'axis'
  },
  toolbox: {
      show: true,
      feature: {
          dataView: {show: true, readOnly: false},
          magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
          restore: {show: true},
          saveAsImage: {show: true}
      }
  },
  legend: {
    top: 'bottom',
    data: ['Activity数量']
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []
  },
  yAxis: {
    type: 'value',
    boundaryGap: [0, '100%']
  },
  series: [{
    name: 'Activity数量',
    type: 'line',
    smooth: true,
    sampling: 'average',
    itemStyle: {
      normal: {
        color: 'rgb(255, 70, 131)'
      }
    },
    areaStyle: {
      normal: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
          offset: 0,
          color: 'rgb(255, 158, 68)'
        }, {
          offset: 1,
          color: 'rgb(255, 70, 131)'
        }])
      }
    },

    data: []
  }]
};

// 使用刚指定的配置项和数据显示图表。
myActivityChart.setOption(optionActivity);
var record_id = record._id;

setInterval(function () {

  $.ajax({
    url: "/api/v1/records/"+record_id+"/summary",
    type: 'GET',
    success: function(result) {

      myWidgetChart.setOption({
         xAxis: {
             data: result.xData
         },
         series: [{
             // 根据名字对应到相应的系列
             name: 'Widget数量',
             data: result.yDataWidget
         }]
     });

      myActivityChart.setOption({
         xAxis: {
             data: result.xData
         },
         series: [{
             // 根据名字对应到相应的系列
             name: 'Activity数量',
             data: result.yDataActivity
         }]
     });
    },
    error: function(error) {

    }
  });
},2000);


// socket.on('summary', function(data) {
//     xData.push(data.action);
//     yDataWidget.push(data.widget);
//     yDataActivity.push(data.activity);
//     myWidgetChart.setOption({
//         xAxis: {
//             data: xData
//         },
//         series: [{
//             // 根据名字对应到相应的系列
//             name: 'Widget数量',
//             data: yDataWidget
//         }]
//     });

//     myActivityChart.setOption({
//         xAxis: {
//             data: xData
//         },
//         series: [{
//             // 根据名字对应到相应的系列
//             name: 'Activity数量',
//             data: yDataActivity
//         }]
//     });
// });