module student_quiz {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens student_quiz to javafx.fxml;

    exports student_quiz;
}