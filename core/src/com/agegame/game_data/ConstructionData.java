package com.agegame.game_data;

import com.agegame.utils.GameEra;

public class ConstructionData {
    public float constructionTime;
    public String construction;
    public int price;

    public ConstructionData() {};

    public ConstructionData(float constructionTime, String construction, int price) {
        this.constructionTime = constructionTime;
        this.construction = construction;
        this.price = price;
    }

    public ConstructionData(String construction, int price) {
        this.construction = construction;
        this.price = price;
    }
};
