FROM openjdk:13-alpine
EXPOSE 5432
EXPOSE 8888
WORKDIR /
ADD build/libs/covid19tracker.jar covid19tracker.jar
CMD ["java", "-jar", "covid19tracker.jar"]