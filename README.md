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
 
 - quickly describe the interfaces
 - explain your design choices associated to these interfaces.

## Associated services
TODO en anglais

Choix de regroupement/séparation des problématique en service
Pourquoi on a pas regrouper flight, car et hotel ? Pourquoi on fait un mail a part et par intégré au service d'approval ? pourquoi on a réuni la submission avec la gestion de son approvement ?
 
Approver:
(On a regroupé les deux problématiques au sein d’un meme service car le second et la suite du flow du premier, en effet l’approval d’une request utilise la ressource déjà existante, il y accède donc et en créer un summary.)
--> Nous avons regroupé la problématique de soumission d'une requête de business travel avec celle de l'envoi d'un récapitulatif car la seconde est la suite logique de la première, de plus la requête est une ressource qui va être envoyé puis visualisé par le manager et ensuite mise à jour (elle passera de "en attente" à "approuvé" ou "rejeté" par exemple)
...

## Protocol choices
TODO en anglais

Choix de protocole (REST, Document et RPC)


### Flight, Hotel and Car
TODO
 + Contrat flexible : de nombreux paramètres, optionnels ou non, avec une probabilité d'évolution très élevée. On souhaite pouvoir étendre ces paramètres en minimisant l'impact côté client, le message non structuré permet de garder de cette souplesse.
 + 

RPC n’aurait pas été une bonne solution car ce qu’on souhaite mettre à disposition à vocation à changer régulièrement..
RPC pas possible puisqu'on pourrait pas mettre autant de paramètre en plus du fait qu'ils soient tous optionnels, il faudrait créer 48564 méthodes c'est pas possible
REST non plus car une requete GET sera limité en taille d'URL et donc les filtres pourrait a terme etre limité, ou alors faudrait faire une requete POST mais ce serait aller directement en enfer

-- Hotel
Ordered by price viol la condition du REST.

Nécessité d’ordonner les résultats par prix -> Document
Pas de REST car non adapté 

-- Car

variabilité

REST : pas de contrainte d’ordre de tri des resultats, peu de paramètre et qui ne 

Le choix de REST peut se justifier (accès en GET à la ressource location de voiture), mais ce choix nous bloquerai et ne nous permettrait pas de proposer des évolutions comme c’est le cas pour les deux premiers (
tri par prix, par préférence, etc.)

## Mail
TODO
On ne manipule pas une ressource, on ne la récupère pas, on ne la met pas à jour, on ne la supprime pas. On effectue une action direct. C'est pourquoi le modèle RPC parait cohérent avec ce service.

 + Contrat figé : l'envoi de mail n'a pas évolué depuis des dizaines d'années
 + Met à disposition une action bien précise qui correspond à la procédure d'envoi de mail tel qu'implémenté dans le service

Dans le cas ou les actions mises à dispositions acceptent un nombre fixe de paramètre qui ne sera pas améné à changer, le protocole RPC prend tout son sens. Sinon, il faut rechercher une approche plus flexible tel que REST ou Document.

## Approver
TODO
Pourquoi ? RPC pas adapté car les paramètres peuvent et vont changer (vol, hôtel, voiture, tous sont facultatifs)
Resources adapté : on va submit une nouvelle ressources, qui sera identifié et qu’on pourra lire par son uri


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

