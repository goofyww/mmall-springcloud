package com.oecoo.order.dao;

import com.oecoo.order.entity.pojo.PayInfo;
import org.springframework.stereotype.Repository;

@Repository("payInfoMapper")
public interface PayInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);

}