FROM gradle:jdk21 AS build-env
ENV DIR=/app
WORKDIR ${DIR}
COPY . $DIR
RUN gradle bootJar

FROM eclipse-temurin:21-jdk-ubi9-minimal
COPY --from=build-env /app/build/libs/pdf-generator-api-*-SNAPSHOT.jar /pdf-generator-api.jar
CMD ["java", "-jar", "/pdf-generator-api.jar"]
