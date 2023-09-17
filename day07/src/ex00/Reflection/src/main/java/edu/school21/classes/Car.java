package edu.school21.classes;

import java.util.StringJoiner;

public class Car {

    private String name;

    private Integer model;

    private Double price;

    public Car() {
        name = "Default car name";
        model = 0;
        price = 0.;
    }

    public Car(String name, Integer model, Double price) {
        this.name = name;
        this.model = model;
        this.price = price;
    }

    public void riseInPrice(Double value) {
        price += value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("model=" + model)
                .add("price=" + price)
                .toString();
    }
}
