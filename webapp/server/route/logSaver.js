/**
 * Created by Lens on 12/16/16.
 */
var fs = require('fs');

var logSave = function(app){
    console.log("Log saver loading ...");

    init(app);

    app.post('/log/sav', function (req, res) {
        var sn = req.param("SN");
        var category = req.param("category");
        var content = req.param("content");

        var dirStr = app.serverRoot + "/" + "data/log/"+sn;


        var now = new Date();
        var dateString = now.toDateString() + " " + now.toTimeString();
        content = dateString + "\n" + content + "\n\n";

        md(dirStr);

        var fileName = dirStr + "/" + category + "_" + getDateStr(now)+".txt";
        console.log("logSave at : " +fileName);
        fs.open(fileName,"a",function(err,fd){
            if(err){
                console.log("Open file with error : " + err);
            }
            console.log("Open file : " + fd);
            fs.write(fd,content,null,'utf8', function () {
                fs.close(fd,function(){
                    console.log("Close file");
                })
            })
        });

        res.sendStatus(200);
    });

    app.post('/log/savTable', function (req, res) {
        var sn = req.param("SN");
        var category = req.param("category");
        var content = req.param("content");
        var selfDiagnosis = req.param("selfDiagnosis");

        var dirStr = app.serverRoot + "/" + "data/log/"+sn;

        var now = new Date();

        md(dirStr);

        var fileName = dirStr + "/" + category + "_" + sn + "_" + getDateStr(now)+getTimeStr(now)+".csv";

        var resultArr = [];
        resultArr.push(content[0]);
        content.splice(0,1);
        content.forEach(function(testItem){
            var str = "";
            /*for(var key in testItem) {
                if(str === "") {
                    str = testItem[key];
                } else {
                    str = str + "," + testItem[key];
                }
            }
            str = str + "\n";*/
            str = "\"" + testItem['category'] + "\",\"" + testItem['portNum'] + "\",\"" + testItem['item'] + "\",\""
                 + testItem['progress'] + "\",\""+ testItem['autoCfm'] + "\",\""+ (testItem['comment']).replace(/\n/g," ") + "\",\""
                 + testItem['result'] + "\"" + "\n";
            resultArr.push(str);
        });

        if(selfDiagnosis != undefined) {
            resultArr.push(selfDiagnosis);
        }

        fs.open(fileName,"a",function(err,fd){
            if(err){
                console.error("Open file with error : " + err);
            }
            console.log("Open file : " + fd);
            /*var name = fd.name;
            fs.write(fd,content,null,'utf8', function () {
                fs.close(fd,function(){
                    console.log("Close file : " + name);
                })
            })*/
            function write(err, written) {
                if (err) throw err;
                if (i>=resultArr.length) {
                    //close the file
                    console.log("Close file");
                    fs.close(fd);
                } else {
                    //continue to write
                    //fs.write(fd, buf, startPos, content[i].length, 'utf8', write);
                    fs.write(fd, resultArr[i], null, 'utf8', write);
                    i++;
                }
            }
            var i=0;
            write(null, 0);

        });

        var file = {"fileName":fileName};
        res.status(200).json(file);
    });

    app.post('/log/selfDiagnosis', function (req, res) {
        var fileName = req.param("fileName");
        var selfDiagnosis = req.param("selfDiagnosis");

        fs.readFile(fileName, 'utf8',function read(err, data) {
            if (err) {
                throw err;
            }
            data = data.substr(0,data.indexOf('Self Diagnosis:')-1) + "\n" + selfDiagnosis;
            fs.writeFile(fileName, data, function (err) {
                if (err) {
                    return console.log(err);
                }
                console.log("Modified last lines");
            });
        });

        res.sendStatus(200);
    });


    app.post('/log/onuInfo/savTable', function (req, res) {
        console.log("/log/onuInfo/savTable");
        var sn = req.param("SN");
        var category = req.param("category");
        var content = req.param("content");

        var dirStr = app.serverRoot + "/" + "data/log/"+sn;

        var now = new Date();

        md(dirStr);

        var fileName = dirStr + "/" + category + "_" + sn + "_" + getDateStr(now)+getTimeStr(now)+".csv";

        var resultArr = [];
        resultArr.push(content[0]);
        var testItem = content[1];
        var str = "\"" + testItem['GPONSN'] + "\",\"" + testItem['Type'] + "\",\"" + testItem['Code'] + "\",\""
                + testItem['Software'] + "\",\""+ testItem['Hardware'] + "\",\""+ testItem['LOID'] + "\",\""
                + testItem['Password'] + "\",\""+ testItem['SLID'] + "\",\""+ testItem['MAC'] + "\",\""
                + testItem['Serial'] + "\",\""+ testItem['OperatorID'] + "\",\""+ testItem['Date'] + "\",\""
                + testItem['InterfaceStr'] + "\",\""+ testItem['ImageStr'] + "\"" + "\n";
        resultArr.push(str);
        fs.open(fileName,"a",function(err,fd){
            if(err){
                console.error("Open file with error : " + err);
            }
            console.log("Open file : " + fd);
            function write(err, written) {
                if (err) throw err;
                if (i>=resultArr.length) {
                    console.log("Close file");
                    fs.close(fd);
                } else {
                    fs.write(fd, resultArr[i], null, 'utf8', write);
                    i++;
                }
            }
            var i=0;
            write(null, 0);
        });

        res.sendStatus(200);
    });
};

function getDateStr(date){
    var year= date.getFullYear();
    var month = date.getMonth() + 1;
    month = (month < 10 ? "0":"")+month;
    var day = date.getDate();
    day = (day < 10 ? "0":"")+day;
    return year+month+day;
}

function getTimeStr(date){
    var hour= date.getHours();
    hour = (hour < 10 ? "0":"")+hour;
    var minute = date.getMinutes();
    minute = (minute < 10 ? "0":"")+minute;
    var second = date.getSeconds();
    second = (second < 10 ? "0":"")+second;
    return hour+minute+second;
}


var md = function(dir){
    if(!fs.existsSync(dir)){
        fs.mkdirSync(dir);

    }
};

function init(app){
    md(app.serverRoot + "/data");
    md(app.serverRoot + "/data/log");
};


module.exports = logSave;
