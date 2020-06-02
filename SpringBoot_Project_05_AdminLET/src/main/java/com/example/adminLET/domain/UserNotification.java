package com.example.adminLET.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户的通知
 */
@Data
public class UserNotification implements Serializable {
    private Messages messages;
    private Responses responses;
    private Reports reports;

    private static UserNotification userNotification(Messages messages, Responses responses, Reports reports){
        return creatUserNotification(messages,responses,reports);
    }

    private static UserNotification creatUserNotification(Messages messages, Responses responses, Reports reports) {
        UserNotification userNotification = new UserNotification();
        userNotification.setMessages(messages);
        userNotification.setResponses(responses);
        userNotification.setReports(reports);
        return userNotification;
    }

    public Messages creatMessages(Integer total,String time) {
        Messages messages = new Messages();
        messages.setTotal(total);
        messages.setTime(time);
        return messages;
    }

    public Responses creatResponses(Integer total,String time) {
        Responses responses = new Responses();
        responses.setTotal(total);
        responses.setTime(time);
        return responses;
    }

    public Reports creatReports(Integer total,String time) {
        Reports reports = new Reports();
        reports.setTotal(total);
        reports.setTime(time);
        return reports;
    }


    @Data
    private class Messages {
        Integer total;
        String time;
    }

    @Data
    private class Responses {
        Integer total;
        String time;
    }

    @Data
    private class Reports {
        Integer total;
        String time;
    }
}