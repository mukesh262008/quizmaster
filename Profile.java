package student_quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Profile {

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
                .add("profile-back-button");

        Label logo =
                new Label("QuizMaster");

        logo.getStyleClass()
                .add("dashboard-logo");

        Label pageTitle =
                new Label("My Profile");

        pageTitle.getStyleClass()
                .add("profile-page-title");

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
                new Insets(
                        20,
                        30,
                        15,
                        30
                )
        );

        // Back → Dashboard
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
                new Label("Account Settings");

        title.getStyleClass()
                .add("profile-title");

        Label subtitle =
                new Label(
                        "Update your personal information"
                );

        subtitle.getStyleClass()
                .add("profile-subtitle");

        // =========================
        // LOAD STUDENT DATA
        // =========================

        String nameValue = "";
        String emailValue = "";
        String usernameValue = "";

        String sql =
                "SELECT name, email, username " +
                "FROM students WHERE id = ?";

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

            if (rs.next()) {

                nameValue =
                        rs.getString("name");

                emailValue =
                        rs.getString("email");

                usernameValue =
                        rs.getString("username");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        // =========================
        // PROFILE ICON
        // =========================

        Label profileIcon =
                new Label("👤");

        profileIcon.getStyleClass()
                .add("profile-icon");

        Label profileName =
                new Label(nameValue);

        profileName.getStyleClass()
                .add("profile-name");

        Label profileUsername =
                new Label(
                        "@" + usernameValue
                );

        profileUsername.getStyleClass()
                .add("profile-username");

        VBox profileHeader =
                new VBox(
                        5,
                        profileIcon,
                        profileName,
                        profileUsername
                );

        profileHeader.setAlignment(
                Pos.CENTER
        );

        // =========================
        // FORM FIELDS
        // =========================

        Label nameLabel =
                new Label("Full Name");

        nameLabel.getStyleClass()
                .add("profile-field-label");

        TextField nameField =
                new TextField(nameValue);

        nameField.getStyleClass()
                .add("profile-input");

        Label emailLabel =
                new Label("Email");

        emailLabel.getStyleClass()
                .add("profile-field-label");

        TextField emailField =
                new TextField(emailValue);

        emailField.getStyleClass()
                .add("profile-input");

        Label usernameLabel =
                new Label("Username");

        usernameLabel.getStyleClass()
                .add("profile-field-label");

        TextField usernameField =
                new TextField(usernameValue);

        usernameField.setEditable(false);

        usernameField.getStyleClass()
                .add("profile-input");

        Label passwordLabel =
                new Label(
                        "New Password"
                );

        passwordLabel.getStyleClass()
                .add("profile-field-label");

        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText(
                "Leave blank to keep current password"
        );

        passwordField.getStyleClass()
                .add("profile-input");

        // =========================
        // FORM
        // =========================

        GridPane form =
                new GridPane();

        form.setHgap(15);
        form.setVgap(12);

        form.setAlignment(
                Pos.CENTER
        );

        form.add(
                nameLabel,
                0,
                0
        );

        form.add(
                nameField,
                1,
                0
        );

        form.add(
                emailLabel,
                0,
                1
        );

        form.add(
                emailField,
                1,
                1
        );

        form.add(
                usernameLabel,
                0,
                2
        );

        form.add(
                usernameField,
                1,
                2
        );

        form.add(
                passwordLabel,
                0,
                3
        );

        form.add(
                passwordField,
                1,
                3
        );

        // =========================
        // UPDATE BUTTON
        // =========================

        Button updateButton =
                new Button(
                        "Save Changes"
                );

        updateButton.getStyleClass()
                .add("profile-update-button");

        Label message =
                new Label();

        message.getStyleClass()
                .add("profile-message");

        updateButton.setOnAction(e -> {

            String newName =
                    nameField.getText().trim();

            String newEmail =
                    emailField.getText().trim();

            String newPassword =
                    passwordField.getText();

            if (newName.isEmpty()
                    || newEmail.isEmpty()) {

                message.setText(
                        "Name and email cannot be empty."
                );

                return;
            }

            try (
                    Connection connection =
                            DBConnection.getConnection()
            ) {

                String updateSql;

                PreparedStatement statement;

                if (newPassword.isEmpty()) {

                    updateSql =
                            "UPDATE students SET " +
                            "name = ?, email = ? " +
                            "WHERE id = ?";

                    statement =
                            connection.prepareStatement(
                                    updateSql
                            );

                    statement.setString(
                            1,
                            newName
                    );

                    statement.setString(
                            2,
                            newEmail
                    );

                    statement.setInt(
                            3,
                            studentId
                    );

                } else {

                    updateSql =
                            "UPDATE students SET " +
                            "name = ?, email = ?, " +
                            "password = ? " +
                            "WHERE id = ?";

                    statement =
                            connection.prepareStatement(
                                    updateSql
                            );

                    statement.setString(
                            1,
                            newName
                    );

                    statement.setString(
                            2,
                            newEmail
                    );

                    statement.setString(
                            3,
                            newPassword
                    );

                    statement.setInt(
                            4,
                            studentId
                    );
                }

                statement.executeUpdate();

                statement.close();

                message.setText(
                        "Profile updated successfully!"
                );

            } catch (Exception ex) {

                ex.printStackTrace();

                message.setText(
                        "Unable to update profile."
                );
            }
        });

        // =========================
        // DELETE ACCOUNT
        // =========================

        Button deleteButton =
                new Button(
                        "Delete Account"
                );

        deleteButton.getStyleClass()
                .add("profile-delete-button");

        deleteButton.setOnAction(e -> {

            Alert confirmation =
                    new Alert(
                            Alert.AlertType.CONFIRMATION
                    );

            confirmation.setTitle(
                    "Delete Account"
            );

            confirmation.setHeaderText(
                    "Are you sure?"
            );

            confirmation.setContentText(
                    "This will permanently delete " +
                    "your account and quiz history."
            );

            confirmation.showAndWait()
                    .ifPresent(response -> {

                        if (response ==
                                javafx.scene.control.ButtonType.OK) {

                            deleteAccount(
                                    stage,
                                    studentId
                            );
                        }
                    });
        });

        // =========================
        // BUTTON AREA
        // =========================

        HBox buttons =
                new HBox(
                        15,
                        updateButton,
                        deleteButton
                );

        buttons.setAlignment(
                Pos.CENTER
        );

        // =========================
        // PROFILE CARD
        // =========================

        VBox card =
                new VBox(
                        18,
                        profileHeader,
                        form,
                        message,
                        buttons
                );

        card.setAlignment(
                Pos.CENTER
        );

        card.setMaxWidth(600);

        card.getStyleClass()
                .add("profile-card");

        // =========================
        // MAIN CONTENT
        // =========================

        VBox content =
                new VBox(
                        10,
                        title,
                        subtitle,
                        card
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

        // =========================
        // ROOT
        // =========================

        BorderPane root =
                new BorderPane();

        root.setTop(header);
        root.setCenter(content);

        root.getStyleClass()
                .add("profile-root");

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
                "QuizMaster - My Profile"
        );

        stage.setScene(scene);

        stage.show();
    }

    // =====================================================
    // DELETE ACCOUNT
    // =====================================================

    private void deleteAccount(
            Stage stage,
            int studentId) {

        String sql =
                "DELETE FROM students WHERE id = ?";

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

            statement.executeUpdate();

            Login login =
                    new Login();

            login.show(stage);

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert =
                    new Alert(
                            Alert.AlertType.ERROR
                    );

            alert.setTitle(
                    "Delete Account"
            );

            alert.setHeaderText(
                    "Unable to delete account"
            );

            alert.setContentText(
                    e.getMessage()
            );

            alert.showAndWait();
        }
    }
}