@echo off
echo *******************************
echo *********begin package*********
set curDir=%~dp0
echo %curDir%
set targetDir=%curDir%dir
echo *********begin package*********
if exist %targetDir% (
	echo  %targetDir% exist, need to Delelte
	rd /s /q %targetDir%
)
echo %targetDir% success and mkdir %targetDir% 
md %targetDir%\config\javascript\
md %targetDir%\config\keywords\
md %targetDir%\datas\haddone\
echo copy %curDir%config\* %targetDir%\config
copy %curDir%config\* %targetDir%\config
echo copy %curDir%config\keywords\*.txt %targetDir%\config\keywords
copy %curDir%config\keywords\*.txt %targetDir%\config\keywords
echo copy %curDir%datas\haddone\*.txt %targetDir%\datas\haddone\
copy %curDir%datas\haddone\*.txt %targetDir%\datas\haddone\
echo copy %curDir%config\javascript\*.js %targetDir%\config\javascript\
copy %curDir%config\javascript\*.js %targetDir%\config\javascript\
call mvn clean package -Dmaven.test.skip=true
copy %curDir%target\*.jar %targetDir%\
copy %curDir%*.sh %targetDir%\
copy %curDir%start.bat %targetDir%\
pause