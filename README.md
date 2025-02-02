# EmailSender

EmailSender is a Spring Boot application designed for sending emails using Spring Mail. This application supports sending both simple text messages and MIME messages with attachments and embedded images.
The purpose of this project was to explore the functionality of SpringMail, how to use it in your SpringBoot application and how to write unit tests for it. As such, this application is designed as spring library that you integrate into your spring-boot app.
In real world application, it be likely better to have the email functionality as part of the spring boot app rather than an external jar. This is simply a POC.   

## Features

- **Simple Text Emails:** Send plain text emails with optional CC and BCC.
- **MIME Emails:** Send rich text emails with attachments and embedded images.
- **Logging:** Integrated logging using Log4j2 for detailed email sending logs.
- **Testing:** Includes unit tests using GreenMail for in-memory email server testing.

## Technologies Used

- **Java 21**
- **Spring Boot 3.3.0**
- **Spring Mail**
- **Log4j2**
- **Lombok**
- **GreenMail** (for testing)
- **Maven** (for dependency management and build)


### Prerequisites

- Java 21 or later
- Maven 3.6.0 or later

### Usage

Since this project is just a POC there is no direct usage, its ment to showcase and test the mail sending funcion of the spring framework. you can see the code function by building the project and executing the unit tests.
* The idea is that this code would be packaged as a JAR that is then used as a dependency by webservices, APIs, etc.
* Or the class could be part of these services/APIs; packaged together.
Depends on perspective, whether you are more of a "microservice guy" or a "monolith guy".

## Future
This project can be modified to be a service

## Contact
For any questions or feedback, please contact Pritam Hooda (me). I can be reached at pritamhooda@outlook.com

