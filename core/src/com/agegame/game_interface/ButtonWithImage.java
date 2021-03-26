package com.agegame.game_interface;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;

public class ButtonWithImage extends Actor {
    public Pixmap imagePixmap;
    public Texture imageTexture;
    public Vector2 position;
    public boolean disabled;
    protected boolean wasDisabled;
    protected enum ButtonState { JUST_PRESSED, PRESSED, HOVERED, IDLE};
    protected ButtonState state;
    private Runnable onClick;

    public ButtonWithImage(){
        super();
        state = ButtonState.IDLE;
    }

    public ButtonWithImage(Pixmap image, Vector2 position){
        this();
        this.onClick = null;
        this.imagePixmap = new Pixmap(image.getWidth(), image.getHeight(), Pixmap.Format.RGBA8888);
        this.imagePixmap.drawPixmap(image,0,0);
        this.position = position;
        imageTexture = new Texture(this.imagePixmap);
        disabled = false;
        wasDisabled = disabled;
        setBounds(position.x, position.y, image.getWidth(), image.getHeight());

    }

    public ButtonWithImage(Pixmap image, Vector2 position, Runnable onClick){
        this(image, position);
        initOnClick(onClick);

    }

    public void initOnClick(Runnable onClick){
        EventListener listener = initListener(onClick);
        for(EventListener listenerToRemove: getListeners())
            removeListener(listenerToRemove);
        this.addListener(listener);
    }

    private EventListener initListener(final Runnable onClick){
        return new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(!disabled){
                    if(state != ButtonState.PRESSED){
                        state = ButtonState.JUST_PRESSED;
                        if(onClick != null) onClick.run();
                    }
                    else state = ButtonState.PRESSED;
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(!disabled) state = ButtonState.IDLE;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(!disabled) state = ButtonState.HOVERED;
            }
        };
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(imageTexture, position.x, position.y);
    }
}
