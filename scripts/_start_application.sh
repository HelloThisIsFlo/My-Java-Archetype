#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

SPRING_PROFILE=$1
JAR_LOCATION=${DIR}/../build/libs/myarchetype-0.0.1-SNAPSHOT.jar

# Start DB and build app
${DIR}/_build_app.sh

# Run Application
java -Dspring.profiles.active=${SPRING_PROFILE} -jar ${JAR_LOCATION}

# Stop DB after app finished running
${DIR}/_stop_db.sh

