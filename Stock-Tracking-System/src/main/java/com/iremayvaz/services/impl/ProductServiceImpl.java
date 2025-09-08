package com.iremayvaz.services.impl;

import com.iremayvaz.model.dto.DtoProduct;
import com.iremayvaz.model.dto.DtoProductIU;
import com.iremayvaz.model.entity.Product;
import com.iremayvaz.repository.ProductRepository;
import com.iremayvaz.repository.specifications.ProductSpecifications;
import com.iremayvaz.services.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Transactional
    @Override
    public DtoProduct addProduct(DtoProductIU addProductRequest) {
        DtoProduct dto = new DtoProduct();
        Product product = new Product();

        product.setProductName(addProductRequest.getProductName());
        product.setCategory(addProductRequest.getCategory());
        product.setBarcode(addProductRequest.getBarcode());
        product.setColor(addProductRequest.getColor());
        product.setExplanation(addProductRequest.getExplanation());
        product.setPrice(addProductRequest.getPrice());
        product.setNumber(addProductRequest.getNumber());
        product.setSize(addProductRequest.getSize());

        Product savedProduct = productRepository.save(product);
        BeanUtils.copyProperties(savedProduct, dto);

        return dto;
    }

    @Transactional
    @Override
    public DtoProduct updateProductInfos(DtoProductIU updateProductRequest) {
        DtoProduct dto = new DtoProduct();

        Product product = productRepository.findByBarcode(updateProductRequest.getBarcode())
                .orElseThrow(() -> new IllegalArgumentException(""));

        if(product.getProductName().equals(updateProductRequest.getProductName())){ product.setProductName(updateProductRequest.getProductName());}
        if(product.getCategory().equals(updateProductRequest.getCategory())){ product.setCategory(updateProductRequest.getCategory());}
        if(product.getColor().equals(updateProductRequest.getColor())){ product.setColor(updateProductRequest.getColor());}
        if(product.getExplanation().equals(updateProductRequest.getExplanation())){ product.setExplanation(updateProductRequest.getExplanation());}
        if(product.getPrice().equals(updateProductRequest.getPrice())){ product.setPrice(updateProductRequest.getPrice());}
        if(product.getNumber().equals(updateProductRequest.getNumber())){ product.setNumber(updateProductRequest.getNumber());}
        if(product.getSize().equals(updateProductRequest.getSize())){ product.setSize(updateProductRequest.getSize());}

        Product savedProduct = productRepository.save(product);

        BeanUtils.copyProperties(savedProduct, dto);
        return dto;
    }

    @Override
    public void deleteProduct(String barcode) {
        Optional<Product> optional = productRepository.findByBarcode(barcode);
        if(optional.isPresent()){
            productRepository.delete(optional.get());
        } else {
            throw new IllegalArgumentException("Yanlış barkod girdiniz veya böyle bir ürün kaydı yok!");
        }
    }

    @Override
    public List<DtoProduct> filterProduct(String column, String content) {
        DtoProduct dto = new DtoProduct();
        List<DtoProduct> dtoProducts = new ArrayList<>();

        var spec  = ProductSpecifications.filterByColumn(column, content);
        List<Product> filteredProducts = productRepository.findAll(spec);

        for(Product p : filteredProducts){
            BeanUtils.copyProperties(p, dto);
            dtoProducts.add(dto);
        }
        return dtoProducts;
    }
}
