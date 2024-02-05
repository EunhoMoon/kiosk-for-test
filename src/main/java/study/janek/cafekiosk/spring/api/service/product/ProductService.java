package study.janek.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.janek.cafekiosk.spring.api.controller.product.dto.ProductCreateRequest;
import study.janek.cafekiosk.spring.api.service.product.response.ProductResponse;
import study.janek.cafekiosk.spring.domain.product.Product;
import study.janek.cafekiosk.spring.domain.product.ProductRepository;
import study.janek.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProduct() {
        return productRepository
            .findAllBySellingStatusIn(ProductSellingStatus.forDisplay())
            .stream()
            .map(ProductResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product newProduct = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(newProduct);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();

        if (latestProductNumber == null) return "001";

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }

}
