# P04 Access Control System

## Overview

This project involves developing a simple access control program, similar to those used to manage access to shared computer resources. The focus is on maintaining a record of valid users, where access is granted based on correct user credentials.

### Learning Objectives

- Develop proficiency in creating instantiable classes and using objects in Java.
- Gain experience with the `java.util.ArrayList` class for managing collections of user objects.
- Enhance understanding of exception handling and the development of robust tester methods.

### Key Features

- **User Management**: The system maintains a list of users, with attributes such as username, password, and administrative privileges.
- **Login Validation**: Users must provide a valid username and password combination to access the system.
- **Administrative Functions**: Administrators can add or remove users, reset passwords, and modify administrative status.
- **Session Control**: Users can change their passwords and log out. The system also supports session control for logged-in users.

### Technical Implementation

- **User Class**: Represents individual users with properties like username, password, and admin status. It includes methods for login validation and user property management.
- **Access Control Class**: Manages a collection of users, handling login processes and administrative tasks such as user creation, password management, and user removal.
- **Tester Methods**: Comprehensive testing methods in `AccessControlTester` class verify the functionality of the access control system, including user management and session control operations.

### Project Requirements

- The assignment must be completed individually, without pair programming.
- Allowed imports are limited to `java.util.ArrayList` and necessary exceptions.
- The system consists of three classes: `User`, `AccessControl`, and `AccessControlTester`, with specific methods and constructors as outlined in the assignment.
- Public methods must match specified signatures, and additional private helper methods can be implemented for internal class functionality.
- Testing methods must be implemented in the `AccessControlTester` class to verify the system's correctness under various scenarios.
