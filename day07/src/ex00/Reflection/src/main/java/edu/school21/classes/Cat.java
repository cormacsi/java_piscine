package edu.school21.classes;

import java.util.StringJoiner;

public class Cat {
    private String name;

    private Integer age;

    private Double weight;

    public Cat() {
        name = "Cat";
        age = 0;
        weight = 0.;
    }

    public Cat(String name, Integer age, Double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public Double increaseWeight(Double l) {
        return weight += l;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Cat.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("model=" + age)
                .add("price=" + weight)
                .toString();
    }
}
