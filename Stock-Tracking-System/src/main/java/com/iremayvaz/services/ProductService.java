package com.iremayvaz.services;

import com.iremayvaz.model.dto.DtoProduct;
import com.iremayvaz.model.dto.DtoProductUpdate;
import com.iremayvaz.model.dto.DtoProductInsert;

import java.util.List;

public interface ProductService {

    public DtoProduct addProduct(DtoProductInsert addProductRequest);

    public DtoProduct updateProductInfos(Long id, DtoProductInsert updateProductRequest);

    public void deleteProduct(Long id);

    public List<DtoProduct> filterProduct(String column, String content);

    public DtoProductUpdate getProductInfo(Long id) ;
}
