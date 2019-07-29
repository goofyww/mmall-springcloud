package com.oecoo.shipping.entity.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oecoo.toolset.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipping {

    private Integer id;

    private Integer userId;

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtil.FORMAT_ON_SECEND_24)
    private Date updateTime;

}