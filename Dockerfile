FROM openjdk:8-jdk-alpine
LABEL maintainer="dev.ryulth@gmail.com"

COPY bindoong-webapi/build/libs/bindoong-webapi-*.jar /opt/bindoong/service.jar
WORKDIR /opt/bindoong

ENTRYPOINT java ${JAVA_OPTIONS} -jar service.jar