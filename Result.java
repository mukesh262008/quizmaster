package student_quiz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Result {

    public void show(
            Stage stage,
            int studentId,
            String studentName,
            String topic,
            int score,
            int totalQuestions) {

        // Calculate percentage
        double percentage =
                ((double) score / totalQuestions) * 100;

        // =========================
        // TOP HEADER
        // =========================

        Button backButton = new Button("← Back");
        backButton.getStyleClass()
                .add("result-back-button");

        Label logo = new Label("QuizMaster");
        logo.getStyleClass()
                .add("result-logo");

        HBox header = new HBox(
                20,
                backButton,
                logo
        );

        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(
                new Insets(20, 30, 10, 30)
        );

        // Back button → Topic Selection
        backButton.setOnAction(e -> {
            TopicSelection topicSelection =
                    new TopicSelection();

            topicSelection.show(
                    stage,
                    studentId,
                    studentName
            );
        });

        // =========================
        // RESULT ICON
        // =========================

        Label icon;

        if (percentage >= 80) {
            icon = new Label("🏆");
        } else if (percentage >= 50) {
            icon = new Label("🎉");
        } else {
            icon = new Label("📚");
        }

        icon.getStyleClass()
                .add("result-icon");

        // =========================
        // TITLE
        // =========================

        Label title =
                new Label("Quiz Completed!");

        title.getStyleClass()
                .add("result-title");

        // =========================
        // STUDENT NAME
        // =========================

        Label student =
                new Label(
                        "Well done, " +
                        studentName +
                        "!"
                );

        student.getStyleClass()
                .add("result-student");

        // =========================
        // TOPIC
        // =========================

        Label topicLabel =
                new Label(
                        "Topic: " + topic
                );

        topicLabel.getStyleClass()
                .add("result-topic");

        // =========================
        // SCORE
        // =========================

        Label scoreCaption =
                new Label("Your Score");

        scoreCaption.getStyleClass()
                .add("result-caption");

        Label scoreLabel =
                new Label(
                        score +
                        " / " +
                        totalQuestions
                );

        scoreLabel.getStyleClass()
                .add("result-score");

        Label percentageLabel =
                new Label(
                        String.format(
                                "%.0f%%",
                                percentage
                        )
                );

        percentageLabel.getStyleClass()
                .add("result-percentage");

        VBox scoreCard =
                new VBox(
                        5,
                        scoreCaption,
                        scoreLabel,
                        percentageLabel
                );

        scoreCard.setAlignment(
                Pos.CENTER
        );

        scoreCard.getStyleClass()
                .add("result-score-card");

        // =========================
        // PERFORMANCE MESSAGE
        // =========================

        String performanceText;

        if (percentage >= 80) {

            performanceText =
                    "Excellent! You're doing great! 🔥";

        } else if (percentage >= 60) {

            performanceText =
                    "Good job! Keep improving! 💪";

        } else if (percentage >= 40) {

            performanceText =
                    "Nice attempt! Keep practicing! 📖";

        } else {

            performanceText =
                    "Keep learning and try again! 🚀";
        }

        Label performance =
                new Label(performanceText);

        performance.getStyleClass()
                .add("result-performance");

        // =========================
        // TAKE ANOTHER QUIZ BUTTON
        // =========================

        Button anotherQuizButton =
                new Button(
                        "Take Another Quiz  →"
                );

        anotherQuizButton
                .getStyleClass()
                .add("result-primary-button");

        anotherQuizButton.setOnAction(e -> {

            TopicSelection topicSelection =
                    new TopicSelection();

            topicSelection.show(
                    stage,
                    studentId,
                    studentName
            );
        });

        // =========================
        // DASHBOARD BUTTON
        // =========================

        Button dashboardButton =
                new Button(
                        "Back to Dashboard"
                );

        dashboardButton
                .getStyleClass()
                .add("result-secondary-button");

        dashboardButton.setOnAction(e -> {

            Dashboard dashboard =
                    new Dashboard();

            dashboard.show(
                    stage,
                    studentId,
                    studentName
            );
        });

        // =========================
        // BUTTON CONTAINER
        // =========================

        VBox buttons =
                new VBox(
                        12,
                        anotherQuizButton,
                        dashboardButton
                );

        buttons.setAlignment(
                Pos.CENTER
        );

        // =========================
        // RESULT CONTENT
        // =========================

        VBox content =
                new VBox(
                        15,
                        icon,
                        title,
                        student,
                        topicLabel,
                        scoreCard,
                        performance,
                        buttons
                );

        content.setAlignment(
                Pos.CENTER
        );

        content.setMaxWidth(550);

        content.getStyleClass()
                .add("result-content");

        // =========================
        // MAIN ROOT
        // =========================

        BorderPane root =
                new BorderPane();

        root.getStyleClass()
                .add("result-root");

        // Header at TOP
        root.setTop(header);

        // Result content at CENTER
        root.setCenter(content);

        BorderPane.setAlignment(
                content,
                Pos.CENTER
        );

        // =========================
        // SCENE
        // =========================

        Scene scene =
                new Scene(
                        root,
                        1000,
                        700
                );

        scene.getStylesheets().add(
                getClass()
                        .getResource("style.css")
                        .toExternalForm()
        );

        stage.setTitle(
                "QuizMaster - Result"
        );

        stage.setScene(scene);

        stage.show();
    }
}