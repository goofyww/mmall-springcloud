package com.oecoo.shipping.entity.transfer;

import com.oecoo.shipping.entity.pojo.Shipping;
import com.oecoo.shipping.entity.vo.ShippingVo;

/**
 * @author gf
 * @date 2019/6/30 20:51
 */
public class ShippingTransfer {

    //转shippingVo
    public static ShippingVo toVo(Shipping shipping) {
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        return shippingVo;
    }

}
