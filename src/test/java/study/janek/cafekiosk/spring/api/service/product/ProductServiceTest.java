package study.janek.cafekiosk.spring.api.service.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import study.janek.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import study.janek.cafekiosk.spring.api.service.product.response.ProductResponse;
import study.janek.cafekiosk.spring.domain.product.Product;
import study.janek.cafekiosk.spring.domain.product.ProductRepository;
import study.janek.cafekiosk.spring.domain.product.ProductSellingStatus;
import study.janek.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static study.janek.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static study.janek.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("신규 상품을 등록할 때 상품 번호는 가장 최근 상품의 상품 번호에서 1 증가한 값이다.")
    void createProduct() {
        //given
        Product product = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.save(product);

        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("카푸치노")
            .price(5000)
            .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);

        // then
        assertThat(productResponse)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains("002", HANDMADE, SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .containsExactlyInAnyOrder(
                tuple("001", HANDMADE, SELLING, "아메리카노", 4000),
                tuple("002", HANDMADE, SELLING, "카푸치노", 5000)
            );
    }

    @Test
    @DisplayName("신규 상품을 등록할 때 상품 번호가 없는 경우 상품 번호는 001이다.")
    void createProductWhenProductIsEmpty() {
        //given
        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("카푸치노")
            .price(5000)
            .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);

        // then
        assertThat(productResponse)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains("001", HANDMADE, SELLING, "카푸치노", 5000);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .containsExactlyInAnyOrder(
                tuple("001", HANDMADE, SELLING, "카푸치노", 5000)
            );

    }

    private static Product createProduct(
        String productNumber,
        ProductType type,
        ProductSellingStatus status,
        String name,
        int price
    ) {
        return Product.builder()
            .productNumber(productNumber)
            .type(type)
            .sellingStatus(status)
            .name(name)
            .price(price)
            .build();
    }

}