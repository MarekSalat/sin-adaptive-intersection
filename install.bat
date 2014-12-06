
mkdir libs


powershell.exe -Command "(new-object System.Net.WebClient).DownloadFile('http://www.stud.fit.vutbr.cz/~xseptu00/sin/jade.jar','.\libs\jade.jar')"

::powershell -Command "Invoke-WebRequest http://www.foo.com/package.zip -OutFile package.zip"

pause
