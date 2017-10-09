#!/usr/bin/env bash

command -v mvn > /dev/null 2>&1 || { echo >&2 "I require 'mvn' but it's not installed. Aborting."; exit 1; }

# Run stress and acceptation tests
cd tests
mvn clean integration-test

cd stress
mvn clean package
mvn gatling:execute

cd ../..
