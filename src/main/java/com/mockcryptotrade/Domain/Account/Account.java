package com.mockcryptotrade.Domain.Account;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @Column(name = "ACCOUNT_ID")
    private String ID;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE_CODE")
    private int roleCode;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "REGISTRATION_DATE")
    private LocalDateTime registrationDate;
}
