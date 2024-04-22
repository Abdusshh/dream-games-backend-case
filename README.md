# Dream Games Backend Case Study

## Overview
This project is a backend service for a game platform where users can create and join teams, as well as update their progress levels and coin balances. It is built with Spring Boot and utilizes JPA for ORM, and MySQL as the persistence layer.

## Architecture
The backend is structured around the MVC (Model-View-Controller) pattern, with the addition of repositories and services for business logic abstraction and data management:

- **Models**: Define the entity schemas for the database.
- **Repositories**: Interface with the database, abstracting CRUD operations away from the business logic.
- **Services**: Contain business logic, calling on repositories to persist data.
- **Controllers**: Handle incoming HTTP requests, invoking services to perform business operations based on the request.

## Implementation Details

### User Management
- Users can be created with a default level of 1 and a coin balance of 5000. The `UserService` handles the creation and updating of user entities.

### Team Management
- Teams can be created or joined by users, with each operation deducting 1000 coins from the user's balance. This ensures that users have a stake in the teams they participate in.
- The system prevents users from joining a team if they do not have sufficient coins or if the team is at full capacity. It also ensures that a user cannot join the same team more than once.
- Synchronization is used in methods handling team joining and leaving to manage concurrency effectively, ensuring data consistency and integrity.

### Transaction Management
- The service methods related to team creation, joining, and leaving are annotated with `@Transactional` to ensure that all database operations within a single method are completed successfully before committing the transaction. This is crucial for maintaining data accuracy and integrity, especially in the case of exceptions.

### Error Handling
- Custom exceptions and error messages guide the user interactions, providing clear feedback for operations like attempting to join a team without enough coins or trying to leave a team they are not a part of.

## Design Choices
- **RESTful API Design**: The API is designed to be RESTful, providing intuitive endpoints for managing users and teams.
- **Use of `@Transactional`**: Ensures that operations related to financial transactions and team membership changes are atomic and consistent.
- **Error Handling and Validation**: Robust error handling and input validation provide security and ensure the database integrity.

## Running the Application
To run the application, you will need:
- JDK 11 or higher
- Maven for dependency management and running the project
- MySQL database running locally or on a server

To start the application:
1. Update the `application.properties` file with your MySQL user and password.
2. Run the command `mvn spring-boot:run` from the root directory of the project.