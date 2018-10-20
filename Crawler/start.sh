PATH_PRE=`pwd`
PATH_DATA=$(dirname "$PWD")
if [ $# -lt 1 ] ; then
	echo "缺少必要的启动参数"
	echo "*********启动参数设置*********"
	echo "penal 刑事案件"
	echo "civil 民事案件"
	echo "admin 行政案件"
	echo "compensation 赔偿案件"
	echo "executive 执行案件"
	echo "比如：bash start.sh penal 表示本次仅仅抓取刑事案件类型"
	echo "******************************"
	exit
fi
echo "本次抓取案件：" $@
echo "执行命令：java -jar -DpathPre=$PATH_PRE/config -DpathData=$PATH_DATA/datas  Crawler-0.0.1-SNAPSHOT.jar $@ >/dev/null 2>&1 &"
nohup java -jar -DpathPre=$PATH_PRE/config -DpathData=$PATH_DATA/datas  Crawler-0.0.1-SNAPSHOT.jar $@ >/dev/null 2>&1 &
sleep 3s
result=`ps -ef |grep Crawler-0.0.1-SNAPSHOT.jar |grep -v grep|awk '{print $2}'`
if [ -n $result ] ; then
	echo "启动成功"
else
    echo "启动失败"
fi
