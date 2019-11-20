package com.example.AdminLET.domain;

import lombok.Data;

@Data
public class UserMessages {
    private Integer total;

    private FirstUserMassage firstUserMassage;
    private SecondUserMassage secondUserMassage;
    private ThirdUserMassage thirdUserMassage;

    @Data
    class FirstUserMassage {
        private String username;
        private String message;
        private String time;
        private String avatar;

    }

    @Data
    class SecondUserMassage{
        private String username;
        private String message;
        private String time;
        private String avatar;

    };

    @Data
    class ThirdUserMassage{
        private String username;
        private String message;
        private String time;
        private String avatar;

    };


}