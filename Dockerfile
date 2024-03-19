FROM openjdk:21-jdk-bullseye

#Create a directory for our application
WORKDIR /app

COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

#compile jar file in target/day17-workshop-0.0.1-SNAPSHOT.jar 
# window need the follow to make it executable
RUN chmod a+x mvnw
RUN ./mvnw package -Dmaven.test.skip=true
#RUN /app/mvnw package -Dmaven.test.skip=true

##run
ENV WEATHER_KEY=abc123 PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar target/day17-workshop-0.0.1-SNAPSHOT.jar