package com.agegame.game_interface;

import java.util.ArrayList;

public class PanelRenderDataPacket {
    public ArrayList<PanelRenderData> packet;

    public PanelRenderDataPacket(){
        packet = new ArrayList<>();
    }

    public PanelRenderDataPacket(ArrayList<PanelRenderData> data){
        packet = data;
    }
}
