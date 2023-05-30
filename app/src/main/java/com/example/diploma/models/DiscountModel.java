package com.example.diploma.models;

public class DiscountModel {
    private String name;
    private String percent;
    private String description;

    public DiscountModel() {
    }

    public DiscountModel(String name, String percent, String description) {
        this.name = name;
        this.percent = percent;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
