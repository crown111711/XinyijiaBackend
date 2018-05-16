package com.xinyijia.backend.service;

import com.xinyijia.backend.domain.BusinessInfo;
import com.xinyijia.backend.domain.ProductInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanjia
 * @email 378097217@qq.com
 * @date 2018/5/16 21:12
 */
@Service
public interface ProductService {
    void addProduct(ProductInfo productInfo);
    List<BusinessInfo> getAllBusiness();
    void addBusiness(BusinessInfo businessInfo);
    void updateBusiness(BusinessInfo businessInfo);
}
