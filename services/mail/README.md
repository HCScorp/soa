# Creating an RPC approver.service

  * Author: Sébastien Mosser [(email)](mosser@i3s.unice.fr)
  * Version: 2017.09
  * Reviewer: [Mireille Blay-Fornarino](blay@i3s.unice.fr)

## Creating the Maven project

We implement this approver.service using the Java language, and use Maven to support the project description. The descriptor is located in the `pom.xml` file, and inherits its content from the global one described in the `approver.service` directory (mainly the application server configuration).  The file system hierarchy is the following:

```
azrael:rpc mosser$ tree .
.
├── README.md
├── pom.xml
└── src
    └── main
        ├── java
        │   └── # approver.service code goes here
        └── webapp
            └── WEB-INF
                └── web.xml
```

## Developing the approver.service

### Declaring the interface

The approver.service declares 2 operations in the [TaxComputationService](https://github.com/polytechnice-si/5A-Microservices-Integration/blob/master/services/rpc/src/main/java/tcs/approver.service/TaxComputationService.java) interface, each one associated to a computation method for the Norwegian tax system.

```java
@WebService(name="TaxComputation", 
			targetNamespace = "http://informatique.polytech.unice.fr/soa1/cookbook/")
public interface TaxComputationService {

	@WebResult(name="simple_result")
	TaxComputation simple(@WebParam(name="simpleTaxInfo") SimpleTaxRequest request);

	@WebResult(name="complex_result")
	TaxComputation complex(@WebParam(name="complexTaxInfo") AdvancedTaxRequest request);
}
```

The associated request and response classes are available in the [approver.data](https://github.com/polytechnice-si/5A-Microservices-Integration/tree/master/services/rpc/src/main/java/tcs/approver.data) package.

### Implementing the interface

The interface is implemented in the [TaxComputationImpl](https://github.com/polytechnice-si/5A-Microservices-Integration/blob/master/services/rpc/src/main/java/tcs/approver.service/TaxComputationImpl.java) class.

## Starting the approver.service

  * Compiling: `mvn clean package` will create the file `target/tcs-approver.service-rpc.war`
  * Running: `mvn tomee:run` will deploy the created `war` inside a TomEE+ server, available on `localhost:8080`
  * The WSDL interface is available at [http://localhost:8080/tcs-approver.service-rpc/ExternalTaxComputerService?wsdl](http://localhost:8080/tcs-approver.service-rpc/ExternalTaxComputerService?wsdl)

