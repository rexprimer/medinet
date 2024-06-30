# mediNet: Spring Boot Project for Hospital and Patient Management

mediNet is a Spring Boot application designed for managing hospitals and patients efficiently.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Make sure you have the following installed:

- Java Development Kit (JDK) 17 or later [Download JDK](https://www.oracle.com/java/technologies/downloads/)
- Apache Maven [Download Maven](https://maven.apache.org/download.cgi)

### Building and Running

Follow these steps to build and run the application:
### Clone the repository:**

   git clone https://github.com/rexprimer/mediNet.git

### Features
* Add a new hospital
* Retrieve the hospital associated with a patient
* Get the number of patients associated with a hospital

### Dependencies
* Spring Boot Starter Data JPA
* Spring Boot Starter Web
* MySQL Connector (runtime)
* Lombok (optional)
* Spring Boot Starter Test
* Springdoc OpenApi (for API documentation)
* Spring Boot Starter Validation
* Configuration
* No specific environment variable configuration is required. The application uses an in-memory database by default for development purposes.

### Testing
Unit tests for the HospitalService and MediNetController classes are included in the src/test/java/com/mediNet/mediNet directory. You can run the tests using Maven:

bash
Copy code
mvn test
Deployment
For deployment to a production environment, you will need to configure a database connection and potentially adjust other configurations depending on your specific environment.

### License
This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

### Contributing
Contributions are welcome! Please see the CONTRIBUTING file for guidelines on how to contribute.

