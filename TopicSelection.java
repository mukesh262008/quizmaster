package student_quiz;

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

public class TopicSelection {

    public void show(Stage stage, int studentId, String studentName) {

        Button backButton = new Button("← Back");
        backButton.getStyleClass().add("topic-back-button");

        backButton.setOnAction(e -> {
            Dashboard dashboard = new Dashboard();
            dashboard.show(stage, studentId, studentName);
        });

        Label logo = new Label("QuizMaster");
        logo.getStyleClass().add("dashboard-logo");

        HBox header = new HBox(20, backButton, logo);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 30, 10, 30));

        Label title = new Label("Choose Your Challenge");
        title.getStyleClass().add("topic-title");

        Label subtitle = new Label(
                "Select a topic and test your knowledge"
        );
        subtitle.getStyleClass().add("topic-subtitle");

        VBox heading = new VBox(8, title, subtitle);
        heading.setAlignment(Pos.CENTER);

        GridPane grid = new GridPane();

        grid.setHgap(30);
        grid.setVgap(30);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(30, 40, 40, 40));

        VBox javaCard = createTopicCard(
                "☕",
                "Java",
                "Core Java concepts",
                "Java",
                stage,
                studentId,
                studentName
        );

        VBox oopCard = createTopicCard(
                "▯",
                "OOP",
                "Object Oriented Programming",
                "OOP",
                stage,
                studentId,
                studentName
        );

        VBox jdbcCard = createTopicCard(
                "🔗",
                "JDBC",
                "Java Database Connectivity",
                "JDBC",
                stage,
                studentId,
                studentName
        );

        VBox sqlCard = createTopicCard(
                "▤",
                "SQL",
                "Database & SQL queries",
                "SQL",
                stage,
                studentId,
                studentName
        );

        VBox gkCard = createTopicCard(
                "🌍",
                "General Knowledge",
                "Test your general knowledge",
                "GK",
                stage,
                studentId,
                studentName
        );

        VBox moviesCard = createTopicCard(
                "🎬",
                "Movies",
                "Movies & entertainment",
                "Movies",
                stage,
                studentId,
                studentName
        );

        grid.add(javaCard, 0, 0);
        grid.add(oopCard, 1, 0);

        grid.add(jdbcCard, 0, 1);
        grid.add(sqlCard, 1, 1);

        grid.add(gkCard, 0, 2);
        grid.add(moviesCard, 1, 2);

        VBox content = new VBox(
                20,
                heading,
                grid
        );

        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(10, 30, 30, 30));

        // ==============================
        // SCROLLABLE CONTENT
        // ==============================

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);

        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setVbarPolicy(
                ScrollPane.ScrollBarPolicy.AS_NEEDED
        );

        scrollPane.setPannable(true);

        scrollPane.getStyleClass().add("topic-scroll");

        // ==============================
        // ROOT
        // ==============================

        BorderPane root = new BorderPane();

        root.getStyleClass().add("topic-root");

        root.setTop(header);

        // THIS FIXES THE SCROLLING
        root.setCenter(scrollPane);

        // ==============================
        // SCENE
        // ==============================

        Scene scene = new Scene(root, 1000, 700);

        scene.getStylesheets().add(
                getClass()
                        .getResource("style.css")
                        .toExternalForm()
        );

        stage.setTitle("QuizMaster - Choose Topic");

        stage.setScene(scene);

        stage.show();
    }

    // ==================================================
    // TOPIC CARD
    // ==================================================

    private VBox createTopicCard(
            String iconText,
            String topicName,
            String description,
            String topic,
            Stage stage,
            int studentId,
            String studentName
    ) {

        Label icon = new Label(iconText);
        icon.getStyleClass().add("topic-icon");

        Label name = new Label(topicName);
        name.getStyleClass().add("topic-card-title");

        Label desc = new Label(description);
        desc.getStyleClass().add("topic-card-description");

        desc.setWrapText(true);

        Button startButton = new Button(
                "Start Quiz  →"
        );

        startButton.getStyleClass().add(
                "topic-start-button"
        );

        startButton.setOnAction(e -> {

            Quiz quiz = new Quiz();

            quiz.show(
                    stage,
                    studentId,
                    studentName,
                    topic
            );
        });

        VBox card = new VBox(
                15,
                icon,
                name,
                desc,
                startButton
        );

        card.setAlignment(Pos.CENTER);

        card.setPrefWidth(570);
        card.setMinHeight(260);

        card.getStyleClass().add(
                "topic-card"
        );

        return card;
    }
}