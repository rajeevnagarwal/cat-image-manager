FROM adoptopenjdk/openjdk8-openj9:alpine-slim

ENV PROJECTNAME="cat-image-manager"
ENV JAR_FILE="cat-image-manager-0.0.1-SNAPSHOT.jar"
ENV TINI_VERSION v0.19.0

COPY start.sh /apps/$PROJECTNAME/bin/start.sh

COPY target/${JAR_FILE} /apps/${PROJECTNAME}/lib/service.jar

RUN chmod +x /apps/$PROJECTNAME/bin/start.sh

RUN apk add --update tini

EXPOSE 8081

ENTRYPOINT ["tini","--","/apps/cat-image-manager/bin/start.sh"]