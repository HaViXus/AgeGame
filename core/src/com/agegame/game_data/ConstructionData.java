package com.agegame.game_data;

public class ConstructionData {
    public float constructionTime;
    public String name;
    public int price;

    public ConstructionData() {};

    public ConstructionData(float constructionTime, String name, int price) {
        this.constructionTime = constructionTime;
        this.name = name;
        this.price = price;
    }

    public ConstructionData(String name, int price) {
        this.name = name;
        this.price = price;
    }
};
