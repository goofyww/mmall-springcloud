package com.oecoo.product.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oecoo.toolset.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date updateTime;

}