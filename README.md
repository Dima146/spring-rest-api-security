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
   * The application supports stateless user authentication and JWT token for authorization. BASIC
   * The custom password validation rules have been implemented.
   * DTO pattern is used for carrying data between the layers.
   * The hypermedia-based structure is used for simplifying access to the application for clients.
   * All the layers of the application architecture are covered with tests.

### APIs Endpoints

      Registration - POST http://localhost:8080/user/registration

      Login - POST http://localhost:8080/user/authentication

      Retrieve all entities - GET http://localhost:8080/api/employees

      Retrieve by id - GET http://localhost:8080/api/employees/{id}

      Retrieve by first or last name - GET http://localhost:8080/api/employees/{name}/

      Create - POST http://localhost:8080/api/employees

      Update - PUT http://localhost:8080/api/employees/{id}

      Delete - DELETE http://localhost:8080/api/employees/{id}

### Extra
<p float="left">
<img alt="already-exists" height="150" src="https://github.com/Dima146/spring-rest-api-security/assets/87914550/62fc1fff-9ad6-49da-b14c-188172a48c89" title="already-exists" width="200"/>
<img alt="authentication" height="150" src="https://github.com/Dima146/spring-rest-api-security/assets/87914550/c83844cb-8734-4eb5-aadc-22e0cf47978e" title="authentication" width="200"/>
<img alt="by-id" height="150" src="https://github.com/Dima146/spring-rest-api-security/assets/87914550/2f4468b6-8765-4896-9b3d-ea2df0fa716d" title="by-id" width="200"/>
<img alt="created" height="150" src="https://github.com/Dima146/spring-rest-api-security/assets/87914550/c08b0ebb-508b-4242-a885-4e271e18fcdc" title="created" width="200"/>
</p>
<p>
<img alt="does-not-exist" height="150" src="https://github.com/Dima146/spring-rest-api-security/assets/87914550/9346d028-9d7b-4750-a8be-16cc4422d659" title="does-not-exist" width="200"/>
<img alt="not-authorized" height="150" src="https://github.com/Dima146/spring-rest-api-security/assets/87914550/716a50f6-7f5e-4f99-8f0a-de5a84976522" title="not-authorized" width="200"/>
<img alt="registration" height="150" src="https://github.com/Dima146/spring-rest-api-security/assets/87914550/40bdf83d-4495-4413-950e-8cd47f40f3dc" title="registration" width="200"/>
<img alt="registration-errors" height="150" src="https://github.com/Dima146/spring-rest-api-security/assets/87914550/4081ac23-1988-4076-8d79-0b7fe6a71e99" title="registration-errors" width="200"/>
</p>
