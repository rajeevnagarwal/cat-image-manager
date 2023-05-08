#!/bin/sh
service_name="cat-image-manager"

echo "Starting ${service_name} application in local environment"
exec java -XX:+UseG1GC -jar "/apps/cat-image-manager/lib/service.jar"
