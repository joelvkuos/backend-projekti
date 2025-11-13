# 📇 Directory - Contact Management Application

A modern web-based contact management system built with Spring Boot, featuring user authentication, role-based access control, and a responsive Bootstrap UI.

## 🚀 Features

### Core Functionality
- **Contact Management (CRUD)**: Create, read, update, and delete contacts
- **User Authentication**: Secure login and registration system
- **Role-Based Access**: USER and ADMIN roles with different permissions
- **Data Isolation**: Each user sees only their own contacts
- **Admin Panel**: User management with statistics and delete capabilities
- **REST API**: RESTful endpoints with HTTP Basic authentication

### Technical Features
- Spring Boot 3.5.7 with Java 17
- Spring Security with BCrypt password encryption
- PostgreSQL database with JPA/Hibernate
- Thymeleaf templating engine
- Bootstrap 5 responsive UI
- Jakarta Bean Validation
- Mobile-friendly design

## 📋 Requirements

- Java 17 or higher
- PostgreSQL 14+
- Maven 3.6+


## 🎯 Usage

### Web Interface
1. **Login**: Navigate to `/login`
2. **Register**: Create new account at `/register`
3. **Contacts**: View and manage contacts at `/directory`
4. **Admin Panel**: Access user management at `/admin` (ADMIN role only)


**Authentication**: HTTP Basic Auth with username/password

## 🏗️ Project Structure

```
directory/
├── src/
│   ├── main/
│   │   ├── java/backendprojekti/directory/
│   │   │   ├── DirectoryApplication.java
│   │   │   ├── DataLoader.java
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── ContactController.java
│   │   │   │   ├── ContactRestController.java
│   │   │   │   ├── LoginController.java
│   │   │   │   └── RegisterController.java
│   │   │   ├── model/
│   │   │   │   ├── AppUser.java
│   │   │   │   ├── AppUserRepository.java
│   │   │   │   ├── Contact.java
│   │   │   │   └── ContactRepository.java
│   │   │   └── service/
│   │   │       └── CustomUserDetailsService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/css/
│   │       │   └── bootstrap.min.css
│   │       └── templates/
│   │           ├── login.html
│   │           ├── register.html
│   │           ├── directory.html
│   │           ├── addcontact.html
│   │           ├── editcontact.html
│   │           └── admin.html
│   └── test/
└── pom.xml
```

## 🔒 Security

- **Authentication**: Spring Security with form-based login
- **Authorization**: Role-based access control (@PreAuthorize)
- **Password Encryption**: BCrypt algorithm
- **CSRF Protection**: Enabled for web forms (disabled for API endpoints)
- **Data Isolation**: Users can only access their own contacts
- **Session Management**: Spring Security session handling

## 🎨 UI Components

- **Bootstrap 5**: Modern, responsive design
- **Mobile-Friendly**: Viewport meta tags and responsive tables
- **Card Layouts**: Clean login and registration forms
- **Form Validation**: Client-side and server-side validation
- **Hover Effects**: Interactive table rows and buttons

## 🚀 Deployment

### Rahti (OpenShift)
The application is deployed on Rahti at:
```
https://backend-projekti-directory-1.2.rahtiapp.fi/
```

Rahti automatically injects PostgreSQL connection details via environment variables.

## 📄 License

This project is part of a backend programming course assignment.

## 👨‍💻 Author

Joel Kuosmanen - [GitHub](https://github.com/joelvkuos)
