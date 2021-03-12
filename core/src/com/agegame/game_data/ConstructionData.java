package com.agegame.game_data;

import com.agegame.utils.GameEra;

public class ConstructionData {
    public float constructionTime;
    public String construction;
    public GameEra gameEra;
    public int price;

    public ConstructionData() {};

    public ConstructionData(GameEra gameEra, float constructionTime, String construction, int price) {
        this.gameEra = gameEra;
        this.constructionTime = constructionTime;
        this.construction = construction;
        this.price = price;
    }
};
