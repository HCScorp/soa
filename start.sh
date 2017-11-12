#!/usr/bin/env bash

command -v docker-compose > /dev/null 2>&1 || { echo >&2 "It would be better to install 'docker-compose'."; exit 1; }

# Start docker services
docker-compose -f deployment/docker-compose.yml run --service-ports bus