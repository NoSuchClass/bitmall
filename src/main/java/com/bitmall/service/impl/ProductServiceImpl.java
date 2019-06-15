package com.bitmall.service.impl;

import com.bitmall.common.Const;
import com.bitmall.common.ResponseCode;
import com.bitmall.common.ServiceResponse;
import com.bitmall.dao.CategoryMapper;
import com.bitmall.dao.ProductMapper;
import com.bitmall.dataobject.ProductDO;
import com.bitmall.service.IProductService;
import com.bitmall.util.DateTimeUtil;
import com.bitmall.util.PropertiesUtil;
import com.bitmall.vo.ProductDetailVO;
import com.bitmall.vo.ProductListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyuehe
 * @date 2019/6/15 15:38
 */
@Service(value = "iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapperl;

    @Override
    public ServiceResponse saveOrUpdateProduct(ProductDO product) {
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }

            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {
                    return ServiceResponse.createBySuccessMessage("更新商品信息成功");
                }
                return ServiceResponse.createByErrorByMessage("更新商品信息失败");
            } else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return ServiceResponse.createBySuccessMessage("添加商品信息成功");
                }
                return ServiceResponse.createByErrorByMessage("添加商品信息失败");
            }
        }
        return ServiceResponse.createByErrorByMessage("新增或更新产品参数不正确");
    }

    @Override
    public ServiceResponse setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        ProductDO product = new ProductDO();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServiceResponse.createByErrorByMessage("修改商品销售状态成功");
        }
        return ServiceResponse.createByErrorByMessage("修改商品销售状态失败");
    }

    @Override
    public ServiceResponse<ProductDetailVO> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        ProductDO product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServiceResponse.createByErrorByMessage("商品已下架或已被删除");
        }
        ProductDetailVO productDetailVo = assembleProductDetailVo(product);
        return ServiceResponse.createBySuccess(productDetailVo);
    }

    @Override
    public ServiceResponse<PageInfo> getList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductDO> productList = productMapper.selectList();
        List<ProductListVO> productListVOList = Lists.newArrayList();
        for(ProductDO productItem : productList) {
            ProductListVO productListVO = assembleProductListVo(productItem);
            productListVOList.add(productListVO);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVOList);
        return ServiceResponse.createBySuccess(pageResult);
    }

    @Override
    public ServiceResponse productSearch(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<ProductDO> productDOList = productMapper.selectByNameAndProductId(productName, productId);
        List<ProductListVO> productListVOList = Lists.newArrayList();
        for(ProductDO productItem : productDOList) {
            ProductListVO productListVO = assembleProductListVo(productItem);
            productListVOList.add(productListVO);
        }
        PageInfo pageResult = new PageInfo(productDOList);
        pageResult.setList(productListVOList);
        return ServiceResponse.createBySuccess(pageResult);
    }

    @Override
    public ServiceResponse getDetail(Integer productId) {
        if (productId == null) {
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        ProductDO product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServiceResponse.createByErrorByMessage("商品已下架或已被删除");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServiceResponse.createByErrorByMessage("商品已下架或已被删除");
        }
        ProductDetailVO productDetailVo = assembleProductDetailVo(product);
        return ServiceResponse.createBySuccess(productDetailVo);
    }



    private ProductDetailVO assembleProductDetailVo(ProductDO product) {
        ProductDetailVO productDetailVo = new ProductDetailVO();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        productDetailVo.setParentCategoryId(categoryMapperl.selectByPrimaryKey(product.getId()).getParentId());
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        return productDetailVo;
    }

    private ProductListVO assembleProductListVo(ProductDO product){
        ProductListVO productListVo = new ProductListVO();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }
}
