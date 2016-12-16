package com.example.dysaniazzz.bean;

/**
 * Created by DysaniazzZ on 15/12/2016.
 */
public class FruitBean {

    private String name;
    private int imageId;

    public FruitBean(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
