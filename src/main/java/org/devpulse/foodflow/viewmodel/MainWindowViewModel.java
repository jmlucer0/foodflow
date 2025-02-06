package org.devpulse.foodflow.viewmodel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowViewModel implements Initializable {

    @FXML
    private StackPane mainStackPane;

    @FXML
    private TreeView<String> trvMenu;

    public MainWindowViewModel() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> rootMenu = new TreeItem<>("Menu");
        rootMenu.setExpanded(true);


        TreeItem<String> orderItem = new TreeItem<>("Pedido");
        TreeItem<String> customerItem = new TreeItem<>("Cliente");
        TreeItem<String> productItem = new TreeItem<>("Producto");

        TreeItem<String> registerOrder = new TreeItem<>("Nuevo Pedido");
        TreeItem<String> listOrders = new TreeItem<>("Lista de Pedidos");


        TreeItem<String> registerCustomer = new TreeItem<>("Nuevo Cliente");
        TreeItem<String> listCustomers = new TreeItem<>("Lista de clientes");

        TreeItem<String> registerProduct = new TreeItem<>("Nuevo Producto");
        TreeItem<String> listProducts = new TreeItem<>("Lista de Productos");

        orderItem.getChildren().addAll(registerOrder, listOrders);
        customerItem.getChildren().addAll(registerCustomer, listCustomers);
        productItem.getChildren().addAll(registerProduct, listProducts);


        rootMenu.getChildren().addAll(orderItem, customerItem, productItem);

        trvMenu.setRoot(rootMenu);

        trvMenu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Seleccionaste: " + newValue.getValue());
                if ("Nuevo Cliente".equals(newValue.getValue())) {
                    cargarVista("CustomerRegisterView.fxml");
                }
            }
        });

    }


    private void cargarVista(String fxmlFile) {
        try {
            System.out.println("Intentando cargar: /org/devpulse/foodflow/view/" + fxmlFile);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/devpulse/foodflow/view/" + fxmlFile));
            if (loader.getLocation() == null) {
                throw new IOException("No se encontró el archivo FXML: " + fxmlFile);
            }
            Node newView = loader.load();
            mainStackPane.getChildren().clear(); // Limpiar la vista actual
            mainStackPane.getChildren().add(newView); // Agregar la nueva vista
        } catch (IOException e) {
            System.err.println("Error al cargar la vista: " + fxmlFile);
            e.printStackTrace();
        }
    }
}
