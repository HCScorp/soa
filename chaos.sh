#!/usr/bin/env bash

random() { # $1: min, $2: max
    echo $(( ( RANDOM % ( $2 - $1))  + $1 ))
}

dc(){ # $1: command, $2: service
    docker-compose -f deployment/docker-compose.yml $1 $2
}

command -v docker-compose > /dev/null 2>&1 || { echo >&2 "I require 'mvn' but it's not installed. Aborting."; exit 1; }

SERVICES=( approver car flight hotel mail car-ext hotel-ext flight-ext database )
SERVICES_SIZE=$(echo ${#SERVICES[@]})

for i in 1 2 3
do
    RANDOM_INDEX=$(random 1 $SERVICES_SIZE)
    STOP_TIME=$(random 1 4)
    WAITING_TIME=$(random 5 10)
    SERVICE=${SERVICES[$RANDOM_INDEX]}

    echo "Shutting down service $SERVICE and waiting $STOP_TIME seconds"
    dc "stop" $SERVICE

    sleep $STOP_TIME

    dc "start" $SERVICE
    echo "Waiting for $WAITING_TIME seconds ..."

    sleep $WAITING_TIME
    echo
done


