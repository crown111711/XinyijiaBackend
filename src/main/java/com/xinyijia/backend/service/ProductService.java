package com.xinyijia.backend.service;

import com.xinyijia.backend.domain.BusinessInfo;
import com.xinyijia.backend.domain.ProductInfo;
import com.xinyijia.backend.param.ProductResponse;
import com.xinyijia.backend.param.request.TradeRequest;
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

    int addBusiness(BusinessInfo businessInfo);

    void updateBusiness(BusinessInfo businessInfo);

    List<ProductResponse> searchProducts(String searchIndex,String searchBusiness);

    List<ProductResponse> getProductInBusiness(String businessName,Integer businessId,Integer productId);

    List<ProductResponse> recommendProducts(String accessToken);

    List<ProductResponse> getAllProducts();

    void deleteProduct(int id);

    void updateProduct(ProductInfo productInfo);

    int deleteBusiness(int id);

}
