<h2 align="center">Spring REST API Security</h2>

### Description 
Web Service for personnel management system.
The system exposes REST APIs to perform the following functions:
 - CRUD operations for Employee. New employees will be created if they are passed during creation.
   For the update operation, all passed attributes are updated. The search function by first or last name is also available.
 - Registration and Authentication

The functions are available to the user depending on the *role*.
*An unauthenticated user* has to create a new account and log in into it. A user with a *basic* role can retrieve all entities, an entity by id, by first or last name.
The *administrator* has access to adding new employees to the database, changing and deleting information about them.


### Used Technologies
* JDK version: 17
* Spring Web MVC (REST)
* Spring Security
* Java JWT: JSON Web Token for Java and Android
* Java Persistence API (Hibernate)
* Spring HATEOAS
* Hibernate Validator
* JUnit 5 and Mockito
* Logback logger
* Build tool: Maven
* Database: MySQL 8.0.32

#### Additional information
   * The application is organised into the three-tier architecture. Each layer has its own logic.
   * JSON is used as a format of client-server communication messages.
   * The error/exception handling mechanism has been implemented.
   * The application supports stateless user authentication and JWT token for authorization.
   * The custom password validation rules have been implemented.
   * DTO pattern is used for carrying data between the layers.
   * The hypermedia-based structure is used for simplifying access to the application for clients.
   * Test

### APIs Endpoints

      Registration - POST http://localhost:8080/user/register

      Login - POST http://localhost:8080/user/authenticate

      Retrieve all entities - GET http://localhost:8080/api/employees

      Retrieve by id - GET http://localhost:8080/api/employees/{id}

      Retrieve by first or last name - GET http://localhost:8080/api/employees/{name}/

      Create - POST http://localhost:8080/api/employees

      Update - PUT http://localhost:8080/api/employees/{id}

      Delete - DELETE http://localhost:8080/api/employees/{id}

### Extra
<p float="left">
<img height="150" src="screenshots\already-exists.png" title="already-exists" width="200"/>
<img height="150" src="screenshots\authentication.png" title="authentication" width="200"/>
<img height="150" src="screenshots\by-id.png" title="by-id" width="200"/>
<img height="150" src="screenshots\does-not-exist.png" title="does not exist" width="200"/>
</p>

<p>
<img height="150" src="screenshots\not-authenticated.png" title="not-authenticated" width="200"/>
<img height="150" src="screenshots\not-authorized.png" title="not-authorized" width="200"/>
<img height="150" src="screenshots\registration-errors.png" title="registration-errors" width="200"/>
<img height="150" src="screenshots\registration.png" title="registration" width="200"/>
</p>










      
      
      

  
