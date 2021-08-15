#!/bin/bash

PORT=8080
function usage()
{
    cat <<EOM
Usage: $0 [options]
Options:
 -p, --port                   Specify container external port (default 8080)
 --active-profile             Specify spring active profile
 --aws-access-key-id          Specify AWS_ACCESS_KEY_ID
 --aws-secret-access-key      Specify AWS_SECRET_ACCESS_KEY
EOM

    exit 1
}
function set_options()
{
    while [ "${1:-}" != "" ]; do
        case "$1" in
            -p | --port)
                shift
                PORT=$1
                ;;
            --active-profile)
                shift
                ACTIVE_PROFILE=$1
                ;;
            --aws-access-key-id)
                shift
                AWS_ACCESS_KEY_ID=$1
                ;;
            --aws-secret-access-key)
                shift
                AWS_SECRET_ACCESS_KEY=$1
                ;;
            *)
                usage
                ;;
        esac
        shift
    done
}

function check() {
    if [ -z "${ACTIVE_PROFILE}" ]; then
      echo "--active-profile required"
      exit 1
    fi
    if [ -z "${AWS_ACCESS_KEY_ID}" ]; then
      echo "--aws-access-key-id required"
      exit 1
    fi
    if [ -z "${AWS_SECRET_ACCESS_KEY}" ]; then
      echo "--aws-secret-access-key required"
      exit 1
    fi
}

set_options "$@"
check

JAVA_OPTIONS="
  -Djava.security.egd=file:/dev/./urandom \
  -Dspring.profiles.active=${ACTIVE_PROFILE} \
  -Daws.accessKeyId=${AWS_ACCESS_KEY_ID} \
  -Daws.secretAccessKey=${AWS_SECRET_ACCESS_KEY} \
"

docker build --tag bindoong-webapi .
docker run -d -p "${PORT}":8080 -e JAVA_OPTIONS="${JAVA_OPTIONS}" bindoong-webapi
