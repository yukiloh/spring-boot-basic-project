package com.example.shiro.controller;

import com.example.shiro.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于欢迎页面和登录的controller
 */

@Controller
public class LoginController {

    /**
     * 欢迎页面
     */
    @GetMapping("/")
    public String welcome() {
        return "/welcome";
    }

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    /**
     * 进行登录,这里图省事使用了get登录
     */
    @GetMapping("/toLogin")
    public String toLogin(String username,String password,Model model, HttpServletRequest request) {
        //subject可以理解为"对象",抽象概念,会与系统进行交互
        Subject subject = SecurityUtils.getSubject();

        //根据传入的name和password封装成一个token,之后会在realm中会进行认证
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        //执行登陆方法,通过try catch的方法进行判断
        try {
            //一旦执行login便会到realm中进行认证操作,可以搜索doGetAuthenticationInfo来查看他是如何进行认证的
            subject.login(token);

            //设置attr
            User user = (User)subject.getPrincipal();
            request.getSession().setAttribute("username",user.getUserName());
            return "redirect:/user/success";
        } catch (UnknownAccountException e) {
            //如果执行subject.login(token)操作出现异常,则会根据异常类型进行判断
            //当账号不存在时,抛出unknown account异常
            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            //当密码错误,抛出IncorrectCredentialsException
            System.out.println("密码错误");
            model.addAttribute("msg", "密码错误");
            return "login";
        }
        //我觉得大部分时候不应该详细告诉用户到底什么错了,以防不良分子
    }

}
