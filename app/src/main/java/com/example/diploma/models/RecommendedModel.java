package com.example.diploma.models;

public class RecommendedModel {
    String description;
    String img_url;
    String name;
    String type;
    String rating;

    public RecommendedModel() {
    }

    public RecommendedModel(String description, String img_url, String name, String type, String rating) {
        this.description = description;
        this.img_url = img_url;
        this.name = name;
        this.type = type;
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
