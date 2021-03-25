package com.agegame.game_interface;

import com.agegame.player.Action;
import com.badlogic.gdx.graphics.Pixmap;

public class PanelRenderData{
    public Pixmap image;
    public float progress;
    public boolean disabled;
    public Action.DomainType moveTo;
    public Runnable onClick;
    public String relatedActionName;
}
