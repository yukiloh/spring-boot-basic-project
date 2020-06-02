package com.example.adminLET.domain;

import lombok.Data;

/**
 * Lombok,虽然我不是很喜欢使用,宁可生成get,set
 */
@Data
public class Account {
    private String uuid;
    private String account;
    private String perm;
    private String info;
    private String lastLoginGMT;

}
