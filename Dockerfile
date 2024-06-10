# syntax=docker/dockerfile:1
FROM openjdk:17-oracle

WORKDIR /opt/bs-reloaded-backend
COPY target/sky-user-manager_latest.jar sky-user-manager.jar

ENTRYPOINT ["java","-jar","sky-user-manager.jar"]
