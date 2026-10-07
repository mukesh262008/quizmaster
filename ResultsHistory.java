package student_quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ResultsHistory {

    public void show(
            Stage stage,
            int studentId,
            String studentName) {

        // =========================
        // HEADER
        // =========================

        Button backButton =
                new Button("← Back");

        backButton.getStyleClass()
                .add("history-back-button");

        Label logo =
                new Label("QuizMaster");

        logo.getStyleClass()
                .add("dashboard-logo");

        Label pageTitle =
                new Label("My Results");

        pageTitle.getStyleClass()
                .add("history-page-title");

        HBox header =
                new HBox(
                        20,
                        backButton,
                        logo,
                        pageTitle
                );

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        header.setPadding(
                new Insets(20, 30, 15, 30)
        );

        backButton.setOnAction(e -> {

            Dashboard dashboard =
                    new Dashboard();

            dashboard.show(
                    stage,
                    studentId,
                    studentName
            );
        });

        // =========================
        // TITLE
        // =========================

        Label title =
                new Label("Quiz History");

        title.getStyleClass()
                .add("history-title");

        Label subtitle =
                new Label(
                        "Track your quiz performance and progress"
                );

        subtitle.getStyleClass()
                .add("history-subtitle");

        // =========================
        // STAT VALUES
        // =========================

        int totalQuizzes = 0;
        int bestScore = 0;
        double totalPercentage = 0;

        VBox resultsContainer =
                new VBox(15);

        resultsContainer.setAlignment(
                Pos.TOP_CENTER
        );

        // =========================
        // LOAD RESULTS
        // =========================

        String sql =
                "SELECT topic, score, total_questions, " +
                "percentage, quiz_date " +
                "FROM quiz_results " +
                "WHERE student_id = ? " +
                "ORDER BY quiz_date DESC";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    studentId
            );

            ResultSet rs =
                    statement.executeQuery();

            while (rs.next()) {

                totalQuizzes++;

                int score =
                        rs.getInt("score");

                int totalQuestions =
                        rs.getInt("total_questions");

                double percentage =
                        rs.getDouble("percentage");

                if (score > bestScore) {
                    bestScore = score;
                }

                totalPercentage += percentage;

                String topic =
                        rs.getString("topic");

                String date =
                        rs.getTimestamp("quiz_date")
                                .toString();

                VBox resultCard =
                        createResultCard(
                                topic,
                                score,
                                totalQuestions,
                                percentage,
                                date
                        );

                resultsContainer
                        .getChildren()
                        .add(resultCard);
            }

        } catch (Exception e) {

            e.printStackTrace();

            Label error =
                    new Label(
                            "Unable to load results."
                    );

            error.getStyleClass()
                    .add("history-empty");

            resultsContainer
                    .getChildren()
                    .add(error);
        }

        // =========================
        // SUMMARY
        // =========================

        double averagePercentage = 0;

        if (totalQuizzes > 0) {

            averagePercentage =
                    totalPercentage /
                    totalQuizzes;
        }

        Label totalValue =
                new Label(
                        String.valueOf(
                                totalQuizzes
                        )
                );

        totalValue.getStyleClass()
                .add("history-stat-value");

        VBox totalCard =
                createStatCard(
                        "📊",
                        "Total Quizzes",
                        totalValue
                );

        Label bestValue =
                new Label(
                        String.valueOf(
                                bestScore
                        )
                );

        bestValue.getStyleClass()
                .add("history-stat-value");

        VBox bestCard =
                createStatCard(
                        "🏆",
                        "Best Score",
                        bestValue
                );

        Label averageValue =
                new Label(
                        String.format(
                                "%.0f%%",
                                averagePercentage
                        )
                );

        averageValue.getStyleClass()
                .add("history-stat-value");

        VBox averageCard =
                createStatCard(
                        "📈",
                        "Average Score",
                        averageValue
                );

        GridPane stats =
                new GridPane();

        stats.setHgap(20);

        stats.setAlignment(
                Pos.CENTER
        );

        stats.add(
                totalCard,
                0,
                0
        );

        stats.add(
                bestCard,
                1,
                0
        );

        stats.add(
                averageCard,
                2,
                0
        );

        // =========================
        // EMPTY MESSAGE
        // =========================

        if (totalQuizzes == 0) {

            Label empty =
                    new Label(
                            "You haven't attempted any quizzes yet."
                    );

            empty.getStyleClass()
                    .add("history-empty");

            resultsContainer
                    .getChildren()
                    .add(empty);
        }

        // =========================
        // SCROLL AREA
        // =========================

        ScrollPane scrollPane =
                new ScrollPane(
                        resultsContainer
                );

        scrollPane.setFitToWidth(true);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.getStyleClass()
                .add("history-scroll");

        // =========================
        // CONTENT
        // =========================

        VBox content =
                new VBox(
                        15,
                        title,
                        subtitle,
                        stats,
                        scrollPane
                );

        content.setAlignment(
                Pos.TOP_CENTER
        );

        content.setPadding(
                new Insets(
                        10,
                        30,
                        30,
                        30
                )
        );

        VBox.setVgrow(
                scrollPane,
                javafx.scene.layout.Priority.ALWAYS
        );

        // =========================
        // ROOT
        // =========================

        BorderPane root =
                new BorderPane();

        root.setTop(header);

        root.setCenter(content);

        root.getStyleClass()
                .add("history-root");

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
                "QuizMaster - My Results"
        );

        stage.setScene(scene);

        stage.show();
    }

    // =====================================================
    // RESULT CARD
    // =====================================================

    private VBox createResultCard(
            String topic,
            int score,
            int totalQuestions,
            double percentage,
            String date) {

        Label topicLabel =
                new Label(topic);

        topicLabel.getStyleClass()
                .add("history-card-topic");

        Label scoreLabel =
                new Label(
                        score +
                        " / " +
                        totalQuestions
                );

        scoreLabel.getStyleClass()
                .add("history-card-score");

        Label percentageLabel =
                new Label(
                        String.format(
                                "%.0f%%",
                                percentage
                        )
                );

        percentageLabel.getStyleClass()
                .add("history-card-percentage");

        Label dateLabel =
                new Label(date);

        dateLabel.getStyleClass()
                .add("history-card-date");

        VBox topicBox =
                new VBox(
                        5,
                        topicLabel,
                        dateLabel
                );

        HBox card =
                new HBox(
                        30,
                        topicBox,
                        scoreLabel,
                        percentageLabel
                );

        card.setAlignment(
                Pos.CENTER_LEFT
        );

        HBox.setHgrow(
                topicBox,
                javafx.scene.layout.Priority.ALWAYS
        );

        VBox wrapper =
                new VBox(card);

        wrapper.getStyleClass()
                .add("history-result-card");

        return wrapper;
    }

    // =====================================================
    // STAT CARD
    // =====================================================

    private VBox createStatCard(
            String iconText,
            String title,
            Label value) {

        Label icon =
                new Label(iconText);

        icon.getStyleClass()
                .add("history-stat-icon");

        Label titleLabel =
                new Label(title);

        titleLabel.getStyleClass()
                .add("history-stat-title");

        VBox card =
                new VBox(
                        5,
                        icon,
                        titleLabel,
                        value
                );

        card.setAlignment(
                Pos.CENTER
        );

        card.setPrefWidth(200);

        card.getStyleClass()
                .add("history-stat-card");

        return card;
    }
}