# Car Document API

## Network

  - Assumes a MongoDB database available on `car-database:21017`;
  - Receives `POST` request on the `car-service-document/car` endpoint;
  - produces and consumes `application/json` data only;
  - answers `200` if everything went well, `400` elsewhere.

## Operation : search cars

The services follows a document approach, and handle only one operation: search cars.
Therefore, the message content can only be the following search criterion (all optional):

Input:
 + city 		: string
 + dateFrom 	: string	(formatted as year-month-day, e.g. 2017-01-21 or 2017-12-09)
 + dateTo 		: string 	(formatted as year-month-day, e.g. 2017-01-21 or 2017-12-09)

Output: list of cars

A car contains the following fields:
 + company 		: string
 + city 	 	: string
 + model 		: string
 + price 		: integer
 + numberPlate	: string

### Example

Input:
```json
{
  "city": "Nice",
  "dateFrom": "2017-12-21",
  "dateTo": "2017-12-25"
}
```

Output:
```json
{
  "result": [
    {
      "company": "Tesla location",
      "city": "Nice",
      "model": "S P100D",
      "price": 35,
      "numberPlate": "888-999"
    },
    {
      "company": "Mercedes location",
      "city": "Nice",
      "model": "Class A",
      "price": 40,
      "numberPlate": "555-444"
    }
  ]
}
```