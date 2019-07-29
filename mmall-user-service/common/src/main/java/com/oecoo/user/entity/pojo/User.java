package com.oecoo.user.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.oecoo.toolset.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date updateTime;

}