package org.devpulse.foodflow.viewmodel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.devpulse.foodflow.exceptions.InvalidCustomerException;
import org.devpulse.foodflow.model.dtos.CustomerRegisterDto;
import org.devpulse.foodflow.service.CustomerService;

public class CustomerRegisterViewModel {
    private final CustomerService customerService;


    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtCustomerLastname;
    @FXML
    private TextField txtCustomerAddress;
    @FXML
    private Button btnSubmit;

    public CustomerRegisterViewModel(CustomerService customerService) {
        this.customerService = customerService;
    }

    @FXML
    public void initialize() {
        if (txtCustomerName != null && txtCustomerLastname != null && txtCustomerAddress != null) {
            System.out.println("Controles correctamente inicializados.");
        }
        btnSubmit.setOnAction(event -> submitCustomerRegistration());
    }


    public void submitCustomerRegistration() {
        try {
            if (txtCustomerName.getText().isEmpty() || txtCustomerLastname.getText().isEmpty()) {
                throw new InvalidCustomerException("Todos los campos son obligatorios, a excepcion de la direccion");
            }
            CustomerRegisterDto customerRegisterDto = new CustomerRegisterDto(
                    txtCustomerName.getText(),
                    txtCustomerLastname.getText(),
                    txtCustomerAddress.getText()
            );
            System.out.println(customerRegisterDto);
           customerService.registerCustomer(customerRegisterDto);

        } catch (InvalidCustomerException e) {
            e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocurrió un error al registrar el cliente.");
        }
    }


}
