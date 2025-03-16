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
- **Spring Boot v3.3.0**
- **Spring Mail**
- **Log4j2**
- **Lombok**
- **GreenMail v2.0.1** (for testing)
- **Maven** (for dependency management and build)



### Prerequisites

- Java 21 or later
- Maven 3.6.0 or later

### Usage

This project serves as a proof of concept and does not have direct usage. Instead, it is designed to showcase and test the mail-sending functionality of the Spring framework. You can explore the code by building the project and executing the unit tests.

* The intention is to package this code as a JAR, which can then be used as a dependency by web services, APIs, and similar applications.
* Alternatively, the classes can be integrated directly into these services/APIs and packaged together.

The approach depends on your perspective, whether you prefer a "microservices" architecture or a "monolithic" structure.

<!-- TODO future section - but I dont remember what I was going to add here -->
<!-- ## Future -->
<!-- This project can be modified to be a service -->

## Contact
For any questions or feedback, please contact Pritam Hooda (me). I can be reached at pritamhooda@outlook.com

