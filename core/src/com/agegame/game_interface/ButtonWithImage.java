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
    private enum ButtonState { DISABLED, JUST_PRESSED, PRESSED, HOVERED, IDLE};
    private ButtonState state;
    private Runnable onClick;

    public ButtonWithImage(){
        super();
        state = ButtonState.IDLE;
    }

    public ButtonWithImage(Pixmap image, Vector2 position){
        this();
        this.onClick = null;
        this.imagePixmap = image;
        this.position = position;
        imageTexture = new Texture(this.imagePixmap);
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
                if(state != ButtonState.DISABLED){
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
                if(state != ButtonState.DISABLED) state = ButtonState.IDLE;
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(state != ButtonState.DISABLED) state = ButtonState.HOVERED;
            }
        };
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(imageTexture, position.x, position.y);
    }
}
