#!/usr/bin/env bash

PUSH=${PUSH:-false}
COMPOSE=true

command -v mvn > /dev/null 2>&1 || { echo >&2 "I require 'mvn' but it's not installed. Aborting."; exit 1; }
command -v docker-compose > /dev/null 2>&1 || { echo >&2 "It would be better to install 'docker-compose'."; COMPOSE=false; }

build() { # $1: directory, $2: image_name
  cd services/$1
  docker build -t $2 .
  if [ "$PUSH" = "true" ]; then docker push $2; fi
  cd ../..
}

build_docker_images() {
  echo "Build docker images..."
  # Build docker images
  if [[ "$COMPOSE" = "true" ]]; then
      docker-compose -f deployment/docker-compose.yml build
  else
      build bus       hcsoa/bus
      build approver  hcsoa/approver
      build car       hcsoa/car
      build flight    hcsoa/flight
      build hotel     hcsoa/hotel
      build mail      hcsoa/mail
  fi
}

# Build & run docker build if no error during compilation
echo "Build..."
mvn clean package && build_docker_images

# Push images
if [[ "$PUSH" == "true" && $? == 0 ]]; then
  if [[ "$COMPOSE" = "true" ]]; then
      docker-compose -f deployment/docker-compose.yml push
  else
      docker push hcsoa/bus
      docker push hcsoa/approver
      docker push hcsoa/car
      docker push hcsoa/flight
      docker push hcsoa/hotel
      docker push hcsoa/mail
  fi
fi
