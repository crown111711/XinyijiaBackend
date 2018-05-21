package com.xinyijia.backend.service.serviceImpl;

import com.google.common.collect.Lists;
import com.xinyijia.backend.common.BaseConsant;
import com.xinyijia.backend.domain.*;
import com.xinyijia.backend.mapper.BusinessInfoMapper;
import com.xinyijia.backend.mapper.ProductInfoMapper;
import com.xinyijia.backend.mapper.UserInfoMapper;
import com.xinyijia.backend.param.ProductResponse;
import com.xinyijia.backend.param.TokenCache;
import com.xinyijia.backend.service.ProductService;
import com.xinyijia.backend.service.TokenCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/16 21:12
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Autowired
    BusinessInfoMapper businessInfoMapper;

    @Autowired
    TokenCacheService tokenCacheService;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public void addProduct(ProductInfo productInfo) {
        productInfo.setCreateTime(System.currentTimeMillis());
        productInfoMapper.insert(productInfo);
    }

    @Override
    public List<BusinessInfo> getAllBusiness() {
        List<BusinessInfo> businessInfos = businessInfoMapper.selectByExample(new BusinessInfoExample());
        return businessInfos;
    }

    @Override
    public int addBusiness(BusinessInfo businessInfo) {
        businessInfoMapper.insert(businessInfo);
        return businessInfo.getId();
    }

    @Override
    public void updateBusiness(BusinessInfo businessInfo) {
        if (businessInfo.getId() == null) {
            BusinessInfoExample query = new BusinessInfoExample();
            query.createCriteria().andBusinessNameEqualTo(businessInfo.getBusinessName());
            businessInfoMapper.updateByExample(businessInfo, query);
        } else {
            businessInfoMapper.updateByPrimaryKey(businessInfo);
        }
    }

    @Override
    public List<ProductResponse> searchProducts(String searchIndex, String searchBusiness) {
        ProductInfoExample productInfoExample = new ProductInfoExample();
        boolean businessSure = false;
        ProductInfoExample.Criteria criteria = null;
        if (StringUtils.isNotBlank(searchBusiness) && !Objects.equals("全部商品", searchBusiness)) {
            BusinessInfoExample query = new BusinessInfoExample();
            query.createCriteria().andBusinessNameEqualTo(searchBusiness);
            List<BusinessInfo> businessInfos = businessInfoMapper.selectByExample(query);
            if (CollectionUtils.isEmpty(businessInfos)) {
                return Lists.newArrayList();
            }
            int bzId = businessInfos.get(0).getId();
            criteria = productInfoExample.createCriteria().andBusinessIdEqualTo(bzId);
            businessSure = true;
        }
        //对业务线进行精确查询 相关判断
        if (!businessSure) {
            try {
                Integer businessId = Integer.parseInt(searchIndex);
                productInfoExample.or().andBusinessIdEqualTo(businessId);
            } catch (Exception e) {
                log.info("将查询条件转化成businessId失败");
            }
            productInfoExample.or().andProductNameLike("%" + searchIndex + "%");
            productInfoExample.or().andCategoryLike("%" + searchIndex + "%");
        } else {
            criteria.andProductNameLike("%" + searchIndex + "%");
        }

        List<ProductInfo> productInfos = productInfoMapper.selectByExample(productInfoExample);
        if (CollectionUtils.isEmpty(productInfos))
            return Lists.newArrayList();

        return convertProduct(productInfos);
    }

    @Override
    public List<ProductResponse> getProductInBusiness(String businessName, Integer businessId, Integer productId) {
        if (productId != null && productId != 0) {
            return convertProduct(Lists.newArrayList(productInfoMapper.selectByPrimaryKey(productId)));
        }
        if (StringUtils.isNotBlank(businessName)) {
            if (Objects.equals("全部商品", businessName)) {
                return getAllProducts();
            }
            BusinessInfoExample query = new BusinessInfoExample();
            query.createCriteria().andBusinessNameEqualTo(businessName);
            List<BusinessInfo> businessInfos = businessInfoMapper.selectByExample(query);
            if (CollectionUtils.isEmpty(businessInfos)) {
                return null;
            }
            businessId = businessInfos.get(0).getId();
        }
        ProductInfoExample productInfoExample = new ProductInfoExample();
        productInfoExample.createCriteria().andBusinessIdEqualTo(businessId);
        return convertProduct(productInfoMapper.selectByExample(productInfoExample));
    }

    @Override
    public List<ProductResponse> recommendProducts(String accessToken) {
        TokenCache tokenCache = tokenCacheService.getCache(accessToken);
        List<ProductInfo> productInfos = Lists.newArrayList();
        if (tokenCache != null) {
            Integer uid = tokenCache.getUid();
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(uid);
            if (StringUtils.isNotBlank(userInfo.getViewProducts())) {
                String[] productIds = userInfo.getViewProducts().split(":");
                for (String productId : productIds) {
                    productInfos.add(productInfoMapper.selectByPrimaryKey(Integer.parseInt(productId)));
                }
            }
        }
        if (productInfos.size() < 10) {
            List<ProductInfo> views = productInfoMapper.selectByExample(new ProductInfoExample());
            if (!CollectionUtils.isEmpty(views)) {


                int lenght = 10 - productInfos.size();
                if (views.size() < lenght) {
                    lenght = views.size();
                }
                for (int i = 0; i < lenght; i++) {
                    productInfos.add(views.get(i));
                }
            }
        }
        return convertProduct(productInfos);
    }

    private List<ProductResponse> convertProduct(List<ProductInfo> productInfos) {
        if (CollectionUtils.isEmpty(productInfos)) {
            return null;
        }
        List<ProductResponse> productResponses = productInfos.stream().map(
                p -> {
                    ProductResponse response = new ProductResponse();
                    BeanUtils.copyProperties(p, response);
                    if (StringUtils.isNotBlank(p.getImageName())) {
                        response.setImageUrl(BaseConsant.SHOW_IMAGE + p.getImageName());
                    }
                    if (p.getBusinessId() != null) {
                        response.setBusinessName(businessInfoMapper.selectByPrimaryKey(p.getBusinessId()).getBusinessName());
                    }
                    if (p.getSelling() == 1) {
                        response.setSelling(true);
                    }
                    return response;
                }
        ).collect(Collectors.toList());
        return productResponses;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return convertProduct(productInfoMapper.selectByExample(new ProductInfoExample()));
    }

    @Override
    public void deleteProduct(int id) {
        productInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateProduct(ProductInfo productInfo) {
        productInfoMapper.updateByPrimaryKey(productInfo);
    }

    @Override
    public int deleteBusiness(int id) {
        //做一个外键关联校验 若在公司，这里 1.不能删
        ProductInfoExample query = new ProductInfoExample();
        query.createCriteria().andBusinessIdEqualTo(id);
        List<ProductInfo> products = productInfoMapper.selectByExample(query);
        if (!CollectionUtils.isEmpty(products)) {
            return 1;
        }
        businessInfoMapper.deleteByPrimaryKey(id);
        return 0;
    }
}
