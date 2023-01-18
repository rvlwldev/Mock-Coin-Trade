package com.mockcryptotrade.Common.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "ACCOUNT_ROLE_CODE")
public class Role {
    @Id
    @Column(name = "ROLE_CODE")
    private int roleCode;

    @Column(name = "ROLE_NAME")
    private int roleName;
}
