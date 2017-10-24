#!/bin/bash

mvn clean package -DskipTests
rm ../deployment/data/deploy/flows-1.0.jar

mkdir -r ../deployment/data/deploy
cp flows/target/flows-1.0.jar ../deployment/data/deploy/flows-1.0.jar