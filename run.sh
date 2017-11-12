#!/usr/bin/env bash

docker run --net=host -v `pwd`/deployment/data/input/email:/email hcsoa/simulation