package com.agegame.request;

import com.agegame.utils.GameEra;

public class ConstructionRequest implements Request{

    private ConstructionRequestData requestData;

    public ConstructionRequest(){}

    public ConstructionRequest(GameEra gameEra, long startTime, String construction) {
        initRequest(gameEra, startTime, construction);
    }
    public void initRequest(GameEra gameEra, long startTime, String construction){
        requestData = new ConstructionRequestData(gameEra, startTime, construction);
    }

    @Override
    public Object getRequestData() {
        return requestData;
    }
}
