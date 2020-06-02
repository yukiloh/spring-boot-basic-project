package com.example.shiro.model;

public class Permission {
    private int pid;
    private String permName;

    @Override
    public String toString() {
        return "Permission{" +
                "pid=" + pid +
                ", permName='" + permName + '\'' +
                '}';
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

}
