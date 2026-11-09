# 🏋️ Fitness Online App

**English** | [Srpski](README.sr.md)

An online fitness platform where users can browse programs, keep an activity log and get exercise suggestions.

<!--
SCREENSHOTS (2-3): replace the file names with the real ones and remove the comment markers around this block.

![Program search and filters](Slike/NAZIV-SLIKE-1.png)
![Program details](Slike/NAZIV-SLIKE-2.png)
![Activity log](Slike/NAZIV-SLIKE-3.png)
-->

## ⭐ Key features

- Search and filter fitness programs by category, difficulty, price, duration and location
- Join programs with a choice of payment method; YouTube video for online programs
- Activity log with a progress chart and PDF download
- Messages to advisors and private messages between users
- Daily suggestion of 10 exercises from an external API, plus an RSS feed of fitness news
- Category subscription: one email a day with new programs in that category
- Separate JSP applications for administrators and advisors

---

## 🛠️ Tech stack

| Layer                | Technology                               |
|----------------------|------------------------------------------|
| Backend              | Java · Spring Boot · RESTful API         |
| Frontend             | Angular · TypeScript · HTML · CSS        |
| Database             | MySQL                                    |
| Administrator app    | Java · JSP MVC (Model 2)                 |
| Advisor app          | Java · JSP                               |

---

## 📁 Project structure

Folder names are in Serbian; the comments explain them.

```
fitness-online-app/
├── Administrator/            # Administrator application (JSP)
├── Baza podataka/            # Database SQL scripts
├── FitnessOnlineAppBackend/  # Backend (Java Spring Boot, RESTful API)
├── FitnessOnlineAppFrontend/ # Frontend (Angular, TypeScript)
├── Savjetnik/                # Advisor application (JSP)
└── Tekst projekta/           # Project documentation
```

---

## 👥 User roles

| Role              | Description                                                                              |
|-------------------|------------------------------------------------------------------------------------------|
| **Guest**         | Can search programs and view their details, but cannot join them                         |
| **User**          | Registered user with full functionality: joining programs, activity log, messages, etc.  |
| **Advisor**       | Reads and answers user messages through a separate JSP application                       |
| **Administrator** | Manages categories, users and statistics through a JSP application                       |

---

## ✨ Features

### Fitness programs
- Search and filter programs by category (cardio, strength, flexibility, HIIT), difficulty, price, duration and location (online, gym, park)
- Programs shown as cards (name, image, price) with pagination / virtual scroll
- Program detail page with all information, including the instructor and contact details
- Comments and questions on a program, visible to all users
- Every registered user can create, view and delete their own programs

### Registration and login
- Registration form: first name, last name, city, username, password, avatar (optional), email
- Account activation through a link sent by email
- Optional CAPTCHA service for bot protection
- Editing of personal data (except the username)

### Joining programs
- Choice of payment method: card, PayPal or in person at the location
- Online programs show a YouTube video; live programs are attended at the location
- Participation and purchase history

### Activity log
- Logging exercise type, duration, intensity and results
- Chart of progress over a chosen period (e.g. weight loss)
- Log can be downloaded as a PDF

### Communication
- Messages to advisors for help choosing a program
- Private messages between users

### Additional features
- RSS feed with the latest fitness news (`feeds.feedburner.com/AceFitFacts`)
- Daily suggestion of 10 exercises with instructions from an external API (`api-ninjas.com/api/exercises`)
- Category subscription: once a day the user receives an email with new programs in that category

### Administrator application (JSP)
- Management of fitness program categories (CRUD)
- Management of users (CRUD)
- Viewing the backend application's logs (statistics)

### Advisor application (JSP)
- Received messages, split into unread and read
- Replying to messages by email, with the option to attach a document or an image
- Searching messages by content

---

## 🚀 Running the project

### Prerequisites

- Java 17+
- Node.js 18+ and npm
- Angular CLI (`npm install -g @angular/cli`)
- MySQL

---

### 1. Database

Run the SQL scripts from the `Baza podataka/` folder:

```bash
mysql -u root -p < skripta.sql
```

---

### 2. Backend (Spring Boot)

```bash
cd FitnessOnlineAppBackend/fitnessOnline
./mvnw spring-boot:run
```

The API runs at `http://localhost:8080`.

> Set the database connection in `src/main/resources/application.properties`.

---

### 3. Frontend (Angular)

```bash
cd FitnessOnlineAppFrontend
npm install
ng serve
```

The app runs at `http://localhost:4200`.

---

### 4. Administrator and advisor applications

Open the `Administrator/` and `Savjetnik/` projects in a Java IDE (e.g. IntelliJ IDEA or Eclipse) and run them as web applications on a suitable server (e.g. Apache Tomcat).

---

## 📄 Documentation

The full project description, data model and application model are in the `Tekst projekta/` folder.
