package com.iremayvaz.controller;

import com.iremayvaz.model.dto.DtoProduct;
import com.iremayvaz.model.dto.DtoProductUpdate;
import com.iremayvaz.model.dto.DtoProductInsert;
import com.iremayvaz.services.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Product API", description = "Ürün işlemleri")
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class RestProductControllerImpl {

    private final ProductServiceImpl productService;

    @Operation(description = "Ürün ekleme")
    @PreAuthorize( "hasAuthority('PRODUCT_ADD') and hasAnyRole('SECRETARY', 'EMPLOYEE', 'BOSS', 'CONSULTANT', 'AUTHORIZED', 'ACCOUNTANT')" )
    @PostMapping("/add")
    public ResponseEntity<DtoProduct> addProduct(@RequestBody @Valid DtoProductInsert addProductRequest) {
        var newProduct = productService.addProduct(addProductRequest);
        return ResponseEntity.created(URI.create("/products/" + newProduct.getBarcode())).body(newProduct);
    }

    @Operation(description = "Ürün güncelleme")
    @PreAuthorize( "hasAuthority('PRODUCT_UPDATE') and hasAnyRole('SECRETARY', 'EMPLOYEE', 'BOSS', 'CONSULTANT', 'AUTHORIZED')" )
    @PutMapping("/update/{id}")
    public DtoProduct updateProductInfos(@PathVariable @NotNull Long id,
                                         @RequestBody @Valid DtoProductInsert updateProductRequest) {
        return productService.updateProductInfos(id, updateProductRequest);
    }

    @Operation(description = "Ürün silme")
    @PreAuthorize( "hasAuthority('PRODUCT_DELETE') and hasAnyRole('SECRETARY', 'BOSS', 'AUTHORIZED', 'ACCOUNTANT')" )
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable(value = "id") @NotNull Long id) {
        productService.deleteProduct(id);
    }

    @Operation(description = "Ürün filtreleme")
    @PreAuthorize( "hasAuthority('PRODUCT_LIST') and hasAnyRole('EMPLOYEE', 'ACCOUNTANT', 'SECRETARY', 'BOSS', 'CONSULTANT', 'VISITOR', 'AUTHORIZED')" )
    @GetMapping("/filter")
    public List<DtoProduct> filterProduct(@RequestParam(required = false) String column,
                                          @RequestParam(required = false) String content) {
        return productService.filterProduct(column, content);
    }

    @Operation(description = "ID ile ürün bilgisi alma")
    @PreAuthorize( "hasAuthority('PRODUCT_LIST') and hasAnyRole('EMPLOYEE', 'ACCOUNTANT', 'SECRETARY', 'BOSS', 'CONSULTANT', 'VISITOR', 'AUTHORIZED')" )
    @GetMapping("/{id}")
    public DtoProductUpdate getProductInfo(@PathVariable(value = "id") @NotNull Long id) {
        return productService.getProductInfo(id);
    }
}
