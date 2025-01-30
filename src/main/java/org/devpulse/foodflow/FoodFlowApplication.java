package org.devpulse.foodflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.devpulse.foodflow.utils.DatabaseInitializer;

import java.io.IOException;
import java.sql.SQLException;

public class FoodFlowApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        DatabaseInitializer.initDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(FoodFlowApplication.class.getResource("/org/devpulse/foodflow/view/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}