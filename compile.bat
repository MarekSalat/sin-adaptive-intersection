set JDK_DIR=C:\Program Files\Java\jdk1.8.0_25\bin\

mkdir build

"%JDK_DIR%javac" -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar src/utils/*.java
"%JDK_DIR%javac" -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar src/agents/*.java
"%JDK_DIR%javac" -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar src/behaviour/*.java
"%JDK_DIR%javac" -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar src/behaviour/car/*.java
"%JDK_DIR%javac" -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar src/behaviour/crossroad/*.java
"%JDK_DIR%javac" -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar src/common/*.java
"%JDK_DIR%javac" -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar src/datetypes/*.java
"%JDK_DIR%javac" -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar src/gui/*.java

mkdir bin
copy libs\jade.jar bin\jade.jar
copy src\gui\krizovatka2.jpg build\gui\krizovatka2.jpg

cd build

"%JDK_DIR%jar" cf ../bin/sin-project.jar utils/*.class agents/*.class behaviour/*.class behaviour/car/*.class behaviour/crossroad/*.class common/*.class datetypes/*.class gui/*

pause