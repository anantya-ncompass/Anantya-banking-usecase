package com.bankingusecase.bank.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Setter
@Entity(name ="bank")
public class Bank {

    @Id
    @Column(nullable = false)
    private String code;

    @Column(nullable = false, length = 50)
    private String bankName;

    @Column(nullable = false, length = 120)
    private String address;



}
