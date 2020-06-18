package com.example.quickstart.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用于读取配置文件(yml)中的配置.比如一个项目,需要设定他的初始值时,可以通过此方法,从配置文件中获取到javabean
 */
@ConfigurationProperties(prefix = "test")
public class TestProperties {
    private String var1;
    private Integer var2;

    @Override
    public String toString() {
        return "TestProperties{" +
                "var1='" + var1 + '\'' +
                ", var2=" + var2 +
                '}';
    }

    public String getVar1() {
        return var1;
    }

    public void setVar1(String var1) {
        this.var1 = var1;
    }

    public Integer getVar2() {
        return var2;
    }

    public void setVar2(Integer var2) {
        this.var2 = var2;
    }
}
