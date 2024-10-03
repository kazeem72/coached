FROM openjdk:21-jdk
VOLUME /tmp
ADD target/coached-0.0.1-SNAPSHOT.jar coached.jar
ENTRYPOINT ["java","-jar","/coached.jar"]