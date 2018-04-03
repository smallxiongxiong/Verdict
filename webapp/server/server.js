// set up ======================================================================
var express = require('express');
var path = require('path');
var app = express(); 						// create our app w/ express
//var mongoose = require('mongoose'); 				// mongoose for mongodb
var port = process.env.PORT || 8080; 				// set the port
//var database = require('./config/database'); 			// load the database config
//var morgan = require('morgan');
var bodyParser = require('body-parser');
var methodOverride = require('method-override');

// configuration ===============================================================
// DEV Configuration
var clientRoot = path.join(__dirname,'../src');
var cwd = process.cwd();
console.log("CWD = "+cwd);
app.serverRoot = cwd;

// WINDOWS Configuration
if(port == 8080){
    clientRoot = path.join(__dirname,'dist/src');
    var nwPath = process.argv[0];
    var serverRoot = path.dirname(nwPath);
    app.serverRoot=serverRoot;
}
console.log("serverRoot = " + app.serverRoot);
console.log("clientRoot = " + clientRoot);
app.use(express.static(clientRoot)); 		// set the static files location /public/img will be /img for users


// Load Module ===============================================================

app.use(bodyParser.json({type: 'application/vnd.api+json'})); // parse application/vnd.api+json as json
app.use(bodyParser.json({limit: "50mb"}));
app.use(bodyParser.urlencoded({limit: "50mb", extended: true, parameterLimit:50000}));
app.use(methodOverride('X-HTTP-Method-Override')); // override with the X-HTTP-Method-Override header in the request




// routes ======================================================================
require('./route/logSaver')(app);

// main ======================================================================
app.use(function (req, res, next) {
    console.log('Time: %d', Date.now());
    console.log('Time: %d' + req.params, Date.now());
    next();
});

app.use(function(err,req,res,next){
    console.error(err.stack);
    res.status(500|err.status);
});


// listen (start app with node server.js) ======================================

app.listen(port);

console.log("App listening on port " + port);
