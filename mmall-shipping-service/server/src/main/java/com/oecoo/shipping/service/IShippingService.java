package com.oecoo.shipping.service;

import com.github.pagehelper.PageInfo;
import com.oecoo.shipping.entity.pojo.Shipping;
import com.oecoo.toolset.common.ServerResponse;

/**
 * Created by gf on 2018/5/3.
 */
public interface IShippingService {

    ServerResponse addShipping(Integer userId, Shipping shipping);

    ServerResponse updateShipping(Integer userId, Shipping shipping);

    ServerResponse deleteShipping(Integer userId, Integer shippingId);

    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);

}
