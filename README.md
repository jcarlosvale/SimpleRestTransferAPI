# simpleRestTransferAPI
This project consists of a RESTful API example for money transfers between accounts.

Frameworks used:
* Spark Java (http://sparkjava.com/)

Tests uses:
* `deltaspike-cdictrl` - CDI weld container for @Inject
* `H2` - in memory database for testing JPQL, [Query Method Expressions](https://deltaspike.apache.org/documentation/data.html#QueryMethodExpressions)


To run tests:

    mvn test


DeltaSpike Data is alternative for Spring Data. 