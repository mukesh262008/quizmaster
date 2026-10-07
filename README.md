**QuizMaster – Student Quiz Management System**

A desktop quiz application built with Java, JavaFX, JDBC and MySQL. Students can register, log in, pick a topic, take a multiple-choice quiz, see their score instantly and review all their earlier attempts. The UI is styled with JavaFX CSS to look like a modern website (cards, rounded buttons, sidebar, clean spacing).


Features
Register / Login with input validation
Dashboard showing name, current streak, best score and quizzes taken
Topic selection – Java, OOP, JDBC, SQL, General Knowledge (GK), Movies (scrollable page)
Quiz – questions loaded from MySQL (up to 10 per topic), four options each, one question at a time with a progress bar
Result – score, percentage and a performance message; saved to the database
My Results – all previous attempts with total quizzes, best score and average percentage
Profile – update name, email and password, or delete the account
Back buttons on Register, Topic Selection, Quiz, Result, My Results and Profile
All queries use PreparedStatement
Screenshots

Add your screenshots to a screenshots/ folder and the links below will work.

Login	Dashboard
Show Image	Show Image
Topic Selection	Quiz
Show Image	Show Image
Result	My Results	Profile
Show Image	Show Image	Show Image
Tech Stack
Item	Version used in the project
Language	Java (JavaSE-25 in Eclipse)
UI	JavaFX 27 + JavaFX CSS
Database	MySQL (quizmaster, port 3306)
Driver	MySQL Connector/J 26.7.0
IDE	Eclipse
Project Structure
student_quiz/
└── src/
    ├── module-info.java
    └── student_quiz/
        ├── Main.java            # entry point, opens Login
        ├── DBConnection.java    # JDBC connection
        ├── Login.java
        ├── Register.java
        ├── Dashboard.java
        ├── TopicSelection.java
        ├── Quiz.java
        ├── Result.java
        ├── ResultsHistory.java
        ├── Profile.java
        └── style.css            # all UI styling

Each screen is a class with a show(...) method that builds the UI in code and sets a new scene on the same Stage (no FXML).

Database Setup

Create the database and the three tables. The script below is a suggested schema that matches the columns the code uses – adjust it if your own database differs.

sql
CREATE DATABASE quizmaster;
USE quizmaster;

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    streak INT NOT NULL DEFAULT 0,
    best_score INT NOT NULL DEFAULT 0,
    quizzes_attempted INT NOT NULL DEFAULT 0,
    last_quiz_date DATE NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    topic VARCHAR(30) NOT NULL,          -- Java, OOP, JDBC, SQL, GK, Movies
    question TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_answer CHAR(1) NOT NULL      -- 'A', 'B', 'C' or 'D'
);

CREATE TABLE quiz_results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    topic VARCHAR(30) NOT NULL,
    score INT NOT NULL,
    total_questions INT NOT NULL,
    percentage DOUBLE NOT NULL,
    quiz_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

Example question:

sql
INSERT INTO questions (topic, question, option_a, option_b, option_c, option_d, correct_answer)
VALUES ('Java', 'Which keyword is used to create a class in Java?', 'class', 'new', 'static', 'void', 'A');

The topic value must be exactly Java, OOP, JDBC, SQL, GK or Movies. Add 10 questions per topic.

How to Run
Install a JDK, JavaFX SDK, MySQL and MySQL Connector/J.
Create the database and tables (above) and insert some questions.
Open src/student_quiz/DBConnection.java and set your MySQL user name and password:
java
   private static final String URL = "jdbc:mysql://localhost:3306/quizmaster";
   private static final String USER = "root";
   private static final String PASSWORD = "your_password";
Import the project into Eclipse and add the JavaFX jars and the Connector/J jar to the build path.
Run Main.java. If JavaFX is not found, add these VM arguments in the run configuration (use your own SDK path):
   --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
Known Limitations
Passwords are stored as plain text (should be hashed, e.g. with BCrypt).
Database credentials are written in DBConnection.java (should be moved to a config file).
Questions are not shuffled and a quiz cannot go back to a previous question.
The streak increases by one for every quiz; it is not yet based on consecutive days.
Questions can only be added directly in the database.
Future Improvements

Password hashing, shuffled questions, per-quiz timer, answer review after submission, day-based streaks, an admin screen to manage questions, leaderboard and progress charts.

