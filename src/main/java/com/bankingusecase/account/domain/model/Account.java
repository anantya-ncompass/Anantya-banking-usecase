package com.bankingusecase.account.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@ToString
@Entity(name="account")
@NoArgsConstructor
public class Account {

    @Id
    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false, length = 50)
    private String customerId;

    @Column(nullable = false, length = 120)
    private String bankCode;

    @Column(nullable = false, length = 120)
    private String accountType;

    @Column(nullable = false, length = 10)
    private double balance;

}

