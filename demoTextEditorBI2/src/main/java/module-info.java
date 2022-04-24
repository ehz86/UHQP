module com.example.demotexteditorbi2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demotexteditorbi2 to javafx.fxml;
    exports com.example.demotexteditorbi2;
}