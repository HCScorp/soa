FROM openjdk:jre-alpine

WORKDIR /

COPY ./target/simulation-jar-with-dependencies.jar ./simulation.jar

CMD ["java", "-jar", "./simulation.jar"]
