//package com.example.demo.controller;
//
//
//import com.example.demo.constants.WebConstants;
//import com.example.demo.domain.TbSysUser;
//import com.example.demo.service.LoginService;
//import com.example.demo.service.redisUtils.RedisService;
//import com.example.demo.utils.CookieUtils;
//import com.example.demo.utils.MapperUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.UUID;
//
///*单点登录，返回页面*/
//@Controller
//public class LoginControllerBK {
//
//    @Autowired
//    private LoginService loginService;
//
//    @Autowired
//    private RedisService redisService;
//
//    @GetMapping("/")
//    public String index(){
//        return "index";
//    }
//
//
//    @GetMapping("/login")
//    public String login(@RequestParam(required = false) String url, HttpServletRequest request, Model model){
//
//        /*通过token检查是否已经登陆*/
//        String token = CookieUtils.getCookieValue(request, WebConstants.SESSION_TOKEN);
//        /*检查token是否为空*/
//        if (isNotBlank(token)) {
//            /*获取的loginCode*/
//            String loginCode = redisService.get(token);
//            /*检查loginCode是否为空*/
//            if (isNotBlank(loginCode)){
//                /*获取user的json数据*/
//                String json = redisService.get(loginCode);
//                /*检查json是否为空*/
//                if (isNotBlank(json)){
//                    try {
//                        /*最终获取到user*/
//                        TbSysUser tbSysUser = MapperUtils.json2pojo(json, TbSysUser.class);
//                        /*检查是否携带访问地址*/
//                        if (isNotBlank(url)) {
//                            return "redirect:"+url; /*原路返回*/
//                        }
//
//                        /*如果没有登陆信息则显示 登陆成功，此处建议模拟,向登录页面返回welcome和user信息*/
//                        model.addAttribute("message","welcome");
//                        model.addAttribute(WebConstants.SESSION_USER,tbSysUser);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        if (isNotBlank(url)) {
//            model.addAttribute("url",url);
//        }
//        return "login";
//    }
//
//    /*登陆页面*/
//    @PostMapping("/login")
//    public String login(String loginCode, String password,
//                        @RequestParam(required = false) String url,     /*用于获取来访地址（并在登陆成功后跳转），设定默认可以没有（false）*/
//                        HttpServletRequest request, HttpServletResponse response, Model model/*, RedirectAttributes  redirectAttributes*/){
//        TbSysUser tbSysUser = loginService.login(loginCode, password);
//        /*redis存入会失败，设置重试次数*/
//        int retrial = 10;
//
//
//        /*登陆失败*/
//        if (tbSysUser == null){
//            /*↓ = request.getSession().setAttribute() ,springMVC的功能，因为登录失败后会重定向*/
//            model.addAttribute("message","用户名密码错误   "+url);
//
//            /*注释理由：redirectAttributes可以用于跳转后msg传参，但重定向后会向地址栏添加静态资源地址，暂时无法解决，搁置*/
////            redirectAttributes.addFlashAttribute("message","用户名密码错误"+url);
//        }
//
//        /*登陆成功,则设置一个全局的token，存放loginCode供其他服务端调取*/
//        else {
//
//            String token = UUID.randomUUID().toString();
//
////            String result = redisService.put(token, loginCode, 30 * 60);/*在redis中存放token（内含loginCode）,结果为"ok"或者null*/
//            Boolean flag = tryToPutDataIntoRedis(token, loginCode, retrial);
//            /*如果存放成功*/
//            if (flag){
//                /*在cookie中存放token的值*/
//                CookieUtils.setCookie(request,response,WebConstants.SESSION_TOKEN,token,30 * 60);
//
//                /*将json数据放入redis    存在bug，无法放入！原因未知，可能是机器性能问题*/
//                try {
//                    tryToPutDataIntoRedis(loginCode,MapperUtils.obj2json(tbSysUser),retrial);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                if (isNotBlank(url)){   /*当存在来访地址时让其返回原地址，否则统一返回login*/
//                    return "redirect:"+url;
//                }
//
//                model.addAttribute("message","welcome");
//                model.addAttribute(WebConstants.SESSION_USER,tbSysUser);
//                return "login";
//            }
//        }
//        /*如果登陆错误,返回至login(并返回错误信息)*/
//        model.addAttribute("message","服务器异常，无法登陆");
//        return "login";
//    }
//
//    /*登出功能，删除cookie 返回一个login的方法（比较新奇）*/
//    @GetMapping("/logout")
//    public String logout(@RequestParam(required = false) String url,HttpServletRequest request,HttpServletResponse response,Model model){
//        try {
//            CookieUtils.deleteCookie(request,response,WebConstants.SESSION_TOKEN);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return login(url,request,model);
//    }
//
//
//
//
//    /*失败后重试*/
//    private Boolean tryToPutDataIntoRedis(String key, String value, int retrial) {
//        try {
//            for (int i = 0; i < retrial; i++) {
//                String result = redisService.put(key, value, 30 * 60);
//                if (isNotBlank(result) && "ok".equals(result)){
//                    return true;
//                }
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("####redis存储数据失败，超过重试次数上限！####");
//        }
//        return false;
//
//    }
//
//
//    /*guava的2个判断字符串是否为空的方法*/
//    private boolean isBlank(String str) {
//        int strLen;
//        if (str != null && (strLen = str.length()) != 0) {
//            for(int i = 0; i < strLen; ++i) {
//                if (!Character.isWhitespace(str.charAt(i))) {
//                    return false;
//                }
//            }
//
//            return true;
//        } else {
//            return true;
//        }
//    }
//
//    private boolean isNotBlank(String str) {
//        return !isBlank(str);
//    }
//}
