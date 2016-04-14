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
        res.render('index', { title: 'Express' });
    });

    router.post('/',function(req,res,next){
        var message = req.body.message;
        io.sockets.emit("message", message);
        res.send({});
    });

    return router;
};
