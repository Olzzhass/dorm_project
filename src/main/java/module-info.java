module com.example.project_inf201 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mail;


    opens com.example.project_inf201 to javafx.fxml;
    exports com.example.project_inf201;
}