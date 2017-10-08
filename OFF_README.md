# SOA, Microservices & Integration

  * Authors: 
	+ César Collé [(email)](cesar.colle@etu.unice.fr)
	+ Loïck Mahieux [(email)](loick.mahieux@etu.unice.fr)
	+ Loris Friedel[(email)](loris.friedel@etu.unice.fr)
	+ Thomas Munoz[(email)](thomas.munoz@etu.unice.fr)
  * Version: 2017.10 
  * Reviewer: [Sébastien Mosser](mosser@i3s.unice.fr)

## Case study: The Virtual Travel Agency

We consider here an ecosystem of services dedicated to support an employee when travelling for business purpose. The services must support the following features:
 + Propose alternative flights for a given destination and a given date, ordered by price and based on travellers preferences (e.g., only direct flight, given max travel time);
 + Propose alternatives hotels for a given destination and a given date, ordered by prices;
 + Propose alternative car rentals at a given place and for a given duration;
 + Submit a business travel (a description of the different tickets and/or hotel nights to buy) to a manager, and wait for approval;
 + Send a summary of an approved business travel by email.

### Personas & Stories

Work in progress.

## Development Timeline

### Phase #1: Deploying services

  * Service development
    * [x] [Creating the Mail Service as an RPC service](https://github.com/thomasmunoz13/soa/services/mail/README.md);
    * [x] [Creating the Flight Service as a Document service](https://github.com/thomasmunoz13/soa/services/flight/README.md);
	* [x] [Creating the Hotel Service as a Document service](https://github.com/thomasmunoz13/soa/services/hotel/README.md);
	* [x] [Creating the Car Service as a Document service](https://github.com/thomasmunoz13/soa/services/car/README.md);
    * [x] [Creating the Approver Service as a Ressource (REST) service](https://github.com/thomasmunoz13/soa/services/approver/README.md).
  * Service deployment
    * [x] [Using containers to deploy services](https://github.com/thomasmunoz13/soa/deployment/Docker.md);
    * [x] [Composing containers into a global system](https://github.com/thomasmunoz13/soa/deployment/README.md).
    * [x] [Monitoring containers](https://github.com/thomasmunoz13/soa/monitoring/README.md)
  * Service testing
    * [x] [Acceptance testing using scenarios](https://github.com/thomasmunoz13/soa/tests/acceptation/README.md)
    * [x] [Stress testing](https://github.com/thomasmunoz13/soa/stress/readme.md)

### Phase #2: Integrating services

  * Message broker
    * [ ] Using asynchronous messages to assemble services;
    * [ ] Monitoring the broker 
  * Legacy integration
    * [ ] Leveraging adapters to integrate legacy systems together

## Technological Stack

  * Service Development: 
    * Application server: [TomEE+](http://openejb.apache.org/apache-tomee.html)
    * REST-based service stack: JAX-RS
    * SOAP-based service stack: JAX-WS
  * Integration: 
    * Enterprise Service Bus: [Apache Service Mix](http://servicemix.apache.org/) (7.0.1)
    * Message Broker: [Apache ActiveMQ](http://activemq.apache.org/)
    * Routing: [Apache Camel](http://camel.apache.org/) (2.19.2)
  * Storage: 
    * Database: [MongoDB](https://www.mongodb.com) (3.5)
  * Deployment: 
    * [Docker Community Engine](https://www.docker.com/community-edition) (17.09.0-ce)
    * [Docker Compose](https://docs.docker.com/compose/) (1.16.1)
  * Testing:
    * Acceptance testing: [Cucumber](https://cucumber.io/) 
    * Stress testing: [Gatling](http://gatling.io/)
  * Monitoring:
    * ESB: [HawtIO](http://hawt.io/)
    * Services: [cAdvisor](https://github.com/google/cadvisor)  

