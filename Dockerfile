FROM amazoncorretto:11-alpine
ADD target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]