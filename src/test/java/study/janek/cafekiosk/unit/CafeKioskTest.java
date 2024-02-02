package study.janek.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.janek.cafekiosk.unit.beverages.Americano;
import study.janek.cafekiosk.unit.beverages.Latte;
import study.janek.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafeKioskTest {

    @Test
    @DisplayName("수동 테스트, 생성 내역 정상 출력.")
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> beverage count: " + cafeKiosk.getBeverages().size());
        System.out.println(">>> beverage name: " + cafeKiosk.getBeverages().get(0).getName());
    }

    @Test
    @DisplayName("음료 한 개를 추가하면 주문 목록에 담긴다.")
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("Americano");
    }

    @Test
    @DisplayName("음료 여러 개 추가하면 주문 목록에 담긴다.")
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);

        assertThat(cafeKiosk.getBeverages()).hasSize(2);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("Americano");
    }

    @Test
    @DisplayName("음료 주문시 수량을 0으로 입력하면 주문을 생성할 수 없다.")
    void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("음료는 한 잔 이상 주문하실 수 있습니다.");
    }

    @Test
    @DisplayName("주문 내역 삭제시 목록에서 삭제된다.")
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    @DisplayName("장바구니 비우기 동작시 모든 주문 목록이 사라진다.")
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        cafeKiosk.clear();

        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    @DisplayName("주문 목록에 담긴 음료의 총 금액이 반환된다.")
    void calculateTotalPrice() {
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        // when
        int totalPrice = cafeKiosk.calculateTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(americano.getPrice() + latte.getPrice());
    }

    @Test
    @DisplayName("영업 시간 내에 주문하면 정상 주문된다.")
    void createOrderWithOperatingHours() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 28, 14, 0));
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("Americano");
    }

    @Test
    @DisplayName("영업 시간을 지나 주문하면 주문을 생성할 수 없다.")
    void createOrderWithoutOperatingHours() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2023, 1, 28, 23, 0)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }

}