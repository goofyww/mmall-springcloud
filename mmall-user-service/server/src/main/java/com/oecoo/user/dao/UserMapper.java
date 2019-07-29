package com.oecoo.user.dao;

import com.oecoo.user.entity.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository("userMapper")
public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Integer checkUsername(String username);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    Integer checkEmail(String email);

    String selectQuestionByUsername(String username);

    Integer checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);

    Integer checkPassword(@Param("password") String password, @Param("userId") Integer userId);

    Integer checkEmailByUserId(@Param("email") String email, @Param("userId") Integer userId);

}