package edu.school21.models;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

import java.util.Objects;
import java.util.StringJoiner;

@OrmEntity(table = "cars")
public class Car {

    @OrmColumnId
    private Long id;

    @OrmColumn(name = "model", length = 10)
    private String model;

    @OrmColumn(name = "horse_power")
    private Double horsePower;

    @OrmColumn(name = "second_hand")
    private Boolean secondHand;

    public Car() {
    }

    public Car(Long id, String model, Double horsePower, Boolean secondHand) {
        this.id = id;
        this.model = model;
        this.horsePower = horsePower;
        this.secondHand = secondHand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(Double horsePower) {
        this.horsePower = horsePower;
    }

    public Boolean getSecondHand() {
        return secondHand;
    }

    public void setSecondHand(Boolean secondHand) {
        this.secondHand = secondHand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(model, car.model) && Objects.equals(horsePower, car.horsePower) && Objects.equals(secondHand, car.secondHand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, horsePower, secondHand);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("model='" + model + "'")
                .add("horsePower=" + horsePower)
                .add("secondHand=" + secondHand)
                .toString();
    }
}
