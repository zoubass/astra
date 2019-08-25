FROM openjdk:8-jdk-alpine
COPY build/libs/astra-0.0.1-SNAPSHOT.jar /astra.jar
VOLUME /tmp
ADD src/main/resources/application.properties /application.properties
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom --spring.config.location=classpath:file:/application-properties","-jar","/astra.jar"]
EXPOSE 8090