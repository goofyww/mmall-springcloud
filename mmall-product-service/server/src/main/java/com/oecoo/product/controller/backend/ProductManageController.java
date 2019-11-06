package com.oecoo.product.controller.backend;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.oecoo.product.entity.pojo.Product;
import com.oecoo.product.entity.vo.ProductDetailVo;
import com.oecoo.product.service.IFileService;
import com.oecoo.product.service.IProductService;
import com.oecoo.toolset.common.ServerResponse;
import com.oecoo.toolset.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by gf on 2018/4/29.
 */
//@CrossOrigin//解决跨域问题 （仅支持所标注的类或api方法）
@RestController
@RequestMapping("/manage/product/")
@Slf4j
public class ProductManageController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @PostMapping(value = "save_or_update.do")
    public ServerResponse productSave(Product product) {
        return iProductService.saveOrUpdateProduct(product);
    }

    @PostMapping(value = "set_sale_status.do")
    public ServerResponse setProductStatus(Integer productId, Integer status) {
        return iProductService.setProductStatus(productId, status);
    }

    @GetMapping(value = "detail.do")
    public ServerResponse<ProductDetailVo> productDetail(Integer productId) {
        return iProductService.manageProductDetail(productId);
    }

    /**
     * selectByPrimaryKey
     *
     * @param productId
     * @return
     */
    @GetMapping(value = "get/{productId}")
    public ServerResponse<Product> productGet(@PathVariable("productId") Integer productId) {
        return iProductService.findProductByProductId(productId);
    }

    @GetMapping(value = "list.do")
    public ServerResponse<PageInfo> productGetList(//设置分页默认属性值
                                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iProductService.getProductList(pageNum, pageSize);
    }

    @GetMapping(value = "search.do")
    public ServerResponse<PageInfo> productSearch(Integer productId,
                                                  String productName,
                                                  //设置分页默认属性值
                                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iProductService.findProduct(productId, productName, pageNum, pageSize);
    }

    @PostMapping(value = "upload.do")
    public ServerResponse upload(HttpServletRequest request,
                                 @RequestParam(value = "upload_file", required = false) MultipartFile file) {
        //获取到上下文路径下的upload文件夹的路径
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        Map<String, Object> map = Maps.newHashMap();
        map.put("fileName", targetFileName);
        map.put("url", url);
        return ServerResponse.createBySuccessData(map);
    }

    @PostMapping(value = "richtext_img_upload.do")
    public Map richtextImgUpload(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam(value = "upload_file", required = false) MultipartFile file) {
        /**
         * 富文本中对于返回值有自己的要求,这里使用是simditor所以按照simditor的要求进行返回
         *  {
         *  "success": true/false,
         *  "msg": "error message", # optional
         *  "file_path": "[real file path]"
         *  }
         */
        Map resultMap = Maps.newHashMap();
        //获取到上下文路径下的upload文件夹的路径
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        if (StringUtils.isBlank(targetFileName)) {
            resultMap.put("success", false);
            resultMap.put("msg", "上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
        resultMap.put("file_path", url);
        response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
        return resultMap;
    }

}
