package study.janek.cafekiosk.unit.beverages;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AmericanoTest {

    @Test
    @DisplayName("아메리카노 이름을 반환한다.")
    void getName() {
        Americano americano = new Americano();

        assertEquals(americano.getName(), "Americano");
        assertThat(americano.getName()).isEqualTo("Americano");
    }

    @Test
    @DisplayName("아메리카노 가격을 반환한다.")
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(3_500);
    }

}