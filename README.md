# SOA : The Virtual Travel Agency

  * Authors: 
	+ César Collé [(email)](cesar.colle@etu.unice.fr)
	+ Loïck Mahieux [(email)](loick.mahieux@etu.unice.fr)
	+ Loris Friedel[(email)](loris.friedel@etu.unice.fr)
	+ Thomas Munoz[(email)](thomas.munoz@etu.unice.fr)

## Case study: The Virtual Travel Agency

We consider here an ecosystem of services dedicated to support an employee when travelling for business purpose. The services must support the following features:
 + Propose alternative flights for a given destination and a given date, ordered by price and based on travellers preferences (e.g., only direct flight, given max travel time);
 + Propose alternatives hotels for a given destination and a given date, ordered by prices;
 + Propose alternative car rentals at a given place and for a given duration;
 + Submit a business travel (a description of the different tickets and/or hotel nights to buy) to a manager, and wait for approval;
 + Send a summary of an approved business travel by email.

## Associated services

We had two viable choices for the first three features : create one web service for each or one web service for all.
We decided to go for one web service for each because they are only related in the way they are requested and we wanted to isolate them by concerns. Each will have its own database so that we keep data isolated by concerns as well. 

For the two remaining features, we decided to group the submission of a business travel request and the approval because approving a request is the next logical step after a submission so we keep the workflow into one piece.
Then, we chose to extract the mail system so that we had all the business travel logic on one side and the result communication (mail) on the other side, that can be easily isolated because it has nothing to do with the rest.

## Protocol choices

### Flight, Hotel and Car : Document

[Flight API documentation](services/flight/api.md)

[Hotel API documentation](services/hotel/api.md)

[Car API documentation](services/car/api.md)

We decided to use the Document protocol for the Flight, Hotel and Car web services.
We needed a flexible contract, that can handle a lot of parameters, optionnal or not, and with a high chance of futur changes. 
We also needed to be able to extend these parameters with the minimum impact on client side and this was possible with the unstructured message.

We could have used the REST protocol but it was more limited as it uses a GET request to search for flights, hotels or cars and URL have a limited number of characters.
RPC protocol was not an option because we couldn't have that much optional parameters, we would have been forced to create a huge number of different method. We could update it easily as well because it is static by design.

This was the choice that gave us the least amount of constraint for future extension of each services.

Note: the car rental feature is described with minimum amount of search criterion but in a real world it would be exactly the same as the flight service. Same goes for the hotel feature.

## Mail : RPC

[Mail API documentation](services/mail/api.md)

For the mail service, we needed something fit for a frozen contract.
This feature is not about manipulating a resource, it is about performing a simple action: send an email.
Moreover, sending an email is an old process, that has not evolved for dozens of years and that always keep the same parameters, frozen contract).
This action is the mirror of its implementation in the web service.

We had a service that takes a fixed amount of parameters that will not change for the next twenty years and that is about a simple action, that is why RPC is the most coherent choice for us.

## Approver : REST

[Approver API documentation](services/approver/api.md)

It is easy to see the business travel request as a resource: it can be created (submission), viewed (manager review) updated (manager approval) and deleted (archiving eslewhere).
This business travel request is unique, identified and reachable by its URI.
This is why we chose to use the REST protocol for this service.

We could have used the Document protocol but what is the point of using an unstructured message when the business logic is structured and the REST protocol give us adapted tools for that situation.
RPC was the least prefered option because there is optional parameters to take into account and we would have lost the resource meaning of a business travel request as well.

## Technological Stack

  * Service Development: 
    * Application server: [TomEE+](http://openejb.apache.org/apache-tomee.html)
    * REST-based service stack: JAX-RS
    * SOAP-based service stack: JAX-WS
  * Storage: 
    * Database: [MongoDB](https://www.mongodb.com) (3.5)
  * Deployment: 
    * [Docker Community Engine](https://www.docker.com/community-edition) (17.09.0-ce)
    * [Docker Compose](https://docs.docker.com/compose/) (1.16.1)
  * Testing:
    * Acceptance testing: [Cucumber](https://cucumber.io/) 
    * Stress testing: [Gatling](http://gatling.io/)

