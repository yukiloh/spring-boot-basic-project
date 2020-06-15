package com.example.security.controller;

import com.example.security.service.SupportForAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @author yukiloh
 * @version 0.1
 * @date 2020/6/15 15:23
 */
@RestController
@RequestMapping("/annotation/")             //添加了路径前缀
public class AnnotationController {

    @Autowired
    private SupportForAnnotationService supportForAnnotationService;

    /**
     * ====================================
     * @Secured 的用法
     * 配置了ADMIN和USER2个角色,但只要满足任意一个便可以访问,且无法做到and的效果
     * 因此一般用于只允许1个角色的接口
     * 另外角色名前需要手动添加前缀"ROLE_"
     */
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
//    @PreAuthorize("hasAnyRole('user')")   //错误示范,启用的注解类型可以开启多个,但所应用的方法只有1个会生效
    @GetMapping("/secured")
    public String securedAnnotation() {
        return "已访问@Secured({\"ROLE_ADMIN\", \"ROLE_USER\"})";
    }

    //权限拦截的注解也可以添加在service层(SupportForAnnotationService)
    //测试路径: http://localhost:8080/annotation/deleteUser?id=1
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam Integer id) {
        boolean isOk = supportForAnnotationService.deleteUser(id);
        return "成功删除用户: "+isOk;
    }


    /**
     * ====================================
     * @PreAuthorize 和 @PostAuthorize的用法
     * @PreAuthorize: 在方法 被调用前 拦截
     * @PostAuthoriz: 在方法 被调用后 拦截,可以通过returnObject来对返回的结果进行逻辑处理
     * 这2个方法可以在内部使用spring el表达式
     * 可以通过 hasRole("ROLE") 来指定允许的角色.IDEA提示可以使用hasPermission,没有仔细研究
     * 表达式参考地址: https://docs.spring.io/spring-security/site/docs/5.2.2.RELEASE/reference/htmlsingle/#el-common-built-in
     * 测试路径: http://localhost:8080/annotation/preAuthorize
     */
    @GetMapping("/preAuthorize")
    @PreAuthorize("hasRole('USER')")
//    @PreAuthorize("hasRole('USER') AND hasRole('ADMIN')")     //可以通过and来实现需要2个权限的效果
    public String  preAuthorize(){
        return "访问了: @PreAuthorize(\"hasRole('USER')\")\n";
    }

    //测试路径:http://localhost:8080/annotation/postAuthorize?number=4
    @GetMapping("/postAuthorize")
    public String postAuthorize(@RequestParam Integer number){
        Integer even = supportForAnnotationService.isEven(number);
        return "输入的数字是偶数: "+even;
    }

    /**
     * @PreFilter 和 @PostFilter 用法与上面2个类似,
     * 一般用于处理集合类型的返回结果
     * 如果有多个参数,可以通过 filterTarget 来指定集合,再通过 filterObject 来获取集合中的每个元素,进行过滤
     * 这里演示了只保留集合中的偶数
     * @PostFileter 略
     * 测试地址: http://localhost:8080/annotation/preFilter?list=1,2,3,4
     */
    @PreFilter(filterTarget = "list",value = "filterObject % 2 == 0 ")
    @GetMapping("/preFilter")
    public String preFilter(@RequestParam List<Integer> list){
        return "只包含偶数的集合:"+list.toString();
    }

    /**
     * ====================================
     * jsr250相关的注解
     * @DenyAll      :拒绝所有访问
     * @RolesAllowed :参数中只要包含其中一个便放行.可以省略前缀ROLE_,程序会自动添加"ROLE_"
     * @PermitAll    :允许所有访问
     * 测试路径:http://localhost:8080/annotation/jsr250RolesAllowedAdmin
     */
    @RolesAllowed("ADMIN")
    @GetMapping("/jsr250RolesAllowedAdmin")
    public String jsr250RolesAllowedAdmin(){
        return "已访问@RolesAllowed(\"ADMIN\")的页面";
    }
}
