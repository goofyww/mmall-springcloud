package com.oecoo.cart.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oecoo.toolset.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {

    private static final long serialVersionUID = -8348310221312265292L;

    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer checked;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date updateTime;

}