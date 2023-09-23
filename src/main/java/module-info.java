module com.example.project1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.project1 to javafx.fxml;
    exports com.example.project1;
}