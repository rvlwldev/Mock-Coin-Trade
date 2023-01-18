package com.mockcryptotrade.Domain.Account;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Entity
@Table(name = "ACCOUNT_OLD_PASSWORD")
public class OldPassword {
    @Id
    @Column(name = "ACCOUNT_ID")
    private String id;

    @Column(name = "OLD_PASSWORD")
    private String oldPassword;

    @Column(name = "CHANGED_DATE")
    private Date changeDate;
}
