FROM maven:3.8.1-jdk-11-slim AS build

WORKDIR /build

COPY ./pom.xml .
COPY ./src src

RUN mvn package -U

FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine-slim as release

COPY --from=build /build/target/partners.jar /app.jar

COPY docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh

ENTRYPOINT ["/docker-entrypoint.sh"]