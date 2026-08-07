package com.nexerp.modules.finance.service.implementation;

import com.nexerp.common.PagedResponse;
import com.nexerp.common.ResourceNotFoundException;
import com.nexerp.modules.auth.service.interfaces.AuthService;
import com.nexerp.modules.finance.dto.request.ProductRequestDto;
import com.nexerp.modules.finance.dto.response.ProductResponseDto;
import com.nexerp.modules.finance.mapper.ProductMapper;
import com.nexerp.modules.finance.repository.interfaces.IProductRepository;
import com.nexerp.modules.finance.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final IProductRepository repository;
    private final ProductMapper     productMapper;
    private final AuthService       auth;

    private Integer cid() { return auth.currentPrincipal().getCompanyId(); }

    @Override
    public PagedResponse<ProductResponseDto> listProducts(int page, int size, String search) {
        log.info("listProducts START | page={}, size={}", page, size);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Page",      page + 1);
            params.put("Size",      size);
            params.put("Search",    productMapper.nvl(search));
            params.put("CompanyId", cid());
            List<Map<String, Object>> rows = repository.getProducts(params);
            long total = productMapper.firstLong(rows, "TotalCount", rows.size());
            return PagedResponse.of(rows.stream().map(productMapper::toProduct).collect(Collectors.toList()), page, size, total);
        } catch (Exception e) { log.error("listProducts | Exception occurred", e); throw e; }
    }

    @Override
    public List<ProductResponseDto> searchProducts(String q) {
        log.info("searchProducts START | q={}", q);
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Query",     productMapper.nvl(q));
            params.put("CompanyId", cid());
            return repository.searchProducts(params)
                    .stream().map(productMapper::toProduct).collect(Collectors.toList());
        } catch (Exception e) { log.error("searchProducts | Exception occurred", e); throw e; }
    }

    @Override
    public ProductResponseDto getProduct(Integer id) {
        log.info("getProduct START | productId={}", id);
        try {
            Map<String, Object> r = repository.getProductById(id, cid());
            if (r == null) throw new ResourceNotFoundException("Product", id);
            return productMapper.toProduct(r);
        } catch (ResourceNotFoundException e) { throw e; }
        catch (Exception e) { log.error("getProduct | Exception occurred | productId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductResponseDto createProduct(ProductRequestDto req) {
        log.info("createProduct START | productName={}", req.getProductName());
        try {
            Map<String, Object> p = productMapper.toProductParams(req);
            p.put("CompanyId", cid());
            Integer id = repository.insertProduct(p);
            return getProduct(id);
        } catch (Exception e) { log.error("createProduct | Exception occurred", e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductResponseDto updateProduct(Integer id, ProductRequestDto req) {
        log.info("updateProduct START | productId={}", id);
        try {
            Map<String, Object> p = productMapper.toProductParams(req);
            p.put("ProductId", id);
            p.put("CompanyId", cid());
            repository.updateProduct(p);
            return getProduct(id);
        } catch (Exception e) { log.error("updateProduct | Exception occurred | productId={}", id, e); throw e; }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(Integer id) {
        log.info("deleteProduct START | productId={}", id);
        try {
            repository.deleteProduct(id, cid());
            log.info("deleteProduct END | productId={}", id);
        } catch (Exception e) { log.error("deleteProduct | Exception occurred | productId={}", id, e); throw e; }
    }
}
