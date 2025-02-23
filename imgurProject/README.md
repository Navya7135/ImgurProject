# README - Imgur Project

## Overview

The Imgur Project is a Spring Boot-based web application that allows users to upload, view, and manage images while ensuring secure authentication and user management. It consists of two main controllers:

- `MemberController`: Manages user authentication, account creation, and login functionalities.
- `ImageController`: Handles image-related operations such as uploading, deleting, and viewing user images.

---

## Controllers

### 1. MemberController

#### Overview

Handles user account creation, authentication, and login.

#### Endpoints

- **Create User**

  - **Endpoint:** `POST /user/createUser`
  - **Request Body:** JSON with user details
  - **Response:** User details on success

- **User Login**

  - **Endpoint:** `POST /user/login`
  - **Request Body:** JSON with `userName` and `password`
  - **Response:** JWT token on success

---

### 2. ImageController

#### Overview

Handles all image-related operations including uploading, viewing, and deleting images.

#### Required Header for Image Endpoints

Include the following header in requests for saving, getting, or deleting images:

```
Key: Authorization
Value: Bearer {{accessToken}}
```

Replace `{{accessToken}}` with the token received from the login response.

#### Endpoints

- **Save an Image**

  - **Endpoint:** `POST /image/uploadImage`
  - **Headers:**
    - Authorization: Bearer `{{accessToken}}`
  - **Request Parameters:**
    - `file` (MultipartFile) - Image to be uploaded
  - **Response:** `200 OK` on success

- **Delete an Image**

  - **Endpoint:** `DELETE /image/deleteImage/{imageId}`
  - **Headers:**
    - Authorization: Bearer `{{accessToken}}`
  - **Response:** `200 OK` on success

- **View All Images**

  - **Endpoint:** `GET /image/viewAllImages`
  - **Headers:**
    - Authorization: Bearer `{{accessToken}}`
  - **Response:** List of uploaded images

- **View User Profile with Images**

  - **Endpoint:** `GET /image/viewUserProfile`
  - **Headers:**
    - Authorization: Bearer `{{accessToken}}`
  - **Response:** User profile details with images

---

## Security

- Implements JWT authentication to secure endpoints.
- Token validation is handled in `JwtUtil.java`.

---

## Logging

- SLF4J is used for logging requests and errors.
- Logs significant events like authentication success/failure and image upload actions.

---

## Dependencies

- Spring Boot
- Spring Security (JWT)
- Lombok
- Multipart File Handling

---

## Setup & Run Instructions

1. Clone the repository.
2. Configure `application.properties` with necessary database and JWT settings.
3. Build and run the project using:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
4. The application runs on `http://localhost:2020/` by default.

