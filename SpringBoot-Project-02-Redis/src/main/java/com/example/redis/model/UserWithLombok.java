package com.example.redis.model;


import lombok.*;

/**
 * lombok的使用,idea中需要另外安装插件
 */

//生成成员变量的getter/setter
@Getter
@Setter

//重写toString,一般后面需要添加of火鹤exclude
//通过of来限定显示哪些字段,通过exclude来排除.当存在of时,exclude会被忽视
@ToString(of = {"id"},exclude = {"username"})

//构造函数,一般全参和无参用的比较多
@NoArgsConstructor          //无参
@AllArgsConstructor         //全参
@RequiredArgsConstructor    //生成包含常量,@NonNull成员变量的构造函数
//可以通过↓来提供一个of的静态方法来构建构造函数,例如:Object.of(var1,var2...)
//@NoArgsConstructor(staticName = "of",access = AccessLevel.PRIVATE)

//是这些注解的集合: @ToString @EqualsAndHashCode @Getter @Setter @RequiredArgsConstructor
@Data

public class UserWithLombok {

    //对象生成时,字段不能为空.即构造函数必须传入该字段,而且不能为null
    @NonNull private Integer id;
    private String username;

    //其使用和@Cleanup注解在 /test/RedisTests.java 中

}
