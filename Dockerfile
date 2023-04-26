FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:12-jdk-alpine
EXPOSE 8085:8085
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/browser_ms-0.0.1.jar
RUN mkdir /src
RUN mkdir /src/main
RUN mkdir /src/main/resources
COPY /src/main/resources/stopWords.txt /src/main/resources
ENTRYPOINT ["java","-jar","/app/browser_ms-0.0.1.jar"]

