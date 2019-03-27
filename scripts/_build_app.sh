#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

${DIR}/_stop_db.sh
${DIR}/_start_db.sh

${DIR}/../gradlew build
