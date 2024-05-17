package com.ecommerdedemo.ecommercedemo.business.dtos;


import lombok.Data;

import java.util.Date;

@Data
public class CustomerDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private Date birthDate;
    private String gender;
}
