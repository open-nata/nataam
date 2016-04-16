module.exports = function(io){
    var express = require('express');
    var router = express.Router();

    // socket.io events
    io.on( "connection", function( socket )
    {
        console.log( "A user connected" );
    });

    /* GET home page. */
    router.get('/', function(req, res, next) {
        res.render('index', { title: '实验记录' });
    });

    router.post("/summary",function(req,res,next){
        var action =  parseInt(req.body.action,10);
        var widget = parseInt(req.body.widget,10);
        var state = parseInt(req.body.state,10);
        var activity = parseInt(req.body.activity,10);
        console.log("action : " + action +
                    "widget : " + widget +
                    "state  : " + state  +
                    "activity : " + activity);
        var data = {"widget": widget, "action": action, "activity": activity};
        io.sockets.emit("summary", data);

        res.send({});
    });


    router.post('/activity',function(req,res,next){
        var message = req.body.message;
        //console.log(message);
        io.sockets.emit("activity", message);
        res.send({});
    });

    router.post('/widget',function(req,res,next){
        var message = req.body.message;
        io.sockets.emit("widget", message);
        res.send({});
    });

    router.post('/action',function(req,res,next){
        var message = req.body.message;
        io.sockets.emit("action", message);
        res.send({});
    });

    router.post('/state',function(req,res,next){
        var message = req.body.message;
        io.sockets.emit("state", message);
        res.send({});
    });

    return router;
};
