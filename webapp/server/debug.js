/**
 * Created by Lens on 1/16/17.
 */
var path = require('path');

var nwPath = process.argv[0];
var serverRoot = path.dirname(nwPath);

process.on('uncaughtException', function(error) {
    var message = '[' + (new Date()).toISOString() + '] ' +
        error.stack + '\n' +
        Array(81).join('-') + '\n\n';

    require('fs').appendFileSync(serverRoot + '/error.log', message);

    process.exit(1);
});
