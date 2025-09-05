package com.iremayvaz.controller.impl;

import com.iremayvaz.controller.RestProductController;
import com.iremayvaz.model.dto.DtoProduct;
import com.iremayvaz.model.dto.DtoProductIU;
import com.iremayvaz.model.entity.Product;
import com.iremayvaz.services.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class RestProductControllerImpl implements RestProductController {

    private final ProductServiceImpl productService;

    @Override
    @PreAuthorize("hasAuthority('PRODUCT_ADD') and (hasRole('SECRETARY') or hasRole('EMPLOYEE') or hasRole('BOSS') or" +
                                                    "hasRole('CONSULTANT') or hasRole('AUTHORIZED') or hasRole('ACCOUNTANT'))")
    @PostMapping("/add")
    public DtoProduct addProduct(DtoProductIU addProductRequest) {
        return productService.addProduct(addProductRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE') and (hasRole('SECRETARY') or hasRole('EMPLOYEE') or hasRole('BOSS') or" +
                                                        "hasRole('CONSULTANT') or hasRole('AUTHORIZED'))")
    @PostMapping("/update")
    public DtoProduct updateProductInfos(DtoProductIU updateProductRequest) {
        return productService.updateProductInfos(updateProductRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('PRODUCT_DELETE') and hasRole('SECRETARY') or hasRole('BOSS') or" +
                                                      "hasRole('AUTHORIZED')  or hasRole('ACCOUNTANT'))")
    @DeleteMapping("/delete")
    public void deleteProduct(String barcode) {
        productService.deleteProduct(barcode);
    }

    @Override
    @PreAuthorize("hasAuthority('PRODUCT_LIST') and (hasRole('EMPLOYEE') or hasRole('ACCOUNTANT') or hasRole('SECRETARY') or" +
                                                     "hasRole('BOSS') or hasRole('CONSULTANT') or hasRole('VISITOR') or hasRole('AUTHORIZED'))")
    @PutMapping("/filter")
    public List<DtoProduct> filterProduct(String column, String content) {
        return productService.filterProduct(column, content);
    }
}
