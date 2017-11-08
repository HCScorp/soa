# Hotel Document API

## Network

  - Assumes a MongoDB database available on `hotel-database:21017`;
  - Receives `POST` request on the `hotel-service-document/hotel` endpoint;
  - produces and consumes `application/json` data only;
  - answers `200` if everything went well, `400` elsewhere.

## Operation : search hotels

The services follows a document approach, and handle only one operation: search hotels.
Therefore, the message content can only be the following search criterion (all optional):

Input: search criterion
 + city 		: string
 + dateFrom 	: string	(formatted as year-month-day, e.g. 2017-01-21 or 2017-12-09)
 + dateTo 		: string 	(formatted as year-month-day, e.g. 2017-01-21 or 2017-12-09)
 + order		: string 	(can be ASCENDING or DESCENDING, ASCENDING by default)

Output: list of hotels ordered by price

An hotel contains the following fields:
 + name 		: string
 + city 	 	: string
 + zipCode 		: string
 + address		: string
 + nightPrice	: double	(in euro)

### Example

Input:
```json
{
  "city": "Nice",
  "dateFrom": "2017-08-21",
  "dateTo": "2017-08-25",
  "order": "ASCENDING"
}
```

Output:
```json
{
  "result": [
	{
      "name": "Le Méridien",
      "city": "Nice",
      "zipCode": "06046",
      "address": "1, promenade des Anglais",
      "nightPrice": 200.0
	},
	{
      "name": "Négresco",
      "city": "Nice",
      "zipCode": "06000",
      "address": "37, promenade des Anglais",
      "nightPrice": 300.0
	}
  ]
}
```