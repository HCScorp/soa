#!/usr/bin/env bash

echo "# Import cars"
docker-compose exec database /bin/bash -c "mongoimport --db car --collection cars --jsonArray --file /import/cars.json"

echo "# Import flights"
docker-compose exec database /bin/bash -c "mongoimport --db flight --collection flights --jsonArray --file /import/flights.json"

echo "# Import hotels"
docker-compose exec database /bin/bash -c "mongoimport --db hotel --collection hotels --jsonArray --file /import/hotels.json"

echo "# Import Approver"
docker-compose exec database /bin/bash -c "mongoimport --db btrs --collection btr --jsonArray --file /import/approver.json"