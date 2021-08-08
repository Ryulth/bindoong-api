#!/bin/bash

PORT=8080
function usage()
{
    cat <<EOM
Usage: $0 [options]
Options:
 -p, --port            Specify container external port (default 8080)
 --active-profile      Specify spring active profile
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
            *)
                usage
                ;;
        esac
        shift
    done
}

set_options "$@"

JAVA_OPTIONS="
  -Djava.security.egd=file:/dev/./urandom \
  -Dspring.profiles.active=${ACTIVE_PROFILE} \
"

docker build --tag bindoong-webapi .
docker run -d -p "${PORT}":8080 -e JAVA_OPTIONS="${JAVA_OPTIONS}" bindoong-webapi
