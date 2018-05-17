package com.xinyijia.backend.service.serviceImpl;

import com.xinyijia.backend.domain.BusinessInfo;
import com.xinyijia.backend.domain.BusinessInfoExample;
import com.xinyijia.backend.domain.ProductInfo;
import com.xinyijia.backend.domain.ProductInfoExample;
import com.xinyijia.backend.mapper.BusinessInfoMapper;
import com.xinyijia.backend.mapper.ProductInfoMapper;
import com.xinyijia.backend.param.ProductResponse;
import com.xinyijia.backend.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
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
    public List<ProductResponse> searchProducts(String searchIndex) {
        ProductInfoExample productInfoExample = new ProductInfoExample();
        try {
            Integer businessId = Integer.parseInt(searchIndex);
            productInfoExample.or().andBusinessIdEqualTo(businessId);
        } catch (Exception e) {
            log.info("将查询条件转化成businessId失败");
        }
        productInfoExample.or().andProductNameLike("%" + searchIndex + "%");
        productInfoExample.or().andCategoryLike("%" + searchIndex + "%");
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(productInfoExample);
        if (CollectionUtils.isEmpty(productInfos))
            return null;

        return convertProduct(productInfos);
    }

    private List<ProductResponse> convertProduct(List<ProductInfo> productInfos) {
        List<ProductResponse> productResponses = productInfos.stream().map(
                p -> {
                    ProductResponse response = new ProductResponse();
                    BeanUtils.copyProperties(p, response);
                    if (StringUtils.isNotBlank(p.getImageName())) {
                        response.setImageUrl("http://localhost:8090/xyj/api/attachment/showImage/" + p.getImageName());
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
