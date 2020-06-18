package tk.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 用于mbg生成实体类.原理是赋予MyMapper tk.mybatis中的查询方法.一般
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
