FROM registry.cn-qingdao.aliyuncs.com/metersphere/alpine-openjdk17-jre
LABEL maintainer="FIT2CLOUD <support@fit2cloud.com>"

ARG MS_VERSION=dev

ENV JAVA_MAIN_CLASS=metersphere.DatabaseServerApplication
ENV MS_VERSION=${MS_VERSION}
ENV JAVA_OPTIONS="-Dfile.encoding=utf-8 -Djava.awt.headless=true --add-opens java.base/jdk.internal.loader=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED"

ADD database-server-springboot-jar-with-dependencies.jar app.jar

EXPOSE 8088

ENTRYPOINT ["java","-jar","/app.jar"]
