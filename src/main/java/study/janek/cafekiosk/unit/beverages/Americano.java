package study.janek.cafekiosk.unit.beverages;

public class Americano implements Beverage {

    @Override
    public String getName() {
        return "Americano";
    }

    @Override
    public int getPrice() {
        return 3500;
    }

}
