FROM amazoncorretto:17-alpine3.16-jdk
EXPOSE 8080
WORKDIR /app
COPY /build/libs/k8s-test-0.0.1.jar k8s-test-0.0.1.jar
CMD ["java", "-jar", "k8s-test-0.0.1.jar"]