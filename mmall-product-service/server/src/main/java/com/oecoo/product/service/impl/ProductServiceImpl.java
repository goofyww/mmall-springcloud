package com.oecoo.product.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.oecoo.order.entity.pojo.OrderItem;
import com.oecoo.product.common.Const;
import com.oecoo.product.dao.CategoryMapper;
import com.oecoo.product.dao.ProductMapper;
import com.oecoo.product.entity.pojo.Category;
import com.oecoo.product.entity.pojo.Product;
import com.oecoo.product.entity.transfer.ProductTransfer;
import com.oecoo.product.entity.vo.ProductDetailVo;
import com.oecoo.product.entity.vo.ProductListVo;
import com.oecoo.product.service.ICategoryService;
import com.oecoo.product.service.IProductService;
import com.oecoo.toolset.common.ResponseCode;
import com.oecoo.toolset.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by gf on 2018/4/29.
 */
@Service("iProductService")
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    public ServerResponse<String> saveOrUpdateProduct(Product product) {
        if (product != null) {
            //商品不为空再执行操作
            //1.设置用户主图
            if (StringUtils.isNotBlank(product.getSubImages())) {//用户上传的图片不为空
                String[] subStringArray = product.getSubImages().split(",");//使用","逗号拆分图片链接
                if (subStringArray.length > 0) {//上传的图片大于 0
                    product.setMainImage(subStringArray[0]);//设置第一张图片作为主图
                }
            }
            //2.根据id 是否存在执行 相应操作
            if (product.getId() == null) {
                //执行保存操作
                int resultCount = productMapper.checkProductByName(product.getName());
                if (resultCount == 0) {//商品不存在再进行保存操作
                    product.setStatus(2);//默认状态下架
                    resultCount = productMapper.insert(product);
                    if (resultCount > 0) {
                        return ServerResponse.createBySuccessMessage("新增商品成功");
                    }
                    return ServerResponse.createByErrorMessage("新增商品失败");
                }
                return ServerResponse.createByErrorMessage("商品已存在，无法新增");

            } else {
                //执行更新操作
                int resultCount = productMapper.checkProductById(product.getId());
                if (resultCount > 0) {//商品存在再进行更新操作
                    product.setStatus(2);//默认状态下架
                    resultCount = productMapper.updateByPrimaryKeySelective(product);
                    if (resultCount > 0) {
                        return ServerResponse.createBySuccessMessage("更新商品成功");
                    }
                    return ServerResponse.createByErrorMessage("更新商品失败");
                }
                return ServerResponse.createByErrorMessage("商品不存在，无法更新");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }

    //产品上下架
    public ServerResponse<String> setProductStatus(Integer productId, Integer status) {
        int resultCount = productMapper.checkProductById(productId);
        if (resultCount > 0) {
            if (status == null) {
                return ServerResponse.createByErrorMessage("您提交的状态是空的，请重新提交");
            }
            if (status != 1 && status != 2 && status != 3) {//非法状态参数
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
            }
            //开始执行修改
            Product productItem = new Product();
            productItem.setId(productId);
            productItem.setStatus(status);
            resultCount = productMapper.updateByPrimaryKeySelective(productItem);
            if (resultCount > 0) {
                return ServerResponse.createBySuccessMessage("修改商品状态成功");
            }
            return ServerResponse.createByErrorMessage("修改商品状态失败");
        }
        return ServerResponse.createByErrorMessage("商品不存在，无法修改状态");
    }

    //商品详情
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("商品不存在");
        }
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());//set商品的品类
        ProductDetailVo productDetailVo = ProductTransfer.toProductDetailVo(product);
        //没有找到品类 则默认是根节点
        productDetailVo.setParentCategoryId(Objects.isNull(category) ? 0 : category.getParentId());
        return ServerResponse.createBySuccessData(productDetailVo);
    }

    //List 商品 列表
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        /**
         * pageHelper使用
         * 1.StartPage ---->start
         * 2.填充sql查询逻辑
         * 3.pagehelper 收尾
         */
        PageHelper.startPage(pageNum, pageSize);                              // ① StartPage ---->start

        List<ProductListVo> productListVoList = Lists.newArrayList();
        //创建ProductListVo对象 作为前台展示的的对象
        List<Product> productList = productMapper.selectProductList();       //② 填充sql查询逻辑
        //查找所有的商品信息 返回list
        for (Product productItem : productList) {
            //遍历所有的商品信息
            ProductListVo productListVo = ProductTransfer.toProductListVo(productItem);
            //将每个商品都转换为Vo对象
            //加入到List<ProductListVo>集合中 ,稍后返回
            productListVoList.add(productListVo);
        }
        //放入sql查询成功返回的对象                                          //③ pagehelper 收尾
        PageInfo pageResult = new PageInfo(productList);
        //返回前台的信息并不是直接查询的信息，因此需要重置为VO对象
        pageResult.setList(productListVoList);
        //这里返回是pageInfo 里面携带了VO对象本身以及页面信息
        return ServerResponse.createBySuccessData(pageResult);
    }

    //查找
    public ServerResponse<PageInfo> findProduct(Integer productId, String productName, Integer pageNum, Integer pageSize) {
        //① pageHelper----->Start
        PageHelper.startPage(pageNum, pageSize);

        if (StringUtils.isNotBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        //② 填充sql逻辑
        List<Product> products = productMapper.selectByProductName(productId, productName);
        List<ProductListVo> productsVoList = Lists.newArrayList();
        for (Product product : products) {
            ProductListVo producListVo = ProductTransfer.toProductListVo(product);
            productsVoList.add(producListVo);
        }
        //③ pageHelper收尾
        PageInfo pageInfo = new PageInfo(products);
        pageInfo.setList(productsVoList);
        return ServerResponse.createBySuccessData(pageInfo);
    }


    /*------------------------------portal 前台 -------------------*/
    //产品详情
    public ServerResponse<ProductDetailVo> getDetailProduct(Integer productId) {
        if (productId == 0 || null == productId) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品已下架或已删除");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.createByErrorMessage("产品已下架或已删除");
        }
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());//set商品的品类
        ProductDetailVo productDetailVo = ProductTransfer.toProductDetailVo(product);
        //没有找到品类 则默认是根节点
        productDetailVo.setParentCategoryId(Objects.isNull(category) ? 0 : category.getParentId());
        return ServerResponse.createBySuccessData(productDetailVo);
    }

    //产品搜索
    public ServerResponse<PageInfo> getProductKeyword(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {
        //关键字 和 品类id 为空 则参数错误
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        List<Integer> categoryIds = Lists.newArrayList();// 稍后用来接子节点 IdS ,为搜索作查询条件
        if (categoryId != null) {
            //前台有传分类id 则查询 该分类
            Category category = categoryMapper.selectByCategoryId(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                //分类不存在 并且没有关键字 返回一个空的集合 使之不报错
                PageHelper.startPage(pageNum, pageSize);                     //①
                List<ProductListVo> productListVos = Lists.newArrayList();  //②
                PageInfo pageInfo = new PageInfo(productListVos);           //③
                return ServerResponse.createBySuccessData(pageInfo);
            }
            //分类存在 则查询该分类下的所有子节点ID
            categoryIds = iCategoryService.selectCategoryAndChildrenById(categoryId).getData();
        }

        //关键字 和 分类id 集合 查询 商品
        if (StringUtils.isNotBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        PageHelper.startPage(pageNum, pageSize);//①
        //排序处理
        if (StringUtils.isNotBlank(orderBy)) {                                    //②
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] oderByArray = orderBy.split("_");
                PageHelper.orderBy(oderByArray[0] + " " + oderByArray[1]);     //------oderBy存在时 由 pageHelper做排序
            }
        }
        List<Product> products = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword, categoryIds.size() == 0 ? null : categoryIds);

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product productItem : products) {
            ProductListVo productListVo = ProductTransfer.toProductListVo(productItem);
            productListVoList.add(productListVo);
        }

        PageInfo pageInfo = new PageInfo(products);             //③
        pageInfo.setList(productListVoList);

        return ServerResponse.createBySuccessData(pageInfo);
    }

    public ServerResponse<Integer> getStockByProductId(Integer productId) {
        Integer result = productMapper.selectStockByProductId(productId);
        return (Objects.isNull(result) || result < 0) ? ServerResponse.createByErrorMessage("库存数据为空或为不合法值")
                : ServerResponse.createBySuccessData(result);
    }

    public ServerResponse<Integer> editByPrimaryKeySelective(Product product) {
        Integer result = productMapper.updateByPrimaryKeySelective(product);
        return result == 0 ? ServerResponse.createByErrorMessage("更新商品信息失败")
                : ServerResponse.createBySuccessData(result);
    }

    public ServerResponse<Product> findProductByProductId(Integer productId) {
        return ServerResponse.createBySuccessData(productMapper.selectByPrimaryKey(productId));
    }

    /**
     * 根据订单详情下单成功信息 增加库存
     *
     * @param
     * @return
     */
    @Transactional
    public ServerResponse incrProductQuantity(List<List<OrderItem>> orderItemLists) {

        try {
            //一定要用主键where条件，防止锁表，mysql的表需为InnoDB引擎
            //未付款期间，商品可能会被删除的情况
            orderItemLists.forEach(orderItems -> {
                orderItems.forEach(orderItem -> {
                    ServerResponse<Integer> stock = this.getStockByProductId(orderItem.getProductId());
                    if (!stock.isSuccess()) {
                        return;
                    }
                    Product product = new Product();
                    product.setId(orderItem.getProductId());
                    product.setStock(stock.getData() + orderItem.getQuantity());
                    this.editByPrimaryKeySelective(product);
                });
            });
        } catch (RuntimeException e) {
            log.error("incrProductQuantity occur exception: {}", e);
            return ServerResponse.createByErrorMessage("恢复库存失败");
        }
        return ServerResponse.createBySuccess();
    }

    @Transactional
    public ServerResponse decrProductQuantity(List<OrderItem> orderItems) {
        try {
            orderItems.forEach(orderItem -> {
                Product product = this.findProductByProductId(orderItem.getProductId()).getData();
                product.setStock(product.getStock() - orderItem.getQuantity());
                this.editByPrimaryKeySelective(product);
            });
        } catch (RuntimeException e) {
            log.error("decrProductQuantity occur exception: {}", e);
            return ServerResponse.createByErrorMessage("减库存失败");
        }
        return ServerResponse.createBySuccess();
    }

}
