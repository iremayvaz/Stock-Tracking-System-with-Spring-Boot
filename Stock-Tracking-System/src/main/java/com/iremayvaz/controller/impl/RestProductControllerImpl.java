package com.iremayvaz.controller.impl;

import com.iremayvaz.controller.RestProductController;
import com.iremayvaz.model.dto.DtoProduct;
import com.iremayvaz.model.dto.DtoProductIU;
import com.iremayvaz.model.entity.Product;
import com.iremayvaz.services.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class RestProductControllerImpl implements RestProductController {

    private final ProductServiceImpl productService;

    @Override
    @PostMapping("/add")
    public DtoProduct addProduct(DtoProductIU addProductRequest) {
        return productService.addProduct(addProductRequest);
    }

    @Override
    @PostMapping("/update")
    public DtoProduct updateProductInfos(DtoProductIU updateProductRequest) {
        return productService.updateProductInfos(updateProductRequest);
    }

    @Override
    @DeleteMapping("/delete")
    public void deleteProduct(String barcode) {
        productService.deleteProduct(barcode);
    }

    @Override
    @PutMapping("/filter")
    public List<DtoProduct> filterProduct(String column, String content) {
        return productService.filterProduct(column, content);
    }
}
