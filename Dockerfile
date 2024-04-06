FROM amazoncorretto:17
ARG JAR_FILE=target/test-1.jar
WORKDIR /opt/app
COPY $JAR_FILE app.jar
ENTRYPOINT ["java","-jar","app.jar"]