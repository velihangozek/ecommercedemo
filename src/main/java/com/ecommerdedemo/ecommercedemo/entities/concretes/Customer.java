package com.ecommerdedemo.ecommercedemo.entities.concretes;

import com.ecommerdedemo.ecommercedemo.entities.abstracts.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender")
    private String gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

     /*
     Explanation
Customer Entity:
@OneToOne(cascade = CascadeType.ALL): This specifies the one-to-one relationship and cascading all operations.
@JoinColumn(name = "cart_id", referencedColumnName = "id"): This defines the foreign key column (cart_id) in the Customer table, which references the primary key (id) of the Cart table.

Cart Entity:
@OneToOne(mappedBy = "cart"): This specifies the inverse side of the one-to-one relationship. It tells JPA that the Customer entity owns the relationship and the foreign key column is defined there.
By setting up your entities this way, JPA understands that the Customer entity has a foreign key (cart_id) linking to the Cart entity, and the Cart entity is aware of the relationship through the mappedBy attribute.
      */

}
