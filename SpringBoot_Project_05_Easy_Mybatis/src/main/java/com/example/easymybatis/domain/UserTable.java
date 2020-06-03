package com.example.easymybatis.domain;

import javax.persistence.*;

@Table(name = "user_table")
public class UserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer uid;

    @Column(name = "userName")
    private String username;

    private String password;

    private String salt;

    private String perm;

    private Integer role;

    /**
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return userName
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
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
     * @return role
     */
    public Integer getRole() {
        return role;
    }

    /**
     * @param role
     */
    public void setRole(Integer role) {
        this.role = role;
    }
}