package com.oecoo.shipping.controller.provide;

import com.oecoo.shipping.dao.ShippingMapper;
import com.oecoo.shipping.entity.pojo.Shipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gf
 * @date 2019/7/6 2:23
 */
@RestController
@RequestMapping("/shippingMapper/")
public class ShippingMapperClient {

    @Autowired
    private ShippingMapper shippingMapper;

    @GetMapping("selectByPrimaryKey")
    public Shipping selectByPrimaryKey(@RequestParam("id") Integer id) {
        return shippingMapper.selectByPrimaryKey(id);
    }

}
