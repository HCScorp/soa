# Flight Document API

## Network

  - Assumes a MongoDB database available on `flight-database:21017`;
  - Receives `POST` request on the `flight-service-document/flight` endpoint;
  - produces and consumes `application/json` data only;
  - answers `200` if everything went well, `400` elsewhere.

## Operation : search flights

The services follows a document approach, and handle only one operation: search flights.
Therefore, the message content can only be the following search criterion (all optional):

Input: search criterion
 + origin 		 : string
 + destination 	 : string
 + date 		 : string 	(formatted as year-month-day, e.g. 2017-01-21 or 2017-12-09)
 + timeFrom		 : string 	(formatted as hour-minute-second, e.g. 08-30-00)
 + timeTo		 : string 	(formatted as hour-minute-second, e.g. 18-45-00)
 + journeyType 	 : string 	(can be DIRECT or INDIRECT)
 + maxTravelTime : integer 	(in minutes)
 + category 	 : string 	(can be ECO, ECO_PREMIUM, BUSINESS, FIRST)
 + airline 		 : string
 + order		 : string 	(can be ASCENDING or DESCENDING, ASCENDING by default)

Output: list of flights ordered by prices

A flight contains the following fields:
 + origin 		 : string
 + destination 	 : string
 + date 		 : string 	(formatted as year-month-day, e.g. 2017-01-21 or 2017-12-09)
 + time 		 : string 	(formatted as hour-minute-second, e.g. 15-30-00)
 + price 	 	 : integer 	(in euro)
 + journeyType 	 : string 	(can be DIRECT or INDIRECT)
 + duration 	 : integer 	(in minutes)
 + category 	 : string 	(can be ECO, ECO_PREMIUM, BUSINESS, FIRST)
 + airline 		 : string

### Example

Input:
```json
{
  "origin": "Nice",
  "destination": "Paris",
  "date": "2017-08-14",
  "timeFrom": "08-00-00",
  "timeTo": "14-30-00",
  "journeyType": "DIRECT",
  "maxTravelTime": 120,
  "category": "ECO",
  "airline": "Air France",
  "order": "ASCENDING"
}
```

Output:
```json
{
  "result": [
    {
      "origin": "Nice",
      "destination": "Paris",
      "date": "2017-08-14",
      "time": "12-30-00",
      "price": "89",
      "journeyType": "DIRECT",
      "duration": 92,
      "category": "ECO",
      "airline": "Air France"
    },
    {
      "origin": "Nice",
      "destination": "Paris",
      "date": "2017-08-14",
      "time": "08-45-00",
      "price": "63",
      "journeyType": "DIRECT",
      "duration": 105,
      "category": "ECO",
      "airline": "EasyJet"
    }
  ]
}
```