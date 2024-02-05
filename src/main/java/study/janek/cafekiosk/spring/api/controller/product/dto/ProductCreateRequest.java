package study.janek.cafekiosk.spring.api.controller.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.janek.cafekiosk.spring.domain.product.Product;
import study.janek.cafekiosk.spring.domain.product.ProductSellingStatus;
import study.janek.cafekiosk.spring.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    private ProductType type;

    private ProductSellingStatus sellingStatus;

    private String name;

    private int price;

    @Builder
    private ProductCreateRequest(ProductType type, ProductSellingStatus status, String name, int price) {
        this.type = type;
        this.sellingStatus = status;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
            .productNumber(nextProductNumber)
            .type(this.type)
            .sellingStatus(this.sellingStatus)
            .name(this.name)
            .price(this.getPrice())
            .build();
    }

}
