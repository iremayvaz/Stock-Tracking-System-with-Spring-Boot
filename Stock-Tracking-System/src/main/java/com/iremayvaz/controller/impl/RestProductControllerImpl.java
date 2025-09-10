package com.iremayvaz.controller.impl;

import com.iremayvaz.controller.RestProductController;
import com.iremayvaz.model.dto.DtoProduct;
import com.iremayvaz.model.dto.DtoProductIU;
import com.iremayvaz.services.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Validated // ????
public class RestProductControllerImpl implements RestProductController {

    private final ProductServiceImpl productService;

    @Override
    @PreAuthorize( "hasAuthority('PRODUCT_ADD') and hasRole('SECRETARY', 'EMPLOYEE', 'BOSS', 'CONSULTANT', 'AUTHORIZED', 'ACCOUNTANT')" )
    @PostMapping("/add")
    public ResponseEntity<DtoProduct> addProduct(@RequestBody @Valid DtoProductIU addProductRequest) {
        var newProduct = productService.addProduct(addProductRequest);
        return ResponseEntity.created(URI.create("/products/" + newProduct.getBarcode())).body(newProduct);
    }

    @Override
    @PreAuthorize( "hasAuthority('PRODUCT_UPDATE') and hasRole('SECRETARY', 'EMPLOYEE', 'BOSS', 'CONSULTANT', 'AUTHORIZED')" )
    @PutMapping("/update/{barcode}")
    public DtoProduct updateProductInfos(@PathVariable String barcode,
                                         @RequestBody @Valid DtoProductIU updateProductRequest) {
        return productService.updateProductInfos(updateProductRequest);
    }

    @Override
    @PreAuthorize( "hasAuthority('PRODUCT_DELETE') and hasRole('SECRETARY', 'BOSS', 'AUTHORIZED', 'ACCOUNTANT')" )
    @DeleteMapping("/delete/{barcode}")
    public void deleteProduct(@PathVariable(value = "barcode") String barcode) {
        productService.deleteProduct(barcode);
    }

    @Override
    @PreAuthorize( "hasAuthority('PRODUCT_LIST') and hasRole('EMPLOYEE', 'ACCOUNTANT', 'SECRETARY', 'BOSS', 'CONSULTANT', 'VISITOR', 'AUTHORIZED')" )
    @GetMapping("/filter")
    public List<DtoProduct> filterProduct(@RequestParam(required = false) String column,
                                          @RequestParam(required = false) String content) {
        return productService.filterProduct(column, content);
    }
}
