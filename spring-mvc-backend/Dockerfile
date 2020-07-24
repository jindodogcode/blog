FROM openjdk:11.0-jre-slim
RUN adduser --system --group spring
USER spring:spring
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-cp", "app:app/lib/*", "dev.mkennedy.blog.BlogApplication"]
EXPOSE 8080
