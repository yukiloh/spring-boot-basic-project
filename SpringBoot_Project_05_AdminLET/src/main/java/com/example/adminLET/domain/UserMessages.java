package com.example.adminLET.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户的消息
 */

@Data
public class UserMessages implements Serializable {
    private Integer total;

    private First first;
    private Second second;
    private Third third;

    public static UserMessages userMessages (Integer total, First firstUserMassage, Second secondUserMassage, Third thirdUserMassage){
        return creatUserMessages(total,firstUserMassage,secondUserMassage,thirdUserMassage);
    }

    private static UserMessages creatUserMessages(Integer total, First firstUserMassage, Second secondUserMassage, Third thirdUserMassage){
        UserMessages userMessages = new UserMessages();
        userMessages.setTotal(total);
        userMessages.setFirst(firstUserMassage);
        userMessages.setSecond(secondUserMassage);
        userMessages.setThird(thirdUserMassage);
        return userMessages;
    }

    public First creatFirstUserMessages(String username, String message, String time, String avatar){
        First firstUserMassage = new First();
        firstUserMassage.setUsername(username);
        firstUserMassage.setMessage(message);
        firstUserMassage.setTime(time);
        firstUserMassage.setAvatar(avatar);
        return firstUserMassage;
    }

    public Second creatSecondUserMessages(String username, String message, String time, String avatar){
        Second secondUserMassage = new Second();
        secondUserMassage.setUsername(username);
        secondUserMassage.setMessage(message);
        secondUserMassage.setTime(time);
        secondUserMassage.setAvatar(avatar);
        return secondUserMassage;
    }

    public Third creatThirdUserMessages(String username, String message, String time, String avatar){
        Third thirdUserMassage = new Third();
        thirdUserMassage.setUsername(username);
        thirdUserMassage.setMessage(message);
        thirdUserMassage.setTime(time);
        thirdUserMassage.setAvatar(avatar);
        return thirdUserMassage;
    }


    @Data
    private class First {
        private String username;
        private String message;
        private String time;
        private String avatar;

    }

    @Data
    private class Second {
        private String username;
        private String message;
        private String time;
        private String avatar;

    }

    @Data
    private class Third {
        private String username;
        private String message;
        private String time;
        private String avatar;

    }


}