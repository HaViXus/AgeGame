package com.agegame.request;

import com.agegame.player.Action;

public class ConstructionRequest implements Request{

    private ConstructionRequestData requestData;

    public ConstructionRequest(){}

    public ConstructionRequest(String gameEra, long startTime, String construction, Action.DomainType domain) {
        initRequest(gameEra, startTime, construction, domain);
    }
    public void initRequest(String gameEra, long startTime, String construction, Action.DomainType domain){
        requestData = new ConstructionRequestData(gameEra, startTime, construction, domain);
    }

    @Override
    public Object getRequestData() {
        return requestData;
    }
}
