module.exports = function() {
    var express = require('express');
    var router = express.Router();
    var DeviceModel = require('./models/model_device.js');
    var RecordModel= require('./models/model_record.js');

    /* GET home page. */
    router.get('/', function(req, res, next) {
        res.render('index', { title: '首页' });
    });

    /**
     * 获取创建测试任务页面
     */
    router.get('/create', function(req, res, next) {
        res.render('create', { title: '创建测试任务' });
    });

    /**
     * 获取设备列表
     */
    router.get('/devices', function(req, res, next) {
        DeviceModel.find({}, function(err, devices) {
            if (err || !devices) {
                return res.status(500).json();
            }
            res.render('devices', { title: '设备列表', devices: devices });
        });
    });

    /**
     * 获取任务详情
     */
    router.get('/records/:id', function(req, res, next) {
        var record_id= req.params.id;
        RecordModel.findOne({_id: record_id}, function(err, record) {
            if (err || !record) {
                return res.status(500).json();
            }
            res.render('run', { title: '任务详情', record: record });
        });
    });

    /**
     * 获取任务列表
     */
    router.get('/records', function(req, res, next) {
        var id = 
        RecordModel.find({}).sort({create_at: -1}).exec(function(err, records) {
            if (err || !records) {
                return res.status(500).json();
            }
            res.render('records', { title: '任务列表', records: records });
        });
    });


    // /**
    //  * 创建测试任务
    //  */
    // router.post('/test', function(req, res, next) {

    // });

    //router.get('/run',function(req,res,next){
    //    res.render('run',{ title : '实时测试'});
    //});

    //router.get('/contact',function(req,res,next){
    //    res.render('contact',{ title : '联系我们'});
    //});

    // router.get('/records', function(req, res, next) {
    //     res.render('records', { title: '测试记录' });
    // });

    return router;
};
