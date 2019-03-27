#!/bin/bash

DB_CONTAINER_NAME=my-java-arch-postgres
LOCAL_PORT=1234
docker run \
    --rm \
    -d \
    --name ${DB_CONTAINER_NAME} \
    -e POSTGRES_USER=dev \
    -e POSTGRES_PASSWORD=dev \
    -e POSTGRES_DB=javaarchetype \
    -p ${LOCAL_PORT}:5432 \
    postgres
