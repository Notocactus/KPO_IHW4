FROM openjdk:17.0.2-jdk-slim-buster

MAINTAINER Victoria Khan
#аналогичный докерфайл должен быть в твоём сервисе
#запускаешь в maven clean, затем compile, потом package – появится target с jar-файлом
#меняешь внизу AuthApi-0.0.1-SNAPSHOT.jar на название jar файла, создавшегося выше
ADD /target/AuthApi-0.0.1-SNAPSHOT.jar AuthApi-0.0.1-SNAPSHOT.jar
#если будет с портами проблема, и в compose не заробит, то можно попробовать тут потыркать, но я бы не стал, лучше порты в compose поменять
EXPOSE 8080:8080
#здесь указываешь также вместо AuthApi jar файл своего сервиса
ENTRYPOINT ["java", "-jar", "AuthApi-0.0.1-SNAPSHOT.jar"]