A desktop quiz application built with Java, JavaFX, JDBC and MySQL. Students can register, log in, pick a topic, take a multiple-choice quiz, see their score instantly and review all their earlier attempts. The UI is styled with JavaFX CSS to look like a modern website (cards, rounded buttons, sidebar, clean spacing).

College micro project – 23AD35C: OOPS with JAVA (CO10: desktop application with event handling).

Features
Register / Login with input validation
Dashboard showing name, current streak, best score and quizzes taken
Topic selection – Java, OOP, JDBC, SQL, General Knowledge (GK), Movies (scrollable page)
Quiz – questions loaded from MySQL (up to 10 per topic), four options each, one question at a time with a progress bar
Result – score, percentage and a performance message; saved to the database
My Results – all previous attempts with total quizzes, best score and average percentage
Profile – update name, email and password, or delete the account
Back buttons on Register, Topic Selection, Quiz, Result, My Results and Profile
All queries use PreparedStatement
Screenshots

Add your screenshots to a screenshots/ folder and the links below will work.

Login	Dashboard
Show Image	Show Image
Topic Selection	Quiz
Show Image	Show Image
Result	My Results	Profile
Show Image	Show Image	Show Image
Tech Stack
Item	Version used in the project
Language	Java (JavaSE-25 in Eclipse)
UI	JavaFX 27 + JavaFX CSS
Database	MySQL (quizmaster, port 3306)
Driver	MySQL Connector/J 26.7.0
IDE	Eclipse
Project Structure
student_quiz/
└── src/
    ├── module-info.java
    └── student_quiz/
        ├── Main.java            # entry point, opens Login
        ├── DBConnection.java    # JDBC connection
        ├── Login.java
        ├── Register.java
        ├── Dashboard.java
        ├── TopicSelection.java
        ├── Quiz.java
        ├── Result.java
        ├── ResultsHistory.java
        ├── Profile.java
        └── style.css            # all UI styling

Each screen is a class with a show(...) method that builds the UI in code and sets a new scene on the same Stage (no FXML).

Database Setup

Create the database and the three tables. The script below is a suggested schema that matches the columns the code uses – adjust it if your own database differs.

sql
CREATE DATABASE quizmaster;
USE quizmaster;

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    streak INT NOT NULL DEFAULT 0,
    best_score INT NOT NULL DEFAULT 0,
    quizzes_attempted INT NOT NULL DEFAULT 0,
    last_quiz_date DATE NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    topic VARCHAR(30) NOT NULL,          -- Java, OOP, JDBC, SQL, GK, Movies
    question TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_answer CHAR(1) NOT NULL      -- 'A', 'B', 'C' or 'D'
);

CREATE TABLE quiz_results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    topic VARCHAR(30) NOT NULL,
    score INT NOT NULL,
    total_questions INT NOT NULL,
    percentage DOUBLE NOT NULL,
    quiz_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

Example question:

sql
INSERT INTO questions (topic, question, option_a, option_b, option_c, option_d, correct_answer)
VALUES ('Java', 'Which keyword is used to create a class in Java?', 'class', 'new', 'static', 'void', 'A');

The topic value must be exactly Java, OOP, JDBC, SQL, GK or Movies. Add 10 questions per topic.

How to Run
Install a JDK, JavaFX SDK, MySQL and MySQL Connector/J.
Create the database and tables (above) and insert some questions.
Open src/student_quiz/DBConnection.java and set your MySQL user name and password:
java
   private static final String URL = "jdbc:mysql://localhost:3306/quizmaster";
   private static final String USER = "root";
   private static final String PASSWORD = "your_password";
Import the project into Eclipse and add the JavaFX jars and the Connector/J jar to the build path.
Run Main.java. If JavaFX is not found, add these VM arguments in the run configuration (use your own SDK path):
   --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
Known Limitations
Passwords are stored as plain text (should be hashed, e.g. with BCrypt).
Database credentials are written in DBConnection.java (should be moved to a config file).
Questions are not shuffled and a quiz cannot go back to a previous question.
The streak increases by one for every quiz; it is not yet based on consecutive days.
Questions can only be added directly in the database.
Future Improvements

Password hashing, shuffled questions, per-quiz timer, answer review after submission, day-based streaks, an admin screen to manage questions, leaderboard and progress charts.
