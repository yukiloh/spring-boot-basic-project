package com.example.AdminLET.domain;

import lombok.Data;

@Data
public class Account {
    private String uuid;
    private String account;
    private String perm;
    private String info;
    private String lastLoginGMT;

}
