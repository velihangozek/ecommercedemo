package com.ecommerdedemo.ecommercedemo.business.dtos.request.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCustomerRequest {
    private String name;
    private String email;
    private String phone;
    private Date birthDate;
    private String gender;
}
