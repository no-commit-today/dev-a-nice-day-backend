FROM eclipse-temurin:21-jdk AS builder
WORKDIR /workspace
ARG APP_NAME=techswipe-api-app
COPY app/${APP_NAME}/build/libs/application.jar ./application.jar
RUN java -Djarmode=tools -jar application.jar extract --layers --launcher

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /workspace/application/dependencies/ ./
COPY --from=builder /workspace/application/spring-boot-loader/ ./
COPY --from=builder /workspace/application/snapshot-dependencies/ ./
COPY --from=builder /workspace/application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
