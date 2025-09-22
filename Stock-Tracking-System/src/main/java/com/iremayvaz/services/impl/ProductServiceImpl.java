package com.iremayvaz.services.impl;

import com.iremayvaz.model.dto.DtoProduct;
import com.iremayvaz.model.dto.DtoProductUpdate;
import com.iremayvaz.model.dto.DtoProductInsert;
import com.iremayvaz.model.entity.Product;
import com.iremayvaz.repository.ProductRepository;
import com.iremayvaz.repository.specifications.ProductSpecifications;
import com.iremayvaz.services.ProductService;
import org.springframework.transaction.annotation.Transactional;
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
    public DtoProduct addProduct(DtoProductInsert addProductRequest) {
        DtoProduct dto = new DtoProduct();
        Product product = new Product();

        product.setProductName(addProductRequest.getProductName());
        product.setCategory(addProductRequest.getCategory());
        product.setBarcode(addProductRequest.getBarcode());
        product.setColor(addProductRequest.getColor());
        product.setExplanation(addProductRequest.getExplanation());
        product.setPrice(addProductRequest.getPrice());
        product.setStockQuantity(addProductRequest.getStockQuantity());
        product.setSize(addProductRequest.getSize());

        Product savedProduct = productRepository.save(product);
        BeanUtils.copyProperties(savedProduct, dto);

        return dto;
    }

    @Override
    public DtoProduct updateProductInfos(Long id, DtoProductInsert updateProductRequest) {
        DtoProduct dto = new DtoProduct();

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Girilen barkoda ait ürün kaydı bulunamadı!"));

        if(!product.getBarcode().equals(updateProductRequest.getBarcode())){ product.setBarcode(updateProductRequest.getBarcode());}
        if(!product.getProductName().equals(updateProductRequest.getProductName())){ product.setProductName(updateProductRequest.getProductName());}
        if(!product.getCategory().equals(updateProductRequest.getCategory())){ product.setCategory(updateProductRequest.getCategory());}
        if(!product.getPrice().equals(updateProductRequest.getPrice())){ product.setPrice(updateProductRequest.getPrice());}
        if(!product.getStockQuantity().equals(updateProductRequest.getStockQuantity())){ product.setStockQuantity(updateProductRequest.getStockQuantity());}

        product.setSize(updateProductRequest.getSize());
        product.setColor(updateProductRequest.getColor());
        product.setExplanation(updateProductRequest.getExplanation());

        Product savedProduct = productRepository.save(product);
        BeanUtils.copyProperties(savedProduct, dto);
        return dto;
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        if(optional.isPresent()){
            productRepository.delete(optional.get());
        } else {
            throw new IllegalArgumentException("Yanlış barkod girdiniz veya böyle bir ürün kaydı yok!");
        }
    }

    @Transactional(readOnly=true)
    @Override
    public List<DtoProduct> filterProduct(String column, String content) {
        List<DtoProduct> dtoProducts = new ArrayList<>();

        var spec  = ProductSpecifications.filterByColumn(column, content);
        List<Product> filteredProducts = productRepository.findAll(spec);

        for(Product p : filteredProducts){
            DtoProduct dto = new DtoProduct();
            BeanUtils.copyProperties(p, dto);
            dtoProducts.add(dto);
        }
        return dtoProducts;
    }

    @Transactional(readOnly=true)
    @Override
    public DtoProductUpdate getProductInfo(Long id) { // update'i otomatik doldurmak için yazdım. değiştirilecek kısım silinip değiştirilebilir.
        DtoProductUpdate dto = new DtoProductUpdate();
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isPresent()){
            BeanUtils.copyProperties(optional.get(), dto);
            return dto;
        } else {
            throw new IllegalArgumentException("Id ile kayıtlı kullanıcı yok!");
        }
    }
}
