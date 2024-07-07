# Spring Blog

Spring Blog is a RESTful web service that allows users to sign up, create posts, and interact with other users' posts. It is built using Spring Boot, Spring Data JPA, Spring Security, Spring Test, MySQL, Swagger and Docker.

### Table of Contents

- [Features](#features)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [License](#license)
- [Acknowledgements](#acknowledgements)

## Features

- Sign up / Sign in / Sign out
- Create, read, update, and delete posts
- Like and comment on posts
- View all posts, posts by a specific user, and posts liked by a specific user
- View all comments or likes on a post
- View all users or a specific user

## Usage

### Prerequisites

- Docker and Docker Compose
- Java 17 or later
- Maven

### Running the application

1. Clone the repository
2. Run the command `docker-compose up` to start the PostgreSQL database
3. Run the application using the command `mvn spring-boot:run` or start the application in your IDE

### API Documentation

The API documentation is available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

### Testing

Run the command `mvn test` to run the tests.

## Endpoints

### Authentication

- `POST /api/auth/signup` - Sign up
- `POST /api/auth/signin` - Sign in
- `POST /api/auth/signout` - Sign out

### Users

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get a user by ID
- `GET /api/users/{id}/posts` - Get all posts by a user
- `GET /api/users/{id}/likes` - Get all posts liked by a user
- `GET /api/users/{id}/comments` - Get all comments by a user

### Posts

- `GET /api/posts` - Get all posts
- `POST /api/posts` - Create a post
- `GET /api/posts/{id}` - Get a post by ID
- `PUT /api/posts/{id}` - Update a post by ID
- `DELETE /api/posts/{id}` - Delete a post by ID

### Likes

- `POST /api/posts/{postId}/likes` - Like a post
- `DELETE /api/posts/{postId}/likes` - Unlike a post
- `GET /api/posts/{postId}/likes` - Get all likes on a post

### Comments

- `POST /api/posts/{postId}/comments` - Comment on a post
- `GET /api/posts/{postId}/comments` - Get all comments on a post
- `GET /api/comments/{id}` - Get a comment by ID
- `PUT /api/comments/{id}` - Update a comment by ID
- `DELETE /api/comments/{id}` - Delete a comment by ID

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Test](https://spring.io/guides/gs/testing-web/)
- [PostgreSQL](https://www.postgresql.org/)
- [Docker](https://www.docker.com/)
- [Swagger](https://swagger.io/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
