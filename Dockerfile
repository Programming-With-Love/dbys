FROM openjdk:8-jre-alpine
LABEL maintainer="db225@qq.com"

RUN echo "Asia/Shanghai" > /etc/timezone
ADD dbys.jar /bin/app/dbys.jar
ADD /lib /bin/app/lib
CMD exec java -Dloader.path="/bin/app/lib" -jar /bin/app/dbys.jar