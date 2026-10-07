package student_quiz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dashboard {

    private Label streakValue;
    private Label bestScoreValue;
    private Label quizzesValue;

    public void show(
            Stage stage,
            int studentId,
            String studentName) {

        // =========================
        // LEFT SIDEBAR
        // =========================

        Label logo = new Label("QuizMaster");
        logo.getStyleClass().add("dashboard-logo");

        Button homeButton =
                createMenuButton("⌂   Home");

        Button quizButton =
                createMenuButton("🎯   Take Quiz");

        Button resultsButton =
                createMenuButton("▣   My Results");

        Button profileButton =
                createMenuButton("👤   My Profile");

        Button logoutButton =
                createMenuButton("↪   Logout");

        Region spacer = new Region();

        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox sidebar =
                new VBox(
                        15,
                        logo,
                        homeButton,
                        quizButton,
                        resultsButton,
                        profileButton,
                        spacer,
                        logoutButton
                );

        sidebar.getStyleClass().add("sidebar");

        // =========================
        // TOP BAR
        // =========================

        Label welcome =
                new Label(
                        "Welcome back, " +
                        studentName + " 👋");

        welcome.getStyleClass()
                .add("welcome-text");

        Label smallText =
                new Label(
                        "Ready to test your knowledge?");

        smallText.getStyleClass()
                .add("small-text");

        VBox welcomeBox =
                new VBox(
                        5,
                        welcome,
                        smallText
                );

        Region topSpacer =
                new Region();

        HBox.setHgrow(
                topSpacer,
                Priority.ALWAYS);

        Label userCircle =
                new Label(
                        studentName
                                .substring(0, 1)
                                .toUpperCase());

        userCircle.getStyleClass()
                .add("user-circle");

        HBox topBar =
                new HBox(
                        15,
                        welcomeBox,
                        topSpacer,
                        userCircle
                );

        topBar.setAlignment(
                Pos.CENTER_LEFT);

        // =========================
        // TITLE
        // =========================

        Label heading =
                new Label(
                        "Dashboard");

        heading.getStyleClass()
                .add("dashboard-heading");

        Label description =
                new Label(
                        "Track your progress and continue learning.");

        description.getStyleClass()
                .add("dashboard-description");

        // =========================
        // STAT CARDS
        // =========================

        streakValue =
                new Label("0");

        bestScoreValue =
                new Label("0");

        quizzesValue =
                new Label("0");

        loadStats(studentId);

        VBox streakCard =
                createStatCard(
                        "🔥",
                        "Current Streak",
                        streakValue);

        VBox bestCard =
                createStatCard(
                        "🏆",
                        "Best Score",
                        bestScoreValue);

        VBox quizCard =
                createStatCard(
                        "📝",
                        "Quizzes Taken",
                        quizzesValue);

        HBox stats =
                new HBox(
                        20,
                        streakCard,
                        bestCard,
                        quizCard
                );

        // =========================
        // START QUIZ CARD
        // =========================

        Label quizTitle =
                new Label(
                        "Ready for your next challenge?");

        quizTitle.getStyleClass()
                .add("quiz-card-title");

        Label quizDescription =
                new Label(
                        "Choose a topic and test your knowledge.");

        quizDescription.getStyleClass()
                .add("quiz-card-description");

        Button startQuiz =
                new Button(
                        "START QUIZ  →");

        startQuiz.getStyleClass()
                .add("start-button");

        VBox quizCardBox =
                new VBox(
                        12,
                        quizTitle,
                        quizDescription,
                        startQuiz
                );

        quizCardBox.getStyleClass()
                .add("quiz-card");

        // =========================
        // BUTTON ACTIONS
        // =========================

        startQuiz.setOnAction(e ->
                openTopics(
                        stage,
                        studentId,
                        studentName));

        quizButton.setOnAction(e ->
                openTopics(
                        stage,
                        studentId,
                        studentName));

        resultsButton.setOnAction(e -> {

            ResultsHistory results =
                    new ResultsHistory();

            results.show(
                    stage,
                    studentId,
                    studentName);
        });

        profileButton.setOnAction(e -> {

            Profile profile =
                    new Profile();

            profile.show(
                    stage,
                    studentId,
                    studentName);
        });

        homeButton.setOnAction(e -> {
            // Already on dashboard
        });

        logoutButton.setOnAction(e -> {

            Login login =
                    new Login();

            login.show(stage);
        });

        // =========================
        // MAIN CONTENT
        // =========================

        VBox content =
                new VBox(
                        25,
                        topBar,
                        heading,
                        description,
                        stats,
                        quizCardBox
                );

        content.getStyleClass()
                .add("dashboard-content");

        // =========================
        // MAIN LAYOUT
        // =========================

        BorderPane root =
                new BorderPane();

        root.setLeft(sidebar);

        root.setCenter(content);

        // =========================
        // SCENE
        // =========================

        Scene scene =
                new Scene(
                        root,
                        1100,
                        700);

        scene.getStylesheets().add(
                getClass()
                        .getResource("style.css")
                        .toExternalForm()
        );

        stage.setTitle(
                "QuizMaster - Dashboard");

        stage.setScene(scene);

        stage.show();
    }

    // ==================================
    // CREATE SIDEBAR BUTTON
    // ==================================

    private Button createMenuButton(
            String text) {

        Button button =
                new Button(text);

        button.getStyleClass()
                .add("menu-button");

        return button;
    }

    // ==================================
    // CREATE STAT CARD
    // ==================================

    private VBox createStatCard(
            String icon,
            String title,
            Label value) {

        Label iconLabel =
                new Label(icon);

        iconLabel.getStyleClass()
                .add("stat-icon");

        Label titleLabel =
                new Label(title);

        titleLabel.getStyleClass()
                .add("stat-title");

        value.getStyleClass()
                .add("stat-value");

        VBox card =
                new VBox(
                        8,
                        iconLabel,
                        titleLabel,
                        value
                );

        card.getStyleClass()
                .add("stat-card");

        return card;
    }

    // ==================================
    // LOAD STATISTICS
    // ==================================

    private void loadStats(
            int studentId) {

        String sql =
                "SELECT streak, best_score, " +
                "quizzes_attempted " +
                "FROM students " +
                "WHERE id = ?";

        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, studentId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                streakValue.setText(
                        String.valueOf(
                                rs.getInt("streak")));

                bestScoreValue.setText(
                        String.valueOf(
                                rs.getInt("best_score")));

                quizzesValue.setText(
                        String.valueOf(
                                rs.getInt(
                                        "quizzes_attempted")));
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    // ==================================
    // OPEN TOPIC SCREEN
    // ==================================

    private void openTopics(
            Stage stage,
            int studentId,
            String studentName) {

        TopicSelection topicSelection =
                new TopicSelection();

        topicSelection.show(
                stage,
                studentId,
                studentName);
    }
}