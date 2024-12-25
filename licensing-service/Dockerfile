#stage1
FROM openjdk:17-slim AS build

LABEL maintainer="Seo <seo92js@gmail.com>"

ARG JAR_FILE=build/libs/licensing-service-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} licensing-service-0.0.1-SNAPSHOT.jar

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /licensing-service-0.0.1-SNAPSHOT.jar)

#stage2
FROM openjdk:17-slim

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

VOLUME /tmp

ARG DEPENDENCY=/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /licenseing-service/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /licenseing-service

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.seojs.licensing_service.LicensingServiceApplication"]