FROM maven:3.9.9-eclipse-temurin-21-alpine as build

WORKDIR /app

COPY pom.xml ./pom.xml

RUN mvn dependency:tree

COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:21.0.4_7-jre-noble

COPY --from=build app/target/*.jar claribills.jar

EXPOSE 8080

CMD ["java", "-jar", "claribills.jar"]