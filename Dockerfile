FROM eclipse-temurin:21-jdk AS builder
WORKDIR /workspace
# ARG APP_NAME  # for local build
# COPY app/${APP_NAME}/build/libs/application.jar ./application.jar  # for local build
# RUN java -Djarmode=tools -jar application.jar extract --layers --launcher  # for local build
COPY app/api-app/build/libs/application.jar application.jar
RUN java -Djarmode=tools -jar application.jar extract --layers --launcher

FROM eclipse-temurin:21-jre

ARG SPRING_PROFILES_ACTIVE
ARG DATABASE_URL
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD
ARG APP_CORS_ENABLED
ARG APP_CORS_ALLOWED_ORIGINS
ARG JWT_ACCESS_TOKEN_SECRET
ARG JWT_ACCESS_TOKEN_EXPIRATION
ARG JWT_REFRESH_TOKEN_SECRET
ARG JWT_REFRESH_TOKEN_EXPIRATION
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USERNAME=${DATABASE_USERNAME}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
ENV APP_CORS_ENABLED=${APP_CORS_ENABLED}
ENV APP_CORS_ALLOWED_ORIGINS=${APP_CORS_ALLOWED_ORIGINS}
ENV JWT_ACCESS_TOKEN_SECRET=${JWT_ACCESS_TOKEN_SECRET}
ENV JWT_ACCESS_TOKEN_EXPIRATION=${JWT_ACCESS_TOKEN_EXPIRATION}
ENV JWT_REFRESH_TOKEN_SECRET=${JWT_REFRESH_TOKEN_SECRET}
ENV JWT_REFRESH_TOKEN_EXPIRATION=${JWT_REFRESH_TOKEN_EXPIRATION}

WORKDIR /app
COPY --from=builder /workspace/application/dependencies/ ./
COPY --from=builder /workspace/application/spring-boot-loader/ ./
COPY --from=builder /workspace/application/snapshot-dependencies/ ./
COPY --from=builder /workspace/application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
