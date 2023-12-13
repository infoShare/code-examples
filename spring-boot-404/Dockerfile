FROM amazoncorretto:21

ADD target/spring-boot-404-0.0.1-SNAPSHOT.jar spring-boot-404.jar

EXPOSE 8080

ARG JAVA_OPTS="-XX:+UseZGC -XX:+ZGenerational -Xms128m -Xmx256m"

ENV JAVA_TOOL_OPTIONS "$JAVA_OPTS"

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "spring-boot-404.jar"]

