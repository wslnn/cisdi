FROM java:8

ENV TZ=Asia/Shanghai

MAINTAINER yya <745035490@qq.com>

COPY *.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xms1024m","-Xmx1024m","/app.jar"]