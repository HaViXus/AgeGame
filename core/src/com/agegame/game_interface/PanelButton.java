package com.agegame.game_interface;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;


public class PanelButton extends ButtonWithImage {

    private boolean imageFullyLoaded;
    private boolean disabledStateChanged;

    public PanelButton(){
        super();
    }

    public PanelButton(Pixmap image, Vector2 position) {
        super(image, position);
        imageFullyLoaded = true;
    }

    public PanelButton(Pixmap image, Vector2 position, Runnable onClick) {
        super(image, position, onClick);
        imageFullyLoaded = true;
    }

    public void update(PanelRenderData buttonData){
        this.disabled = buttonData.disabled;
        updateDisableStateChange();

        updateImage(buttonData);
    }

    private void updateDisableStateChange(){
        disabledStateChanged = false;
        if(this.disabled != wasDisabled){
            disabledStateChanged = true;
            wasDisabled = this.disabled;
        }
    }

    private void updateImage(PanelRenderData buttonData){
        boolean isProgressNotFull = buttonData.progress > 0 && buttonData.progress < 1;
        if(isProgressNotFull || !imageFullyLoaded) {
            setProgressOnImage(buttonData.image, buttonData.progress);

        }
        else if(disabled){
            setDisabledImage(buttonData.image);
        }
        else if(disabledStateChanged){
            imagePixmap.drawPixmap(buttonData.image,0,0);
            imageTexture.draw(imagePixmap, 0,0);
        }
    }

    private void setProgressOnImage(Pixmap originalImage, float progress){
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();
        int grayHeight = (int)Math.ceil( (1 - progress ) * height);
        imageFullyLoaded = (grayHeight <= 0);
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                if(grayHeight > y) imagePixmap.setColor(originalImage.getPixel(x, y) & 0b11000011);
                else imagePixmap.setColor(originalImage.getPixel(x, y) );
                imagePixmap.drawPixel(x, y);
            }
        }
        imageTexture.draw(imagePixmap,0,0);
    }

    private void setDisabledImage(Pixmap originalImage){
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();

        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                imagePixmap.setColor(originalImage.getPixel(x, y) & 0b11000011);
                imagePixmap.drawPixel(x, y);
            }
        }
        imageTexture.draw(imagePixmap,0,0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(imageTexture, position.x, position.y);
    }


}
