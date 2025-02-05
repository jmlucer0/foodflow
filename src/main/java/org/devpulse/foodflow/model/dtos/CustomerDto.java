package org.devpulse.foodflow.model.dtos;

import org.devpulse.foodflow.model.Customer;

public class CustomerDto {
    private final String name;
    private final String lastname;
    private final String address;


    public CustomerDto(String name, String lastname, String address) {
        this.name = name;
        this.lastname = lastname;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

}
