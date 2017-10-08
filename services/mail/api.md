# Mail RPC API

## Network
  - Receives `POST` request on the `mail-service-rpc` endpoint;
  - produces and consumes `application/json` data only;
  - answers `200` if everything went well, `400` elsewhere.

## Operation : send mail

The services follows a RPC approach, and handle only one operation: send a mail.

Input: Mail Request
 + sender 		 : string
 + recipient 	 : string
 + object 		 : string 
 + message     : string

Output: Mail Status
 + cause       : string

### Example

Input:
```json
{
  "sender": "supercoolmail@gmail.com", 
  "recipient": "superuncoolmail@gmail.com",
  "object": "Asking for vacation", 
  "message": "Hi ..."
}
```

Output:
```json
{
  "cause": "ok"
}
```
