package org.devpulse.foodflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.devpulse.foodflow.repository.CustomerRepository;
import org.devpulse.foodflow.service.CustomerService;
import org.devpulse.foodflow.utils.DatabaseInitializer;
import org.devpulse.foodflow.viewmodel.CustomerRegisterViewModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class FoodFlowApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        DatabaseInitializer.initDatabase();

        CustomerRepository customerRepository = new CustomerRepository();
        CustomerService customerService = new CustomerService(customerRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(FoodFlowApplication.class.getResource("/org/devpulse/foodflow/view/MainWindow.fxml"));
        fxmlLoader.setControllerFactory(param -> {
            if (param == CustomerRegisterViewModel.class) {
                return new CustomerRegisterViewModel(customerService);
            }
            try {
                // Retornar el controlador por defecto si no es CustomerRegisterViewModel
                return param.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Foodflow");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}