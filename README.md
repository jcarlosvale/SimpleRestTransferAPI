# simpleRestTransferAPI
This project consists of a RESTful API example for money transfers between accounts.

Frameworks used:
* Spark Java (http://sparkjava.com/)
* Weld (https://weld.cdi-spec.org/)

Tests uses:
* `deltaspike-cdictrl` - CDI weld container for @Inject
* `H2` - in memory database for testing JPQL, [Query Method Expressions](https://deltaspike.apache.org/documentation/data.html#QueryMethodExpressions)

##Classes
* Main class (executable): simpleRestTransferAPI.App
    
## Specification
* simpleRestTransferAPI.App
        *Class to start the application and configure the REST endpoints by routes.*
    

To run tests:

    mvn test


DeltaSpike Data is alternative for Spring Data. 