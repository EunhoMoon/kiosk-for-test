package study.janek.cafekiosk.unit;

import org.junit.jupiter.api.Test;
import study.janek.cafekiosk.unit.beverages.Americano;

import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> beverage count: " + cafeKiosk.getBeverages().size());
        System.out.println(">>> beverage name: " + cafeKiosk.getBeverages().get(0).getName());
    }

}