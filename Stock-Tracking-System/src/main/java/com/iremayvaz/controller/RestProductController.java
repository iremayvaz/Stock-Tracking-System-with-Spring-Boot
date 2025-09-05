package com.iremayvaz.controller;

import com.iremayvaz.model.dto.DtoProduct;
import com.iremayvaz.model.dto.DtoProductIU;
import com.iremayvaz.model.entity.Product;

import java.util.List;

public interface RestProductController {

    public DtoProduct addProduct(DtoProductIU addProductRequest);

    public DtoProduct updateProductInfos(DtoProductIU updateProductRequest);

    public void deleteProduct(String barcode);

    public List<DtoProduct> filterProduct(String column, String content);
}
