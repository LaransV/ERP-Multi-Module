package com.nexerp.modules.finance.service.interfaces;

import com.nexerp.common.PagedResponse;
import com.nexerp.modules.finance.dto.request.ProductRequestDto;
import com.nexerp.modules.finance.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {
    PagedResponse<ProductResponseDto> listProducts(int page, int size, String search);
    List<ProductResponseDto> searchProducts(String q);
    ProductResponseDto getProduct(Integer id);
    ProductResponseDto createProduct(ProductRequestDto req);
    ProductResponseDto updateProduct(Integer id, ProductRequestDto req);
    void deleteProduct(Integer id);
}
