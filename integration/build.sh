#!/bin/bash

mvn clean package -DskipTests
cp flows/target/flows-1.0.jar ../deployment/data/deploy