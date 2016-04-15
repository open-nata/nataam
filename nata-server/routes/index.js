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


    router.post('/hello', function (req,res,next) {
        console.log(req.body.message) ;
        res.send("");
    });

    router.post('/activity',function(req,res,next){
        var message = req.body.message;
        console.log(message);
        io.sockets.emit("activity", message);
        res.send({});
    });



    return router;
};
