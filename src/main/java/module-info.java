module com.example.particlelife {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.particlelife to javafx.fxml;
    exports com.example.particlelife;
}