# Creating a REST service

  * Authors: 
	+ César Collé [(email)](cesar.colle@etu.unice.fr)
	+ Loïck Mahieux [(email)](loick.mahieux@etu.unice.fr)
	+ Loris Friedel[(email)](loris.friedel@etu.unice.fr)
	+ Thomas Munoz[(email)](thomas.munoz@etu.unice.fr)
  * Version: 2017.10 
  * Reviewer: [Sébastien Mosser](mosser@i3s.unice.fr)

## Starting the service

  * Compiling: `mvn clean package` will create the file `target/tcs-service-rpc.war`
  * Running: `mvn tomee:run` will deploy the created `war` inside a TomEE+ server, available on `localhost:8080`
  * The WSDL interface is available at the address specified by TomEE when the server is up and running.

## Documentation

[Approver API documentation](api.md)