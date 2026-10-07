# Student Quiz Management System

A modern desktop-based quiz application built with **Java, JavaFX, JDBC, and MySQL**.  
The application allows students to register, attend topic-based quizzes, track their performance, view quiz history, and manage their profiles.

---

## ✨ Features

- 🔐 Student Registration & Login
- 📊 Interactive Dashboard
- 🧠 Topic-based Quizzes
- ☕ Java
- 🧩 OOP
- 🔗 JDBC
- 🗄️ SQL
- 🌍 General Knowledge
- 🎬 Movies
- 📝 Multiple-choice Questions
- 📈 Automatic Score & Percentage Calculation
- 🏆 Best Score Tracking
- 🔥 Quiz Streak Tracking
- 📚 Quiz History
- 👤 Student Profile Management
- ✏️ Update Profile
- 🗑️ Delete Account
- 🚪 Logout
- ↩️ Page-to-page Navigation
- 🎨 Modern JavaFX UI
- 🖱️ Scrollable Content

---

## 🛠️ Tech Stack

| Technology | Usage |
|---|---|
| Java | Application Development |
| JavaFX | Graphical User Interface |
| JDBC | Database Connectivity |
| MySQL | Database |
| CSS | UI Styling |
| Eclipse | Development Environment |

---

## 🏗️ Project Architecture

```text
Student Quiz Management System
│
├── Presentation Layer
│   └── JavaFX UI
│
├── Application Layer
│   ├── Login
│   ├── Registration
│   ├── Dashboard
│   ├── Topic Selection
│   ├── Quiz
│   ├── Result
│   ├── Results History
│   └── Profile
│
├── Database Layer
│   └── JDBC
│
└── Database
    └── MySQL
📂 Project Structure
student_quiz/
│
├── src/
│   └── student_quiz/
│       ├── Main.java
│       ├── DBConnection.java
│       ├── Login.java
│       ├── Register.java
│       ├── Dashboard.java
│       ├── TopicSelection.java
│       ├── Quiz.java
│       ├── Result.java
│       ├── ResultsHistory.java
│       ├── Profile.java
│       ├── module-info.java
│       └── style.css
│
└── README.md
🔄 Application Flow
                 ┌──────────────┐
                 │ Registration │
                 └──────┬───────┘
                        │
                        ▼
                 ┌──────────────┐
                 │    Login     │
                 └──────┬───────┘
                        │
                        ▼
                 ┌──────────────┐
                 │  Dashboard   │
                 └──────┬───────┘
                        │
             ┌──────────┼──────────┐
             ▼          ▼          ▼
        Take Quiz    My Results   Profile
             │
             ▼
      ┌───────────────┐
      │ Topic Selection│
      └───────┬───────┘
              │
              ▼
        ┌───────────┐
        │   Quiz    │
        └─────┬─────┘
              │
              ▼
        ┌───────────┐
        │  Result   │
        └───────────┘
🎯 Quiz Topics

The application currently provides six quiz categories:

Topic	Description
☕ Java	Core Java concepts
🧩 OOP	Object-Oriented Programming
🔗 JDBC	Java Database Connectivity
🗄️ SQL	SQL and database concepts
🌍 GK	General Knowledge
🎬 Movies	Movies and entertainment

Each topic contains multiple-choice questions stored in the MySQL database.

🗄️ Database Design

Database name:

quizmaster
Tables
students

Stores student account and performance information.

id
name
email
username
password
streak
best_score
quizzes_attempted
last_quiz_date
created_at
questions

Stores quiz questions and answers.

id
topic
question
option_a
option_b
option_c
option_d
correct_answer
quiz_results

Stores completed quiz results.

id
student_id
topic
score
total_questions
percentage
quiz_date
Database Relationship
students
   │
   │ 1
   │
   │
   │ N
   ▼
quiz_results

A student can have multiple quiz results.

⚙️ Requirements

Before running the project, install:

Java JDK
JavaFX SDK
MySQL Server
MySQL Connector/J
Eclipse IDE
🚀 Getting Started
1. Clone the Repository
git clone https://github.com/your-username/student-quiz-management-system.git
2. Open in Eclipse

Import the project into Eclipse and configure the JavaFX libraries.

3. Create the Database

Open MySQL and run:

CREATE DATABASE quizmaster;

Then:

USE quizmaster;

Create the required tables and insert the quiz questions.

4. Configure Database Connection

Open:

DBConnection.java

Update your MySQL credentials:

private static final String USER = "root";
private static final String PASSWORD = "your_password";

The database URL is:

jdbc:mysql://localhost:3306/quizmaster
5. Configure Dependencies

Add:

JavaFX libraries
MySQL Connector/J

to the Eclipse project.

6. Run the Application

Run:

Main.java
🖥️ Application Screens
Login

Students can securely access the application using their username and password.

Registration

New students can create an account.

Dashboard

Displays:

Current Streak
Best Score
Quizzes Taken
Quiz navigation
Profile navigation
Results navigation
Topic Selection

Students can choose their preferred quiz topic.

Quiz

Students answer multiple-choice questions and track their progress.

Result

Displays the final score and percentage after submitting the quiz.

Results History

Displays previous quiz attempts and performance statistics.

Profile

Allows students to update their account information or delete their account.

📊 Quiz Result Query

To view all quiz results:

SELECT * FROM quiz_results;

To view results with student information:

SELECT
    s.name,
    s.username,
    r.topic,
    r.score,
    r.total_questions,
    r.percentage,
    r.quiz_date
FROM students s
JOIN quiz_results r
ON s.id = r.student_id
ORDER BY r.quiz_date DESC;
🔍 Check Question Count

To verify the number of questions available for each topic:

SELECT
    topic,
    COUNT(*) AS question_count
FROM questions
GROUP BY topic;
🔒 Data Management

The application uses JDBC to communicate with MySQL.

User information, quiz questions, and quiz results are stored persistently in the database.

When a student account is deleted, related quiz results can also be removed through the database relationship using cascading deletion.

🎨 UI Design

The application uses a modern JavaFX-based interface rather than the default JavaFX controls.

The UI includes:

Card-based layouts
Rounded buttons
Sidebar navigation
Clean typography
Light backgrounds
Hover effects
Responsive scrolling
Consistent navigation
JavaFX CSS styling
