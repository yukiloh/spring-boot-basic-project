package com.example.demo.mapper;

import com.example.demo.model.VerificationQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IVerificationQuestionMapper {

    @Select("SELECT COUNT(id) FROM verification_table")
    Integer countVerificationQuestion();

    @Select("SELECT * FROM verification_table where id = #{id}")
    VerificationQuestion findVerificationQuestionById(Integer id);
}
