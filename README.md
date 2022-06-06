# Hexalab API

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3.8.5](https://maven.apache.org/download.cgi)

You will need an empty [PostgreSQL](https://www.postgresql.org/download/) database and you will also need to configure an `application-local.yml` to run locally.

You will need to use these settings in the `application-local.yml`:

    spring:
      config:
        activate:
          on-profile: local

      datasource:
        url: jdbc:postgresql://Database URL
        username: Database Username
        password: Database Password

      jpa:
        hibernate:
          ddl-auto: update
    app:
      jwt:
        secret: Secret JWT
    
    services:
      user-authentication-service:
        url: User Authentication API URL
      transaction-authorization-service:
        url: Transaction Authorizaation API URL

## Running the application locally

To run the application locally you can use the standard boot command:

```shell
mvn spring-boot:run
```

Or you can use Boot Dashboard from Eclipse IDE or Spring Tool Suite.

## About documentation

The application does not have written documentation, but it has a Postman collection import file that has all the application's endpoints and has a folder with a standard endpoint flow to test the application initially without too many problems.

The import file is at the root of the project and is called `Hexalab API.postman_collection` and can be imported in [Postman v9.20.3](https://www.postman.com/downloads/).

The standard flow uses collection variables to work without the need to change a lot of data such as the access token and some identifier codes, so you can just run requests through the interface of the standard flow folder.

The other folders only separate requests into groups.

## The basics to use the application

To start the application it is worth mentioning the use of the Spring Security dependency to control most of the access to the endpoints with an access token, to start using the application we can use the "Save User" endpoint that allows the registration of a user without using a access token, and to have access to the token we can use the "Do Login" endpoint, from there it is necessary to add the token to the request header or use the Postman Authorization tab and choose the Bearer Token option in the Type field and insert it there.
