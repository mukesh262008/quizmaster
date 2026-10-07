package student_quiz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {

    public void show(Stage stage) {

        // Logo
        Label title = new Label("QuizMaster");
        title.getStyleClass().add("logo");

        Label subtitle =
                new Label("Test your knowledge. Improve your skills.");
        subtitle.getStyleClass().add("subtitle");

        // Username
        TextField usernameField =
                new TextField();

        usernameField.setPromptText("Username");
        usernameField.getStyleClass().add("input-field");

        // Password
        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("input-field");

        // Login button
        Button loginButton =
                new Button("LOGIN");

        loginButton.getStyleClass().add("login-button");

        // Register button
        Button registerButton =
                new Button("CREATE ACCOUNT");

        registerButton.getStyleClass().add("register-button");

        Label message =
                new Label();

        message.getStyleClass().add("message");

        // Login action
        loginButton.setOnAction(e -> {

            String username =
                    usernameField.getText().trim();

            String password =
                    passwordField.getText().trim();

            if (username.isEmpty() ||
                password.isEmpty()) {

                message.setText(
                        "Please enter username and password.");

                return;
            }

            String sql =
                    "SELECT id, name " +
                    "FROM students " +
                    "WHERE username = ? " +
                    "AND password = ?";

            try (
                    Connection con =
                            DBConnection.getConnection();

                    PreparedStatement ps =
                            con.prepareStatement(sql)
            ) {

                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs =
                        ps.executeQuery();

                if (rs.next()) {

                    int studentId =
                            rs.getInt("id");

                    String name =
                            rs.getString("name");

                    Dashboard dashboard =
                            new Dashboard();

                    dashboard.show(
                            stage,
                            studentId,
                            name);

                } else {

                    message.setText(
                            "Invalid username or password.");
                }

            } catch (Exception ex) {

                message.setText(
                        "Database connection error.");

                ex.printStackTrace();
            }
        });

        // Register action
        registerButton.setOnAction(e -> {

            Register register =
                    new Register();

            register.show(stage);
        });

        // Card
        VBox card =
                new VBox(
                        15,
                        title,
                        subtitle,
                        usernameField,
                        passwordField,
                        loginButton,
                        registerButton,
                        message
                );

        card.getStyleClass().add("login-card");

        card.setAlignment(Pos.CENTER);

        card.setMaxWidth(380);

        // Main layout
        VBox root =
                new VBox(card);

        root.setAlignment(Pos.CENTER);

        root.setPadding(
                new Insets(30));

        // Scene
        Scene scene =
                new Scene(
                        root,
                        700,
                        600);

        // Load CSS
        scene.getStylesheets().add(
                getClass()
                        .getResource("style.css")
                        .toExternalForm()
        );

        stage.setTitle(
                "QuizMaster - Login");

        stage.setScene(scene);

        stage.show();
    }
}