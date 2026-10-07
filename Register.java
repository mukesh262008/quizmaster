package student_quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Register {

    public void show(Stage stage) {

        // =========================
        // HEADER
        // =========================

        Button backButton =
                new Button("← Back");

        backButton.getStyleClass()
                .add("register-back-button");

        Label logo =
                new Label("QuizMaster");

        logo.getStyleClass()
                .add("register-logo");

        HBox header =
                new HBox(
                        20,
                        backButton,
                        logo
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

        // Back → Login
        backButton.setOnAction(e -> {

            Login login =
                    new Login();

            login.show(stage);
        });

        // =========================
        // TITLE
        // =========================

        Label title =
                new Label("Create Your Account");

        title.getStyleClass()
                .add("register-title");

        Label subtitle =
                new Label(
                        "Join QuizMaster and start testing your knowledge"
                );

        subtitle.getStyleClass()
                .add("register-subtitle");

        // =========================
        // FIELDS
        // =========================

        TextField nameField =
                new TextField();

        nameField.setPromptText(
                "Full Name"
        );

        nameField.getStyleClass()
                .add("register-input");

        TextField emailField =
                new TextField();

        emailField.setPromptText(
                "Email"
        );

        emailField.getStyleClass()
                .add("register-input");

        TextField usernameField =
                new TextField();

        usernameField.setPromptText(
                "Username"
        );

        usernameField.getStyleClass()
                .add("register-input");

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Password"
        );

        passwordField.getStyleClass()
                .add("register-input");

        // =========================
        // MESSAGE
        // =========================

        Label message =
                new Label();

        message.getStyleClass()
                .add("register-message");

        // =========================
        // REGISTER BUTTON
        // =========================

        Button registerButton =
                new Button(
                        "Create Account"
                );

        registerButton.getStyleClass()
                .add("register-primary-button");

        registerButton.setMaxWidth(
                Double.MAX_VALUE
        );

        registerButton.setOnAction(e -> {

            String name =
                    nameField.getText().trim();

            String email =
                    emailField.getText().trim();

            String username =
                    usernameField.getText().trim();

            String password =
                    passwordField.getText();

            // -------------------------
            // VALIDATION
            // -------------------------

            if (name.isEmpty()
                    || email.isEmpty()
                    || username.isEmpty()
                    || password.isEmpty()) {

                message.setText(
                        "Please fill in all fields."
                );

                return;
            }

            if (!email.contains("@")) {

                message.setText(
                        "Please enter a valid email."
                );

                return;
            }

            if (password.length() < 4) {

                message.setText(
                        "Password must contain at least 4 characters."
                );

                return;
            }

            // -------------------------
            // DATABASE INSERT
            // -------------------------

            String sql =
                    "INSERT INTO students " +
                    "(name, email, username, password) " +
                    "VALUES (?, ?, ?, ?)";

            try (
                    Connection connection =
                            DBConnection.getConnection();

                    PreparedStatement statement =
                            connection.prepareStatement(sql)
            ) {

                statement.setString(
                        1,
                        name
                );

                statement.setString(
                        2,
                        email
                );

                statement.setString(
                        3,
                        username
                );

                statement.setString(
                        4,
                        password
                );

                statement.executeUpdate();

                message.setText(
                        "Account created successfully! Please login."
                );

                // Clear fields
                nameField.clear();
                emailField.clear();
                usernameField.clear();
                passwordField.clear();

            } catch (Exception ex) {

                ex.printStackTrace();

                if (ex.getMessage() != null
                        && ex.getMessage()
                        .toLowerCase()
                        .contains("duplicate")) {

                    message.setText(
                            "Username or email already exists."
                    );

                } else {

                    message.setText(
                            "Unable to create account."
                    );
                }
            }
        });

        // =========================
        // LOGIN BUTTON
        // =========================

        Button loginButton =
                new Button(
                        "Already have an account? Login"
                );

        loginButton.getStyleClass()
                .add("register-login-button");

        loginButton.setOnAction(e -> {

            Login login =
                    new Login();

            login.show(stage);
        });

        // =========================
        // FORM
        // =========================

        VBox form =
                new VBox(
                        14,
                        nameField,
                        emailField,
                        usernameField,
                        passwordField,
                        registerButton,
                        message,
                        loginButton
                );

        form.setAlignment(
                Pos.CENTER
        );

        form.setMaxWidth(420);

        // =========================
        // CARD
        // =========================

        VBox card =
                new VBox(
                        20,
                        title,
                        subtitle,
                        form
                );

        card.setAlignment(
                Pos.CENTER
        );

        card.setMaxWidth(520);

        card.getStyleClass()
                .add("register-card");

        // =========================
        // ROOT
        // =========================

        BorderPane root =
                new BorderPane();

        root.setTop(header);
        root.setCenter(card);

        root.getStyleClass()
                .add("register-root");

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
                "QuizMaster - Create Account"
        );

        stage.setScene(scene);

        stage.show();
    }
}