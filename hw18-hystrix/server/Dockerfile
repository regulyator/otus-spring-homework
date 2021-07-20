FROM gradle:6.9.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM bellsoft/liberica-openjdk-centos:11

EXPOSE 8099

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/library-server.jar

ENTRYPOINT ["java","-jar","/app/library-server.jar"]