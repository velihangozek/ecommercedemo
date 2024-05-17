package com.ecommerdedemo.ecommercedemo.business.dtos.response.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerResponse {
    private int id;
    private String name;
    private String email;
    private String phone;
    private Date birthDate;
    private String gender;
}
