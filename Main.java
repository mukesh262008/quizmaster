package student_quiz;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Login login = new Login();

        login.show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}