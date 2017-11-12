# Integration part of the Travel Angency project.



## Bus Communication

All the protocol features are descibes such as :


### Explanation

#### Provide Explanation

To provide an explanation, you need :
* The travelID that you received from the travel department.
* The explanation of the user : why he exceed the initial budget.

The format is JSON.

Send a POST request with the proper Json in the request's body


##### Exemple 

```json
{
  "id" : "id_travelID_in_MongoDB",
  "explanation" : "A bear took me as it own son."
}
```

#### Accept or Refuse the explanation.

If you're the manager, to accept or reject the explanation that your employee send to you.

You need :
* The travelID that describe the travel.
* The acceptRefund : a boolean that describe your answer.

The format is JSON.

Send a POST request with the proper Json wrote in the request.

##### Exemple 

```json
{
  "id" : "id_travelID_in_MongoDB",
  "acceptRefund" : true
}
```