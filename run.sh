#!/usr/bin/env bash

cd deployment
./import_database.sh
cd ..

docker run --net=host -v `pwd`/deployment/data/input/email:/email hcsoa/simulation