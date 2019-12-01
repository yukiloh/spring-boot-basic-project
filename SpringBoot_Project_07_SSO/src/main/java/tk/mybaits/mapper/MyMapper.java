package tk.mybaits.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


/**
 * 自己的Mapper,不可被扫描,容易出错
 * @param <T>
 */

public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
