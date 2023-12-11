# Auth-token-SpringBoot

## About the project 
### Description 
Auth-token-SpringBoot is a secure authentication API developed using the Spring Boot framework. 
This project provides robust and flexible user authentication capabilities, allowing users to securely login and access protected resources. 
Utilizing industry-standard practices, such as JSON Web Tokens (JWT) for token-based authentication, and Bcrypt for password hashing, Auth-token-SpringBoot ensures a high level of security. 
The project is designed with simplicity and extensibility in mind, making it an ideal solution for integrating authentication into various Spring Boot applications.

### Key features
The project pretend to have the following key features:
- Token-based authentication using JWT for enhanced security. 
- Bcrypt password hashing for secure storage. 
- Integration with Spring Security for comprehensive user authentication and authorization. 
- Easy-to-use API endpoints for user login and token generation. 

### Dependencies used

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate Validator](https://hibernate.org/validator/). To validate not null and not empty fields in the body request.
- [Lombok](https://projectlombok.org/). For data objects.
- [H2](https://www.h2database.com/html/main.html). Embedded database for use the application. In real projects **use an external database**.
- [JJWT](https://github.com/jwtk/jjwt). JSON Web Token

### Information

 - The expiration time is set in 24 hours. Can be modified in JwtService.java in line 38:
    ```
    .setExpiration(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
    ```
 - The signature algorithm is HS256.
 - FrameOptionsConfig disable to can use h2-console properly.
#### Endpoints
The project has three endpoints:
1. **POST /v1/login**
    
    Given a body request with email and password, if the user exists and the 
    password is correct, return a token.
    
    Example:
    ```json
   {
    "email": "test@test.com",
    "password": "test"
    }
    ```    
2. **POST /v1/register**

   Given a body request with at least a new email and a password,
   then the application create a new user and return a token.
    ```json
   {
    "email": "test@test.com",
    "password": "test",
    "name": "Test"
    }
    ```    
3. **GET /v1/user**

   Return information about the user logged. 
   The authorization is with Bearer Token. Must set this authorization with a valid token from login or register.

## Quickstart

Download and compile the project (mvn clean compile) and run the Main file.

### Prerequisites

Before you begin, ensure you have met the following requirements:

- [Java Development Kit (JDK) 17](https://adoptium.net/temurin/releases/?version=17): Ensure that you have Java 17 installed on your machine.

- [Apache Maven](https://maven.apache.org/download.cgi): Maven is used for project management and build. Make sure you have Maven installed to build and manage dependencies.

The secret key variable in *application.yml* file must be updated with a 256bit key. In this project the key is an environment variable.

## Useful information

Generate a 256-bit WEP Keys: https://seanwasere.com/generate-random-hex/

The H2 datasource can be updated for other database (recommended). It is important in case of change
the database, update the *application.yml* with the new drivers and configuration properties.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.