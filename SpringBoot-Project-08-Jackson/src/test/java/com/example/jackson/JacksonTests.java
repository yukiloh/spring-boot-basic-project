package com.example.jackson;

import com.example.jackson.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.json.YamlJsonParser;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.List;
import java.util.Map;

//@SpringBootTest
class JacksonTests {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 1. json - object 互相转换的列子
     *
     */
    @Test
    void mapperTest() throws IOException {
        User user = new User(1,"jerry","11223");

        //序列化(toJson)
        String jsonString = serializedUser(user);

        String temp = "{\n" +
                "  \"my-id\" : 1,\n" +
                "  \"password\" : \"11223\",\n" +
                "  \"username\" : \"jerry\",\n" +
                "  \"time\" : \"2020-06-13 21:32\"\n" +
                "}";


        //反序列化(toObject)
        User deserializedUser = deserializedUser(jsonString);

        //或者将json读取为map
        Map<String, Object> stringObjectMap = deserializedUserMap(jsonString);
    }



    /**
     * 2. 如果需要解析javabean没有的字段,则可以通过设置@JsonAnySetter和@JsonAnyGetter来指定收集到一个map集合中
     */
    @Test
    void deserializeOther() throws JsonProcessingException {
        String json = "{\n" +
                "  \"my-id\" : 1,\n" +
                "  \"password\" : \"11223\",\n" +
                "  \"username\" : \"jerry\",\n" +
                "  \"time\" : \"2020-06-13 21:32\",\n" +

                //添加了2个自定义的对象
                "  \"abc\" : \"abc\",\n" +
                "  \"def\" : 2\n" +
                "}";

        User deserializedUser = deserializedUser(json);
        //打印结果: User{id=1, username='jerry', password='11223', time=Sat Jun 13 21:32:00 CST 2020, other={abc=abc, def=2}}
    }

    /**
     * 3. 通过传入Reader类型(多用于读取文件配置),来解析object
     * 会在根目录生成userJson.json文件
     */
    @Test
    void readValueTest() throws IOException {

        //顺便练习下fileWriter的用法.先通过fileWriter将user序列化后写入文件
        File file = new File("userJson.json");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(serializedUser(new User(1,"jerry","11223")));
        fileWriter.flush();
        fileWriter.close();

        //再读取文件
        FileReader fileReader = new FileReader(file);
        User user = mapper.readValue(fileReader, User.class);
//        User user = mapper.readValue(file, User.class);       //也可以传入file,url,byte[]等
        System.out.println("===result===");
        System.out.println(user);
    }

    /**
     * 4. 读取数组类型的json
     */
    @Test
    void parseList() throws IOException {
        //这里的json是数组[]
        String json = "[{\n" +
                "  \"my-id\" : 1,\n" +
                "  \"password\" : \"11223\",\n" +
                "  \"username\" : \"jerry\",\n" +
                "  \"time\" : \"2020-06-13 21:32\",\n" +

                //添加了2个自定义的对象
                "  \"abc\" : \"abc\",\n" +
                "  \"def\" : 2\n" +
                "}]";

        List<User> userList = mapper.readValue(
                json
                ,new TypeReference<List<User>>() {}
        );

        userList.forEach(user -> System.out.println(user));
    }

    /**
     * 5. 通过添加新依赖(jackson-dataformat-yaml)后,jackson还可以读取yaml文件
     * 会在模块的根目录生成 user.yml 文件
     */
    @Test
    void parseYaml() throws IOException {
        File file = new File("user.yml");

        //需要重新定义mapper
        mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)        //将date写为String,而不是时间戳
        ;

        //写入文件中
        mapper.writeValue(file, new User(1, "tom", "223344"));

        //然后来读取yml,也需要新建一个mapper
        mapper = new ObjectMapper(new YAMLFactory())
                .findAndRegisterModules()               //处理日期
        ;

        //读取yml文件
        User user = mapper.readValue(file, User.class);
        System.out.println(user);
    }


    private String serializedUser(User user) throws JsonProcessingException {
        String jsonString = mapper

                //在序列化时,可以对mapper进行设置
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)    //将时间格式化
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)                  //忽略null的属性
                .setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT)             //忽略默认属性(比如int的默认值0)
                //更多 JsonInclude.Include 下面的用法可以参考: https://www.cnblogs.com/yangy608/p/3936848.html
                .writerWithDefaultPrettyPrinter()

                //通过writeValue来序列化
                .writeValueAsString(user);

        System.out.println(jsonString);
        /**
         * 序列化后的json数据
         * {
         *   "my-id" : 1,                       //id的key取了别名
         *   "password" : "11223",              //password出现在username前
         *   "username" : "jerry",
         *   "time" : "2020-06-13 21:32"        //时间被格式化
         * }
         */

        return jsonString;
    }

    private User deserializedUser(String jsonString) throws JsonProcessingException {
        //反序列化(toObject).通过readValue来序列化String,Byte,Array等
        User deserializedUser = mapper

                //在反序列化时同样可以设置mapper的一些属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false) //忽略java中不存在的属性

                //通过readValue来反序列化
                .readValue(jsonString, User.class);

        System.out.println(deserializedUser);
        return deserializedUser;
    }

    private Map<String, Object> deserializedUserMap(String jsonString) throws IOException {

        Map<String, Object> jsonMap =
                mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});

        jsonMap.forEach((key, value) -> System.out.println("key: "+key+" value:"+value));
        /**
         * 结果
         * key: my-id value:1
         * key: password value:11223
         * key: username value:jerry
         * key: time value:2020-06-14 02:23
         */
        return jsonMap;
    }

}
