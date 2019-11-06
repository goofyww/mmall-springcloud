package com.oecoo.order.controller.backend;

import com.github.pagehelper.PageInfo;
import com.oecoo.order.service.IOrderService;
import com.oecoo.toolset.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gf on 2018/5/11.
 */
@RestController
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    private IOrderService iOrderService;

    @GetMapping("list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return iOrderService.manageList(pageNum, pageSize);
    }

    @GetMapping("detail.do")
    public ServerResponse orderDetail(Long orderNo) {
        return iOrderService.manageDetail(orderNo);
    }

    @GetMapping("search.do")
    public ServerResponse<PageInfo> orderSearch(Long orderNo,
                                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iOrderService.manageSearch(orderNo, pageNum, pageSize);
    }

    @GetMapping("send_goods.do")
    public ServerResponse<String> orderSendGoods(Long orderNo) {
        return iOrderService.manageSendGoods(orderNo);
    }


}
