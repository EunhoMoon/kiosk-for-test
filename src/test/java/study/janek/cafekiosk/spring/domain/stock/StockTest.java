package study.janek.cafekiosk.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}