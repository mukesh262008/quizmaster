package student_quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Quiz {

    private static class Question {

        int id;
        String question;
        String optionA;
        String optionB;
        String optionC;
        String optionD;
        String correctAnswer;

        Question(
                int id,
                String question,
                String optionA,
                String optionB,
                String optionC,
                String optionD,
                String correctAnswer) {

            this.id = id;
            this.question = question;
            this.optionA = optionA;
            this.optionB = optionB;
            this.optionC = optionC;
            this.optionD = optionD;
            this.correctAnswer = correctAnswer;
        }
    }

    private List<Question> questions =
            new ArrayList<>();

    private int currentQuestion = 0;

    private int score = 0;

    private Stage stage;

    private int studentId;

    private String studentName;

    private String topic;

    private Label questionCounter;

    private ProgressBar progressBar;

    private Label questionNumber;

    private Label questionText;

    private VBox optionsBox;

    private ToggleGroup toggleGroup;

    private Button nextButton;

    private Label message;

    // =====================================================
    // SHOW QUIZ
    // =====================================================

    public void show(
            Stage stage,
            int studentId,
            String studentName,
            String topic) {

        this.stage = stage;
        this.studentId = studentId;
        this.studentName = studentName;
        this.topic = topic;

        currentQuestion = 0;
        score = 0;
        questions.clear();

        loadQuestions();

        if (questions.isEmpty()) {

            Alert alert =
                    new Alert(Alert.AlertType.WARNING);

            alert.setTitle("QuizMaster");
            alert.setHeaderText("No Questions Found");
            alert.setContentText(
                    "No questions found for " + topic
            );

            alert.showAndWait();

            TopicSelection topicSelection =
                    new TopicSelection();

            topicSelection.show(
                    stage,
                    studentId,
                    studentName
            );

            return;
        }

        createQuizUI();
        showQuestion();
    }

    // =====================================================
    // LOAD QUESTIONS
    // =====================================================

    private void loadQuestions() {

        String sql =
                "SELECT id, question, option_a, option_b, " +
                "option_c, option_d, correct_answer " +
                "FROM questions " +
                "WHERE topic = ? " +
                "LIMIT 10";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, topic);

            ResultSet rs =
                    statement.executeQuery();

            while (rs.next()) {

                questions.add(
                        new Question(
                                rs.getInt("id"),
                                rs.getString("question"),
                                rs.getString("option_a"),
                                rs.getString("option_b"),
                                rs.getString("option_c"),
                                rs.getString("option_d"),
                                rs.getString("correct_answer")
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Database Error");
            alert.setHeaderText(
                    "Unable to load questions"
            );
            alert.setContentText(
                    e.getMessage()
            );

            alert.showAndWait();
        }
    }

    // =====================================================
    // CREATE QUIZ UI
    // =====================================================

    private void createQuizUI() {

        // -------------------------
        // BACK BUTTON
        // -------------------------

        Button backButton =
                new Button("← Back");

        backButton.getStyleClass()
                .add("quiz-back-button");

        backButton.setOnAction(e -> {

            TopicSelection topicSelection =
                    new TopicSelection();

            topicSelection.show(
                    stage,
                    studentId,
                    studentName
            );
        });

        // -------------------------
        // LOGO
        // -------------------------

        Label logo =
                new Label("QuizMaster");

        logo.getStyleClass()
                .add("quiz-logo");

        // -------------------------
        // TOPIC
        // -------------------------

        Label topicLabel =
                new Label(topic);

        topicLabel.getStyleClass()
                .add("quiz-topic");

        HBox header =
                new HBox(
                        20,
                        backButton,
                        logo,
                        topicLabel
                );

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        header.setPadding(
                new Insets(
                        20,
                        30,
                        10,
                        30
                )
        );

        // -------------------------
        // QUESTION COUNTER
        // -------------------------

        questionCounter =
                new Label();

        questionCounter.getStyleClass()
                .add("question-counter");

        // -------------------------
        // PROGRESS BAR
        // -------------------------

        progressBar =
                new ProgressBar(0);

        progressBar.setMaxWidth(
                Double.MAX_VALUE
        );

        progressBar.getStyleClass()
                .add("quiz-progress");

        // -------------------------
        // QUESTION NUMBER
        // -------------------------

        questionNumber =
                new Label();

        questionNumber.getStyleClass()
                .add("question-number");

        // -------------------------
        // QUESTION TEXT
        // -------------------------

        questionText =
                new Label();

        questionText.setWrapText(true);

        questionText.getStyleClass()
                .add("question-text");

        // -------------------------
        // OPTIONS
        // -------------------------

        optionsBox =
                new VBox(12);

        optionsBox.setAlignment(
                Pos.CENTER_LEFT
        );

        // -------------------------
        // QUESTION CARD
        // -------------------------

        VBox questionCard =
                new VBox(
                        15,
                        questionNumber,
                        questionText,
                        optionsBox
                );

        questionCard.getStyleClass()
                .add("question-card");

        questionCard.setMaxWidth(750);

        // -------------------------
        // NEXT BUTTON
        // -------------------------

        nextButton =
                new Button();

        nextButton.getStyleClass()
                .add("next-button");

        nextButton.setOnAction(
                e -> nextQuestion()
        );

        // -------------------------
        // MESSAGE
        // -------------------------

        message =
                new Label();

        message.getStyleClass()
                .add("quiz-message");

        // -------------------------
        // EXIT BUTTON
        // -------------------------

        Button exitButton =
                new Button("Exit Quiz");

        exitButton.getStyleClass()
                .add("exit-button");

        exitButton.setOnAction(e -> {

            TopicSelection topicSelection =
                    new TopicSelection();

            topicSelection.show(
                    stage,
                    studentId,
                    studentName
            );
        });

        // -------------------------
        // BUTTON AREA
        // -------------------------

        HBox buttons =
                new HBox(
                        15,
                        exitButton,
                        nextButton
                );

        buttons.setAlignment(
                Pos.CENTER
        );

        // -------------------------
        // MAIN CONTENT
        // -------------------------

        VBox content =
                new VBox(
                        15,
                        questionCounter,
                        progressBar,
                        questionCard,
                        message,
                        buttons
                );

        content.setAlignment(
                Pos.CENTER
        );

        content.setMaxWidth(800);

        content.setPadding(
                new Insets(20)
        );

        // -------------------------
        // ROOT
        // -------------------------

        BorderPane root =
                new BorderPane();

        root.getStyleClass()
                .add("quiz-root");

        root.setTop(header);
        root.setCenter(content);

        // -------------------------
        // SCENE
        // -------------------------

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
                "QuizMaster - " + topic
        );

        stage.setScene(scene);

        stage.show();
    }

    // =====================================================
    // SHOW QUESTION
    // =====================================================

    private void showQuestion() {

        Question q =
                questions.get(currentQuestion);

        questionCounter.setText(
                "Question " +
                (currentQuestion + 1) +
                " of " +
                questions.size()
        );

        progressBar.setProgress(
                (double) (currentQuestion + 1)
                / questions.size()
        );

        questionNumber.setText(
                String.format(
                        "QUESTION %02d",
                        currentQuestion + 1
                )
        );

        questionText.setText(
                q.question
        );

        optionsBox.getChildren().clear();

        toggleGroup =
                new ToggleGroup();

        addOption(
                "A",
                q.optionA
        );

        addOption(
                "B",
                q.optionB
        );

        addOption(
                "C",
                q.optionC
        );

        addOption(
                "D",
                q.optionD
        );

        if (currentQuestion ==
                questions.size() - 1) {

            nextButton.setText(
                    "Submit Quiz ✓"
            );

        } else {

            nextButton.setText(
                    "Next Question →"
            );
        }

        message.setText("");
    }

    // =====================================================
    // ADD OPTION
    // =====================================================

    private void addOption(
            String letter,
            String text) {

        RadioButton option =
                new RadioButton(
                        letter + ". " + text
                );

        option.setUserData(letter);

        option.setToggleGroup(
                toggleGroup
        );

        option.setWrapText(true);

        option.setMaxWidth(
                Double.MAX_VALUE
        );

        option.getStyleClass()
                .add("answer-option");

        optionsBox.getChildren()
                .add(option);
    }

    // =====================================================
    // NEXT QUESTION
    // =====================================================

    private void nextQuestion() {

        RadioButton selected =
                (RadioButton)
                        toggleGroup.getSelectedToggle();

        if (selected == null) {

            message.setText(
                    "Please select an answer."
            );

            return;
        }

        String answer =
                selected.getUserData()
                        .toString();

        Question q =
                questions.get(currentQuestion);

        if (answer.equalsIgnoreCase(
                q.correctAnswer)) {

            score++;
        }

        if (currentQuestion <
                questions.size() - 1) {

            currentQuestion++;

            showQuestion();

        } else {

            saveResult();
        }
    }

    // =====================================================
    // SAVE RESULT
    // =====================================================

    private void saveResult() {

        double percentage =
                ((double) score
                / questions.size()) * 100;

        String sql =
                "INSERT INTO quiz_results " +
                "(student_id, topic, score, " +
                "total_questions, percentage) " +
                "VALUES (?, ?, ?, ?, ?)";

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

            statement.setString(
                    2,
                    topic
            );

            statement.setInt(
                    3,
                    score
            );

            statement.setInt(
                    4,
                    questions.size()
            );

            statement.setDouble(
                    5,
                    percentage
            );

            statement.executeUpdate();

            updateStudentStats();

            Result result =
                    new Result();

            result.show(
                    stage,
                    studentId,
                    studentName,
                    topic,
                    score,
                    questions.size()
            );

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Database Error");

            alert.setHeaderText(
                    "Unable to save quiz result"
            );

            alert.setContentText(
                    e.getMessage()
            );

            alert.showAndWait();
        }
    }

    // =====================================================
    // UPDATE STUDENT STATS
    // =====================================================

    private void updateStudentStats() {

        String sql =
                "UPDATE students SET " +
                "quizzes_attempted = quizzes_attempted + 1, " +
                "best_score = GREATEST(best_score, ?), " +
                "streak = streak + 1, " +
                "last_quiz_date = CURDATE() " +
                "WHERE id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    score
            );

            statement.setInt(
                    2,
                    studentId
            );

            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
} 	 	