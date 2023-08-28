FROM openjdk:17
VOLUME /javarush-jira
ARG JAR_FILE=target/*.jar
COPY target /
COPY ${JAR_FILE} jira-1.0.jar
COPY resources ./resources
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/jira-1.0.jar"]
