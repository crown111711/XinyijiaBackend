package com.xinyijia.backend.service.serviceImpl;

import com.xinyijia.backend.domain.BusinessInfo;
import com.xinyijia.backend.domain.BusinessInfoExample;
import com.xinyijia.backend.domain.ProductInfo;
import com.xinyijia.backend.mapper.BusinessInfoMapper;
import com.xinyijia.backend.mapper.ProductInfoMapper;
import com.xinyijia.backend.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void addBusiness(BusinessInfo businessInfo) {
        businessInfoMapper.insert(businessInfo);
    }

    @Override
    public void updateBusiness(BusinessInfo businessInfo) {
        BusinessInfoExample query = new BusinessInfoExample();
        query.createCriteria().andBusinessNameEqualTo(businessInfo.getBusinessName());
        businessInfoMapper.updateByExample(businessInfo, query);
    }
}
