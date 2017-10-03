#!/bin/sh

# Push docker images: $ PUSH=false ./build.sh

PUSH=${PUSH:-false}

build() { # $1: directory, $2: image_name
  cd $1
  docker build -t $2 .
  if [ "$PUSH" = "true" ]; then docker push $2; fi
  cd ..
}

# Compile services code
mvn -q clean package

# Build docker images
build approver  hcsoa/approver
build car       hcsoa/car
build flight    hcsoa/flight
build hotel     hcsoa/hotel
build mail      hcsoa/mail
