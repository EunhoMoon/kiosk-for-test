package study.janek.cafekiosk.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

    @Test
    @DisplayName("재고의 수량이 주문 수량보다 적은지 확인")
    void isQuantityLessThen() {
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // when
        boolean result = stock.isQuantityLessThen(quantity);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("주어진 수 만큼 재고 수를 차감할 수 있다.")
    void deductQuantity() {
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        // when
        stock.deductQuantity(quantity);

        // then
        assertThat(stock.getQuantity()).isZero();
    }

    @Test
    @DisplayName("재고 수 보다 많은 수를 차감할 경우 예외 발생")
    void deductQuantityOver() {
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // when
        // then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("재고 보다 많이 주문할 수 없습니다.");
    }

    // Dynamic Test
    @TestFactory
    @DisplayName("재고 차감 시나리오")
    Collection<DynamicTest> Test() {
        Stock stock = Stock.create("001", 1);

        return List.of(
            DynamicTest.dynamicTest("재고를 주어진 개수만큼 차감할 수 있다.", () -> {
                // given
                int quantity = 1;

                // when
                stock.deductQuantity(quantity);

                // then
                assertThat(stock.getQuantity()).isZero();
            }),
            DynamicTest.dynamicTest("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.", () -> {
                // given
                int quantity = 1;

                // expect
                assertThatThrownBy(() -> stock.deductQuantity(quantity))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("재고 보다 많이 주문할 수 없습니다.");
            })
        );
    }

}