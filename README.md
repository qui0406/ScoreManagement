# 📚 ScoreManagement

A **Student Score Management System** with features for administration, score entry, PDF report export, CSV data import, email notifications, and real-time communication.  
The project consists of **Backend** (Spring MVC + Thymeleaf) and **Frontend** (ReactJS) as separate applications, communicating via REST APIs.


---

## 🚀 Key Features

### Backend
- **Authentication & Authorization**: Spring Security + JWT
- **Score Management**: Add, edit, delete, and search student scores
- **Report Export**: Generate PDF reports for each student/class
- **CSV Data Import**: Read CSV files to import scores
- **Email Notifications**: Using **SendGrid**
- **Image Storage**: Integrated **Cloudinary**
- **Caching**: Redis for faster data access
- **Realtime Communication**: Send/receive score updates via Socket.IO
- **Server-side Rendering**: Thymeleaf templates for admin interface

### Frontend
- **Score Management UI**: Input and view scores
- **Student Lookup**
- **Report Export** directly from browser
- **User Authentication** (Login/Register)


## 🛠 Technologies

### Backend
- **Java 21**
- **Spring MVC 6**
- **Spring Security** + **JWT**
- **Hibernate**
- **Thymeleaf**
- **MySQL**
- **Redis**
- **Docker**
- **SendGrid API**
- **Apache POI / OpenCSV**
- **Cloudinary API**
- **Socket.IO**
- **iText PDF**

### Frontend
- **ReactJS**
- **Axios** (API requests)


## ⚙️ Installation & Run

### Requirements
- **Java 21**
- **Node.js >= 18**
- **Docker & Docker Compose**
- **MySQL** (if not running via Docker)
- **Redis** (if not running via Docker)

### 1. Clone the repository

git clone https://github.com/qui0406/ScoreManagement.git

cd ScoreManagement

## Docker setup
  
docker-compose up -d


## Backend setup

./mvnw clean install

./mvnw spring-boot:run

## Front-end setup

cd ScoreManagementApp

npm install

npm start
