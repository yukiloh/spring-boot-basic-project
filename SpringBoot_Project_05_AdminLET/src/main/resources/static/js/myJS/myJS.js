var Vue = new Vue();

import Vue from 'https://cdn.jsdelivr.net/npm/vue'


    var vmMain = new Vue({
        el: "#message",
        data() {
            return {
                msg: {
                    message: "none",
                },
            }
        },
        mounted() {
            // axios.get("/static/data.json")
            axios.post("http://localhost:8080/"+114514+"/msg")
                .then(response => this.msg = response.data);
        },
    });

    var vmDate = new Vue({
        el: "#date",
        data: {
            <!--获取今日日期-->
            date: new Date().getFullYear() + "/" + ("0" + (new Date().getMonth() + 1)).slice(-2) + "/" + ("0" + new Date().getDate()).slice(-2),
        }

    });
