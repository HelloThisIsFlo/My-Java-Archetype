#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

${DIR}/scripts/_stop_db.sh
${DIR}/scripts/_start_db.sh
${DIR}/gradlew test
