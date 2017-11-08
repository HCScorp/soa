# SCBBT

## How to deploy

Run this command:
```bash
docker-compose run -p 8181:8181 bus
```
Every exposed web services will be available at the address:
```
http://localhost:8181/camel/rest
```

## How to feed HCS document services database
Run this command:
```bash
./import_database.sh
```
This will load the JSON files contained by the "import" directory.
* If you want to customize the data, you need to update the JSON files in the "import" directory. 
* If you want to customize the script, to feed only one collection for example, you need to update the "import_database.sh" script accordingly.