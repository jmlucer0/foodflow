package org.devpulse.foodflow.model.dtos;

public class CustomerRegisterDto {
     private final String name;
     private final String lastname;
     private final String address;

    public CustomerRegisterDto(String name, String lastname, String address) {
        this.name = name;
        this.lastname = lastname;
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerRegisterDto{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                '}';
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
