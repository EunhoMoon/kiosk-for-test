package study.janek.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    @ParameterizedTest
    @DisplayName("상품 분류가 재고 관련 분류인지 여부 확인")
    @CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"})
    void containsStockTypeWithParamCsv(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // expect
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideProductTypesForCheckingStockType() {
        return Stream.of(
            Arguments.of(ProductType.HANDMADE, false),
            Arguments.of(ProductType.BOTTLE, true),
            Arguments.of(ProductType.BAKERY, true)
        );
    }

    @ParameterizedTest(name = "[{0}]: {1}")
    @DisplayName("상품 분류가 재고 관련 분류인지 여부 확인")
    @MethodSource("provideProductTypesForCheckingStockType")
    void containsStockTypeWithParamMethod(ProductType productType, boolean expected) {
        // when
        boolean result = ProductType.containsStockType(productType);

        // expect
        assertThat(result).isEqualTo(expected);
    }

}