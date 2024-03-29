package study.janek.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.janek.cafekiosk.spring.domain.product.Product;
import study.janek.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static study.janek.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;

class OrderTest {

    @Test
    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    void calculateTotalPrice() {
        //given
        List<Product> products = List.of(
            createProduct("001", 1000),
            createProduct("002", 2000)
        );
        LocalDateTime registeredDateTime = LocalDateTime.of(2024, 2, 7, 21, 0, 0);

        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getTotalPrice())
            .isEqualTo(3000);
    }

    @Test
    @DisplayName("주문 생성 시 주문 상태는 INIT이다.")
    void init() {
        //given
        List<Product> products = List.of(
            createProduct("001", 1000),
            createProduct("002", 2000)
        );

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getStatus())
            .isEqualByComparingTo(OrderStatus.INIT);
    }

    @Test
    @DisplayName("주문 생성 시 등록 시간을 기록한다.")
    void registeredDateTime() {
        //given
        LocalDateTime registeredDateTime = LocalDateTime.of(2024, 2, 7, 21, 0, 0);
        List<Product> products = List.of(
            createProduct("001", 1000),
            createProduct("002", 2000)
        );

        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getRegisteredDateTime())
            .isEqualTo(registeredDateTime);
    }


    private Product createProduct(String productNumber, int price) {
        return Product.builder()
            .type(ProductType.HANDMADE)
            .productNumber(productNumber)
            .price(price)
            .sellingStatus(SELLING)
            .name("메뉴 이름")
            .build();
    }

}