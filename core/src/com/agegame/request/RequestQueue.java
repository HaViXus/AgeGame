package com.agegame.request;

import java.util.ArrayList;

public class RequestQueue {
    private ArrayList<Request> requestArray;

    public RequestQueue(){
        requestArray = new ArrayList<>();
    }

    public void addRequest(Request request){
        requestArray.add(request);
    }

    public void deleteRequest(Request requestToDelete){
        //TODO
    }

    public ArrayList<ConstructionRequest> getConstructionRequests(){
        ArrayList<ConstructionRequest> constructionRequests = new ArrayList<>();
        for(Request request: requestArray){
            if(request instanceof ConstructionRequest){
                constructionRequests.add((ConstructionRequest) request);
            }
        }
        return constructionRequests;
    }
}
