module org.devpulse.foodflow {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens org.devpulse.foodflow.viewmodel to javafx.fxml;
    exports org.devpulse.foodflow;
}