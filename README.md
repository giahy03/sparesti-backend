# Sparesti: Backend

Sparesti is a full-stack application developed as part of an assessment in the IDATT2106 Software Engineering course
at the Norwegian University of Science and Technology (NTNU). The application is developed with Spring Boot and Vue.js.

## Team
- Olav Sie Rotvær
- Gia Hy Nguyen
- Melissa Visnjic
- Henrik Tefre
- Hanne-Sofie Søreide
- Jeffrey Yaw Annor Tabiri
- Ramtin Forouzandehjoo Samavat
- Tobias Skipevåg Oftedal

## Table of Contents
- [Overview](#overview)
- [Wiki](#wiki)
- [Features](#features)
- [Installation Manual](#installation-manual)
- [Test](#test)
- [Acknowledgements](#acknowledgements)

## Overview
Sparesti is a full-stack web application designed to provide a secure
and efficient platform for users to manage their savings. It includes
a range of features that support secure authentication, goal tracking,
and personalized challenges to encourage users to save money.

The project utilizes the following technologies:
- Frontend: Vue.js with Node.js.
- Backend: Spring Boot V3 with Java 21 and Maven.
- Database: MySQL V8 for runtime and H2 for tests.

## Wiki
The wiki contains system documents which describe the various aspects of the software system. The documents capture the
details of the system's design, implementation, operation and maintenance, serving as a reference for developers.
- [System Architecture Program]()
- [Class Diagram]()
- [Sequence diagram for getting quiz]()
- [Sequence diagram for login user]()

## Features
- **Secure Authentication**: Ensures that users can securely log in, register, and manage their accounts. Passwords are encrypted and stored safely to protect user data.
- **Goals And Challenges**: Enables users to set personal financial goals, invite friends or family for collaboration, and design personalized challenges to achieve these goals effectively.
- **Progress Tracking**: Utilizes interactive game elements for real-time visualization of progress, and rewards users with badges for meeting financial milestones.
- **Financial Management**: Includes functionality for uploading PDF bank statements for automated budget creation and transaction management.
- **Enhanced Financial Knowledge**: Budgets are generated using SSB statistical data and personal income, displaying expected versus actual spending values to help users compare and adjust their financial plans. Additionally, users can access a dedicated page with news related to finance.
- **Accessibility Support**: Complies with WCAG and Firefox accessibility standards to ensure usability for all users.

## Installation Manual

### Prerequisites
- JDK 21.
- Maven installed on device.
- A maven management tool.
- Docker.

### Set up and run the backend.

1. Begin by cloning the project into your device. In your terminal, enter the following command:
   ```
   git clone https://github.com/RamtinS/quiz-app-backend.git
   ```

2. Navigate to the project directory by typing the following command in the terminal:
   ```
   cd quiz-app-backend
   ```

3. Navigate to the docker file by running this command in the terminal:
   ```
   cd docker
   ```

4. Run the database required for the backend to function by starting the docker compose file in the terminal with this command.
   ```sh
   docker-compose up
   ```

5. Find a new terminal and enter the following command to finally start the backend.
   ```sh
   /mvnw spring-boot:run
   ```

## Test
To run the tests in the project, you would use the command
   ```sh
   mvn test
   ```

### Test users and data
#### Admin:
- Email: admin@example.com
- Password: password

Test data, such as bank statements, can be located within the resource folder.


## Acknowledgements
Special thanks to the subject teachers and the product owners for creating this assignment and providing us with the 
opportunity to develop this project.
