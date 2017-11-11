# Approver REST API

## Network

  - Assumes a MongoDB database available on `database:21017`;
  - Receives requests on the `approver-service-rest` endpoint;
  - produces and consumes `application/json` data only;
  - answers `200` if everything went well, `400` elsewhere.

## Ressource Business Travel Request (BTR)

A BTR contains the following fields:
  + id : int
  + status  : string (WAITING/APPROVED/DENIED)
  + flights : array[flight]
  + hotels  : array[hotel]
  + cars    : array[car]
  
### GET
Retrieve a BTR giving his id

Output: Business Travel Request 
  

#### Example

Input:
```
http://localhost:8080/approver-service-rest/btr/1
```

Output:
```json
{
  "id": 1, 
  "status": "WAITING", 
  "cars": [], 
  "hotels": [],
  "flights": [
	{
          "origin": "Nice",
          "destination": "Paris",
          "date": "2017-08-14",
          "time": "12:30:00",
          "price": 89.0,
          "journeyType": "DIRECT",
          "duration": 92,
          "category": "ECO",
          "airline": "Air France"
        },
        {
          "origin": "Nice",
          "destination": "Paris",
          "date": "2017-08-14",
          "time": "08:45:00",
          "price": 63.0,
          "journeyType": "DIRECT",
          "duration": 105,
          "category": "ECO",
          "airline": "EasyJet"
        }
  ]
}
```

### POST
Submit a new BTR

Input:
```json
{
  "cars": [], 
  "hotels": [],
  "flights": [
	{
          "origin": "Nice",
          "destination": "Paris",
          "date": "2017-08-14",
          "time": "12:30:00",
          "price": 89.0,
          "journeyType": "DIRECT",
          "duration": 92,
          "category": "ECO",
          "airline": "Air France"
        },
        {
          "origin": "Nice",
          "destination": "Paris",
          "date": "2017-08-14",
          "time": "08:45:00",
          "price": 63.0,
          "journeyType": "DIRECT",
          "duration": 105,
          "category": "ECO",
          "airline": "EasyJet"
        }
  ]
}
```

### PUT
Update a BTR.

*Note :* for now, we only update the status field, but to be REST compliant we will update every fields

```
http://localhost:8080/approver-service-rest/btr/1?status=APPROVED
```


