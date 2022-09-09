package com.bankingusecase.customer.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Setter
@ToString
@Entity(name="customers")
@NoArgsConstructor
public class Customer {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 120)
    private String address;

    @Column(nullable = false, length = 10)
    private String phoneNumber;

}

