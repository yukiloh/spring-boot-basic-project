package com.example.AdminLET.domain;

import javax.persistence.*;

@Table(name = "user_table")
public class UserTable {
    @Id
    @Column(name = "UUID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private String uuid;

    private String account;

    private String perm;

    private String info;

    @Column(name = "lastLoginGMT")
    private String lastlogingmt;

    /**
     * @return UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return perm
     */
    public String getPerm() {
        return perm;
    }

    /**
     * @param perm
     */
    public void setPerm(String perm) {
        this.perm = perm;
    }

    /**
     * @return info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return lastLoginGMT
     */
    public String getLastlogingmt() {
        return lastlogingmt;
    }

    /**
     * @param lastlogingmt
     */
    public void setLastlogingmt(String lastlogingmt) {
        this.lastlogingmt = lastlogingmt;
    }
}