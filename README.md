# User Management System

## Description
This project is a RESTful API for managing users and their associated addresses. It provides endpoints for CRUD (Create, Read, Update, Delete) operations on user data, as well as updating user addresses.

## Setup Instructions
1. Clone the repository from [GitHub Link].
2. Ensure you have Java and Spring Boot installed on your machine.
3. Import the project into your preferred IDE.
4. Configure the project with your database settings in `application.properties`.
5. Build and run the application.

## Endpoints
1. **GET /users**
   - Retrieves all users.

2. **GET /users/{id}**
   - Retrieves a single user by ID.

3. **POST /users**
   - Creates a new user.
   - Requires a JSON object representing the user in the request body.

4. **PUT /users/{id}**
   - Updates an existing user by ID.
   - Requires a JSON object representing the updated user in the request body.

5. **DELETE /users/{id}**
   - Deletes a user by ID.

6. **GET /users/dates**
   - Retrieves users based on birthdate range.
   - Requires a JSON object representing the date range in the request body.

7. **PATCH /users/{userId}/address**
   - Updates the address of a user.
   - Requires a JSON object representing the address in the request body.

## Error Handling
- The application handles validation errors and missing entities with appropriate error messages and HTTP status codes.
