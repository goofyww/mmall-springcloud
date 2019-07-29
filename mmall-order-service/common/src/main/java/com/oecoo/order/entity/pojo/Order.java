package com.oecoo.order.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oecoo.toolset.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Integer id;

    private Long orderNo;

    private Integer userId;

    private Integer shippingId;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date paymentTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date sendTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date endTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date closeTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date updateTime;

}