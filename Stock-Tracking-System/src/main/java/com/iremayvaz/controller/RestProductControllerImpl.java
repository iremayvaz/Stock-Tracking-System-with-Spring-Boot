package com.iremayvaz.controller;

import com.iremayvaz.model.dto.DtoProduct;
import com.iremayvaz.model.dto.DtoProductDetail;
import com.iremayvaz.model.dto.DtoProductIU;
import com.iremayvaz.services.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class RestProductControllerImpl {

    private final ProductServiceImpl productService;

    
    @PreAuthorize( "hasAuthority('PRODUCT_ADD') and hasAnyRole('SECRETARY', 'EMPLOYEE', 'BOSS', 'CONSULTANT', 'AUTHORIZED', 'ACCOUNTANT')" )
    @PostMapping("/add")
    public ResponseEntity<DtoProduct> addProduct(@RequestBody @Valid DtoProductIU addProductRequest) {
        var newProduct = productService.addProduct(addProductRequest);
        return ResponseEntity.created(URI.create("/products/" + newProduct.getBarcode())).body(newProduct);
    }

    
    @PreAuthorize( "hasAuthority('PRODUCT_UPDATE') and hasAnyRole('SECRETARY', 'EMPLOYEE', 'BOSS', 'CONSULTANT', 'AUTHORIZED')" )
    @PutMapping("/update/{id}")
    public DtoProduct updateProductInfos(@PathVariable @NotNull Long id,
                                         @RequestBody @Valid DtoProductIU updateProductRequest) {
        return productService.updateProductInfos(id, updateProductRequest);
    }

    
    @PreAuthorize( "hasAuthority('PRODUCT_DELETE') and hasAnyRole('SECRETARY', 'BOSS', 'AUTHORIZED', 'ACCOUNTANT')" )
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable(value = "id") @NotNull Long id) {
        productService.deleteProduct(id);
    }

    
    @PreAuthorize( "hasAuthority('PRODUCT_LIST') and hasAnyRole('EMPLOYEE', 'ACCOUNTANT', 'SECRETARY', 'BOSS', 'CONSULTANT', 'VISITOR', 'AUTHORIZED')" )
    @GetMapping("/filter")
    public List<DtoProduct> filterProduct(@RequestParam(required = false) String column,
                                          @RequestParam(required = false) String content) {
        return productService.filterProduct(column, content);
    }

    @PreAuthorize( "hasAuthority('PRODUCT_LIST') and hasAnyRole('EMPLOYEE', 'ACCOUNTANT', 'SECRETARY', 'BOSS', 'CONSULTANT', 'VISITOR', 'AUTHORIZED')" )
    @GetMapping("/{id}")
    public DtoProductDetail getProductInfo(@PathVariable(value = "id") @NotNull Long id) {
        return productService.getProductInfo(id);
    }
}
