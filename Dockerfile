FROM openjdk:12-jdk-alpine

RUN apk add --no-cache bash

WORKDIR /browser_ms

CMD ./gradlew run