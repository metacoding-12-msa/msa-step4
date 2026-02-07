package com.metacoding.product.products;

import com.metacoding.product.core.handler.ex.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse findById(int productId) {
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new Exception404("상품이 없습니다."));
        return ProductResponse.from(findProduct);
    }

    @Transactional
    public ProductResponse decreaseQuantity(int productId, int quantity, Long price) {
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new Exception404("상품이 없습니다."));
        findProduct.decreaseQuantity(quantity);
        return ProductResponse.from(findProduct);
    }

    @Transactional
    public ProductResponse increaseQuantity(int productId, int quantity, Long price) {
        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new Exception404("상품이 없습니다."));
        findProduct.increaseQuantity(quantity);
        return ProductResponse.from(findProduct);
    }
}