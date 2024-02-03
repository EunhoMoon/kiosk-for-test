package study.janek.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @Test
    @DisplayName("상품 분류가 재고 관련 분류인지 여부 확인")
    void containsStockType() {
        //given
        ProductType handmade = ProductType.HANDMADE;
        ProductType bakery = ProductType.BAKERY;

        // when
        boolean expectFalse = ProductType.containsStockType(handmade);
        boolean expectTrue = ProductType.containsStockType(bakery);

        // then
        assertThat(expectFalse).isFalse();
        assertThat(expectTrue).isTrue();
    }

}