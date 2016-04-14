var express = require('express');
var router = express.Router();


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});



router.post('/',function(req,res,next){
    var message = req.body.message;
    console.log(message);

    //res.send("get message");
    //res.render('index', { title: 'Express' ,message: message});
});

module.exports = router;
