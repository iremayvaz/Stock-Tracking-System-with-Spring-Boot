package com.iremayvaz.services;

import com.iremayvaz.model.dto.DtoProduct;
import com.iremayvaz.model.dto.DtoProductDetail;
import com.iremayvaz.model.dto.DtoProductIU;

import java.util.List;

public interface ProductService {

    public DtoProduct addProduct(DtoProductIU addProductRequest);

    public DtoProduct updateProductInfos(Long id, DtoProductIU updateProductRequest);

    public void deleteProduct(Long id);

    public List<DtoProduct> filterProduct(String column, String content);

    public DtoProductDetail getProductInfo(Long id) ;
}
