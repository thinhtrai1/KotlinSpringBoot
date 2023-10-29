FROM openjdk:8-jdk-alpine AS build
COPY . .
RUN ./gradlew build

FROM openjdk:8-jdk-alpine
COPY --from=build /build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]