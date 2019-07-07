# simpleRestTransferAPI
by João Carlos (https://www.linkedin.com/in/joaocarlosvale/)

This project consists of a RESTful API example for money transfers between accounts, generating a **Fat Jar**.

Frameworks used:
* Spark Java (http://sparkjava.com/): micro web framework.
* Weld (https://weld.cdi-spec.org/): alternative for Spring CDI/CD.
* Delta Spike (https://deltaspike.apache.org/): alternative for Spring Data.
* H2 database engine (https://www.h2database.com/html/main.html): embedded relational database

## Requirement and principles
- Keep it simple
- Without Spring

## Relationships
  APP <--> Controller <--> Service <--> Repository <--> Database

## Structure
Simple structure containing **controller, dto, entity, exception, repository, service** packages.
### Packages
####controller
Contains the controller classes: TransferController (main controller), CorsFilter (enable Cors), UtilController (util endpoints).
####dto
Contains the dto TransferDto.
####entity
Simple API uses only the Account entity, representing the accounts (id and balance).
####exception
Many exceptions classes used by application.
####Repository
Contains the AccountRepository responsible to store and retrieve accounts information.
#### Service
Contains the TransferService (communicate the Controller and Repository) and UtilService (util methods).

## Endpoints:
***POST: /api/transfer***
Input (*JSON*):
*{
"senderAccountId": ``senderAccountId``,
"receiverAccountId": ``receiverAccountId``,
"amount": ``amount``
}*

Output:

|  HttpCode | Description  | Reason  |
| ------------ | ------------ | ------------ |
| 200  | The request has succeeded.  |  Transfer OK |
|  415 |  Unsupported Media Type | Content-type different from JSON  |
|  500 |  Internal Server Error | Unexpected error  |
|  422 |  Unprocessable Entity | Account not found in Database OR amount negative value OR Note enough balance OR invalid receiver, sender ID   |
|  400 |  Bad Request | Invalid amount value (not numeric) in JSON request OR request body is empty|

### Example:
http://localhost:8080/api/transfer

Valid input example:
```json
{
	"senderAccountId": 1,
	"receiverAccountId": 2,
	"amount": 10.00
}
```

***GET: /api/private/ping***
Util endpoint to verify if the APP is running.
### Example:
http://localhost:8080/api/private/ping

***GET: /api/private/list***
Util endpoint to list the Accounts of Database.
### Example:
http://localhost:8080/api/private/list

***GET: /api/private/persist***
Util endpoint to create 5 accounts with $500 balance.
### Example:
http://localhost:8080/api/private/persist

##Testing
  The project uses a persistent embedded H2 running locally and a in-memory embedded H2 running in test mode (multiple *persistence.xml*).
  If you want to create data to test executing the JAR, you can use the **/api/private/persist** endpoint and verify the data using the **/api/private/list** endpoint.

    
To generate JAR:

	mvn clean package

To run tests:

    mvn test

To run simple JAR:

```java
java -jar target/simpleRestTransferAPI.jar
```