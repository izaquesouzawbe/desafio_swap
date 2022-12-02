FROM openjdk:11

WORKDIR /app

COPY ./target/*.jar ./root.jar

EXPOSE 8080

ENTRYPOINT java -jar root.jar