# Importing JDK and copying required files
FROM openjdk:21-jdk AS build
WORKDIR /coached
COPY pom.xml .
COPY src src

# Copy Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk
VOLUME /tmp
ADD target/coached-0.0.1-SNAPSHOT.jar coached.jar
ENTRYPOINT ["java","-jar","/coached.jar"]